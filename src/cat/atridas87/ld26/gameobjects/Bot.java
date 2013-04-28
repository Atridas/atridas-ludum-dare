package cat.atridas87.ld26.gameobjects;

import java.util.Random;

import javax.vecmath.Vector2f;

import org.lwjgl.opengl.GL11;

import cat.atridas87.ld26.render.Model;
import cat.atridas87.ld26.render.ShaderManager;
import static cat.atridas87.ld26.GameParameters.*;

public class Bot {

	public static final float BOT_WIDTH = 5;

	private static final float DISTANCE_CHANGE_CONTROL_POINT = 25;
	private static final float EVADE_TOWERS_AT = 25;
	private static final float EVADE_BOTS_AT = 10;

	private static final Model models[];

	private static Random rnd = new Random();

	public final boolean player;
	public final Type type;
	public final Lane lane;

	private final float rangeToTower;
	private final float rangeToBot;
	private final float rangeToHome;

	public final Vector2f position;
	public int lives;

	private final Vector2f velocity = new Vector2f(0, 0);

	private int controlPoint;
	private float timeSinceLastShot = 0;

	public Bot(boolean _player, Type _type, Lane _lane) {
		lane = _lane;
		controlPoint = _player ? 0 : lane.controlPoints.length - 1;
		Vector2f firstPoint = lane.controlPoints[controlPoint];

		player = _player;
		type = _type;
		position = new Vector2f(firstPoint);
		lives = type.lives;

		controlPoint += _player ? 1 : -1;

		rangeToTower = type.range + Tower.TOWER_WIDTH / 2;
		rangeToBot = type.range + BOT_WIDTH / 2;
		rangeToHome = type.range + Home.HOME_WIDTH / 2;

		velocity.x = lane.controlPoints[controlPoint].x - position.x;
		velocity.y = lane.controlPoints[controlPoint].y - position.y;
		velocity.normalize();
		velocity.scale(type.maxSpeed / 4);
	}

	public void render() {

		float r = player ? 1 : 0;
		float g = player ? 0 : 1;
		float b = player ? 1 : 0;

		float vida = ((float) lives) / ((float) type.lives);

		r = r * vida + (1 - vida);
		g *= vida;
		b *= vida;

		ShaderManager.instance.setColor(r, g, b, 1);
		ShaderManager.instance.setPosition(position.x, position.y);

		models[type.ordinal()].draw();
	}

	private Vector2f calcSteeringSeek(Vector2f targetPos) {

		Vector2f desiredVelocity = new Vector2f();
		desiredVelocity.x = targetPos.x - position.x;
		desiredVelocity.y = targetPos.y - position.y;

		if (desiredVelocity.x == 0 && desiredVelocity.y == 0) {
			return desiredVelocity;
		}

		desiredVelocity.normalize();
		desiredVelocity.scale(type.maxSpeed);

		return desiredVelocity;
	}

	private Vector2f calcSteeringArrive(Vector2f targetPos) {

		Vector2f desiredVelocity = new Vector2f();
		desiredVelocity.x = targetPos.x - position.x;
		desiredVelocity.y = targetPos.y - position.y;

		float dist = desiredVelocity.length();

		if (dist > 0) {
			float magicNumber = 1f;

			float speed = dist / magicNumber;

			// speed = (speed > type.maxSpeed) ? type.maxSpeed : speed;

			desiredVelocity.scale(speed / dist);

			desiredVelocity.x -= velocity.x;
			desiredVelocity.y -= velocity.y;

			return desiredVelocity;
		}

		desiredVelocity.x = 0;
		desiredVelocity.y = 0;

		return desiredVelocity;
	}

	private Vector2f calcSteeringEvade(Vector2f evadePoint,
			float evadeDistance, float evadeCosAngle) {

		Vector2f desiredVelocity = new Vector2f();
		desiredVelocity.x = evadePoint.x - position.x;
		desiredVelocity.y = evadePoint.y - position.y;

		if (desiredVelocity.lengthSquared() > evadeDistance * evadeDistance) {
			return new Vector2f(0, 0);
		}

		float dist = desiredVelocity.length();
		if (dist == 0) {
			Vector2f w = calcSteeringWander();
			w.scale(type.maxSpeed * 5);
			return w;
		}

		desiredVelocity.scale(1 / dist);

		Vector2f velocityVector = new Vector2f(velocity);
		velocityVector.normalize();

		float dot = velocityVector.dot(desiredVelocity);

		if (dot < evadeCosAngle) {
			return new Vector2f(0, 0);
		}

		desiredVelocity.scale(type.maxSpeed * -5);

		return desiredVelocity;
	}

	private Vector2f calcSteeringWander() {

		Vector2f targetPos = new Vector2f(velocity);
		if (targetPos.x != 0 || targetPos.y != 0)
			targetPos.normalize();

		float a = rnd.nextFloat() * 3.14159f * 2;

		targetPos.x += Math.sin(a);
		targetPos.y += Math.cos(a);

		return targetPos;
	}

	private Vector2f calcSteeringLane() {

		Vector2f targetPoint = lane.controlPoints[controlPoint];

		Vector2f distance = new Vector2f();
		distance.x = targetPoint.x - position.x;
		distance.y = targetPoint.y - position.y;

		if (distance.lengthSquared() < DISTANCE_CHANGE_CONTROL_POINT
				* DISTANCE_CHANGE_CONTROL_POINT) {
			int nextControlPoint = controlPoint + (player ? 1 : -1);
			if (nextControlPoint >= 0
					&& nextControlPoint < lane.controlPoints.length) {
				controlPoint = nextControlPoint;
				targetPoint = lane.controlPoints[controlPoint];
			}
		}

		return calcSteeringSeek(targetPoint);
	}

	private Vector2f calcSteeringArriveToDistance(Vector2f center,
			float distance) {
		Vector2f arrivePoint = new Vector2f();
		arrivePoint.x = position.x - center.x;
		arrivePoint.y = position.y - center.y;
		arrivePoint.normalize();
		arrivePoint.scale(distance);
		arrivePoint.x += center.x;
		arrivePoint.y += center.y;

		return calcSteeringArrive(arrivePoint);
	}

	private static final class Steering {
		Vector2f steering;
		boolean force;
	}

	private Steering calcSteeringTower(Tower closestTower) {
		Steering steering = new Steering();

		Vector2f steeringTower;
		steeringTower = calcSteeringEvade(closestTower.position,
				EVADE_TOWERS_AT, 0.5f);
		if (closestTower.player != player && closestTower.live > 0) {
			Vector2f distToTower = new Vector2f();
			distToTower.x = closestTower.position.x - position.x;
			distToTower.y = closestTower.position.y - position.y;

			float range = (rangeToTower < 50) ? 50 : rangeToTower;

			if (distToTower.lengthSquared() < range * range) {
				Vector2f steeringTowerArrive = calcSteeringArriveToDistance(
						closestTower.position, rangeToTower / 2);
				steeringTowerArrive.add(steeringTower);
				steering.steering = steeringTowerArrive;
				steering.force = true;
				return steering;
			} else {
				steeringTower = new Vector2f();
			}
		}

		steering.steering = steeringTower;
		steering.force = false;
		return steering;
	}

	private Steering calcSteeringBot(Bot closestBot) {
		Steering steering = new Steering();

		Vector2f steeringBot;
		steeringBot = calcSteeringEvade(closestBot.position, EVADE_BOTS_AT,
				0.5f);
		if (closestBot.player != player && closestBot.lives > 0) {
			Vector2f distToBot = new Vector2f();
			distToBot.x = closestBot.position.x - position.x;
			distToBot.y = closestBot.position.y - position.y;

			float range = (rangeToBot < 50) ? 50 : rangeToBot;

			if (distToBot.lengthSquared() < range * range) {
				Vector2f steeringBotArrive = calcSteeringArriveToDistance(
						closestBot.position, rangeToBot / 2);
				steeringBotArrive.add(steeringBot);
				steering.steering = steeringBotArrive;
				steering.force = true;
				return steering;
			} else {
				steeringBot = new Vector2f();
			}
		}

		steering.steering = steeringBot;
		steering.force = false;
		return steering;
	}

	private Steering calcSteeringBots(Bot closestBots[]) {
		Steering steering = new Steering();
		steering.steering = new Vector2f(0, 0);
		steering.force = false;

		for (int i = 0; i < closestBots.length; i++) {
			Steering steering2 = calcSteeringBot(closestBots[i]);
			if (steering2.force) {
				return steering2;
			}
			steering.steering.add(steering2.steering);
		}

		return steering;
	}

	private Steering calcSteeringHome() {
		Steering steering = new Steering();
		steering.steering = new Vector2f(0, 0);
		steering.force = false;

		Vector2f enemyHomePosition = player ? Home.AI_HOME : Home.PLAYER_HOME;

		Vector2f distToHome = new Vector2f();
		distToHome.x = enemyHomePosition.x - position.x;
		distToHome.y = enemyHomePosition.y - position.y;

		float range = (rangeToBot < 50) ? 50 : rangeToBot;

		if (distToHome.lengthSquared() < range * range) {
			Vector2f steeringBotArrive = calcSteeringArriveToDistance(
					enemyHomePosition, rangeToHome / 2);
			steering.steering = steeringBotArrive;
			steering.force = true;
			return steering;
		}

		return steering;
	}

	private Vector2f calcSteering(Tower closestTower, Bot closestBots[]) {

		Vector2f directionToField = Battleground.instance
				.vectorToField(position);
		if (directionToField.lengthSquared() > 0) {
			directionToField.scale(type.maxSpeed * 5);
			return directionToField;
		}

		Steering steeringTower = calcSteeringTower(closestTower);
		Steering steeringBots = calcSteeringBots(closestBots);
		Steering steeringHome = calcSteeringHome();

		Vector2f steeringElements = new Vector2f();
		steeringElements.x = steeringTower.steering.x + steeringBots.steering.x
				+ steeringHome.steering.x;
		steeringElements.y = steeringTower.steering.y + steeringBots.steering.y
				+ steeringHome.steering.y;

		if (steeringTower.force || steeringBots.force || steeringHome.force) {
			return steeringElements;
		}

		Vector2f lane = calcSteeringLane();
		Vector2f wander = calcSteeringWander();

		Vector2f result = new Vector2f(lane);
		result.add(steeringElements);
		result.add(wander);

		return result;
	}

	private void calcPosition(Tower closestTower, Bot closestBots[], float _dt) {
		Vector2f force = calcSteering(closestTower, closestBots);

		if (force.lengthSquared() > type.maxForce * type.maxForce) {
			float len = force.length();
			force.scale(type.maxForce / len);
		}

		Vector2f acceleration = new Vector2f(force.x / type.mass, force.y
				/ type.mass);

		// if(type == Type.BASIC)
		// System.out.println("x: " + acceleration.x + " y: " + acceleration.y);

		velocity.x += acceleration.x * _dt;
		velocity.y += acceleration.y * _dt;

		if (velocity.lengthSquared() > type.maxSpeed * type.maxSpeed) {
			float len = velocity.length();
			velocity.scale(type.maxSpeed / len);
		}

		// if(type == Type.BASIC)
		// System.out.println("x: " + velocity.x + " y: " + velocity.y);

		position.x += velocity.x * _dt;
		position.y += velocity.y * _dt;
	}

	private void calcShoot(Tower closestTower, Bot closestBots[], float _dt) {
		if (timeSinceLastShot < type.attTime) {
			timeSinceLastShot += _dt;
		} else {
			
			if (closestTower.player != player && closestTower.live > 0) {
				Vector2f distToTower = new Vector2f();
				distToTower.x = closestTower.position.x - position.x;
				distToTower.y = closestTower.position.y - position.y;

				if (distToTower.lengthSquared() < rangeToTower * rangeToTower) {
					Shot shot = new Shot(player, type.range, type.attack,
							position, closestTower.position);
					timeSinceLastShot = 0;
					Battleground.instance.addShot(shot);
					return;
				}
			}

			int enemyBotsAtRange = 0;
			for (int i = 0; i < closestBots.length; i++) {
				if (closestBots[i].player != player) {

					Vector2f distToBot = new Vector2f();
					distToBot.x = closestBots[i].position.x - position.x;
					distToBot.y = closestBots[i].position.y - position.y;

					if (distToBot.lengthSquared() < rangeToBot * rangeToBot) {
						enemyBotsAtRange++;
					}
				}
			}

			if (enemyBotsAtRange > 0) {
				int shootAt = rnd.nextInt(enemyBotsAtRange);

				for (int i = 0; i < closestBots.length; i++) {
					if (closestBots[i].player != player) {

						Vector2f distToBot = new Vector2f();
						distToBot.x = closestBots[i].position.x - position.x;
						distToBot.y = closestBots[i].position.y - position.y;

						if (distToBot.lengthSquared() < rangeToBot * rangeToBot) {
							if (shootAt == 0) {
								Shot shot = new Shot(player, type.range,
										type.attack, position,
										closestBots[i].position);
								timeSinceLastShot = 0;
								Battleground.instance.addShot(shot);
								return;
							} else {
								shootAt--;
							}
						}
					}
				}

			}

			Vector2f enemyHomePosition = player ? Home.AI_HOME
					: Home.PLAYER_HOME;
			Vector2f distToHome = new Vector2f();
			distToHome.x = enemyHomePosition.x - position.x;
			distToHome.y = enemyHomePosition.y - position.y;

			if (distToHome.lengthSquared() < rangeToHome * rangeToHome) {
				Shot shot = new Shot(player, type.range, type.attack, position,
						enemyHomePosition);
				timeSinceLastShot = 0;
				Battleground.instance.addShot(shot);
				return;
			}
		}
	}

	public void update(float _dt) {
		Tower closestTower = Battleground.instance.getClosestTower(position);
		Bot[] cosestBots = Battleground.instance.getClosestBots(this);
		calcPosition(closestTower, cosestBots, _dt);

		calcShoot(closestTower,
				Battleground.instance.getClosestEnemyBotsInRange(position,
						player, rangeToBot), _dt);
	}

	public String toString() {
		return "bot x:" + position.x + " y: " + position.y;
	}

	static {
		models = new Model[Type.values().length];

		{
			float positions[] = { 0, 0, 0 };
			short indexs[] = { 0 };

			models[Type.BASIC.ordinal()] = new Model(positions, indexs,
					GL11.GL_POINTS);
		}
		{
			float positions[] = { 2.5f, 2.5f, 0, -2.5f, 2.5f, 0, -2.5f, -2.5f,
					0, 2.5f, -2.5f, 0, };
			short indexs[] = { 0, 1, 1, 2, 2, 3, 3, 0 };

			models[Type.TANK.ordinal()] = new Model(positions, indexs,
					GL11.GL_LINES);
		}
		{
			float positions[] = { 3, 5, 0, 3.75f, 3, 0, 5.5f, 3, 0, 4, 2, 0,
					4.5f, 0, 0, 3, 1.25f, 0, 1.5f, 0, 0, 2, 2, 0, 0.5f, 3, 0,
					2.25f, 3, 0 };
			for (int i = 0; i < 10; i++) {
				positions[i * 3 + 0] = (positions[i * 3 + 0] - 3.f) * 1.5f;
				positions[i * 3 + 1] = (positions[i * 3 + 1] - 2.5f) * 1.5f;
			}
			short indexs[] = { 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8,
					8, 9, 9, 0 };

			models[Type.SUPER.ordinal()] = new Model(positions, indexs,
					GL11.GL_LINES);
		}
	}

	public static enum Type {

		BASIC(BASIC_BOT_LIVES, BASIC_BOT_ATTACK, BASIC_BOT_ATTACK_TIME,
				BASIC_BOT_MASS, BASIC_BOT_MAX_SPEED, BASIC_BOT_MAX_FORCE,
				BASIC_BOT_RANGE, BASIC_BOT_AI_WEIGHT, BASIC_BOT_VALUE), TANK(
				TANK_BOT_LIVES, TANK_BOT_ATTACK, TANK_BOT_ATTACK_TIME,
				TANK_BOT_MASS, TANK_BOT_MAX_SPEED, TANK_BOT_MAX_FORCE,
				TANK_BOT_RANGE, TANK_BOT_AI_WEIGHT, TANK_BOT_VALUE), SUPER(
				SUPER_BOT_LIVES, SUPER_BOT_ATTACK, SUPER_BOT_ATTACK_TIME,
				SUPER_BOT_MASS, SUPER_BOT_MAX_SPEED, SUPER_BOT_MAX_FORCE,
				SUPER_BOT_RANGE, SUPER_BOT_AI_WEIGHT, SUPER_BOT_VALUE);

		private final int lives, attack;
		private final float attTime, mass, maxSpeed, maxForce, range;
		public final float aiWeight;
		public final int value;

		private Type(int _lives, int _attack, float _attTime, float _mass,
				float _maxSpeed, float _maxForce, float _range,
				float _aiWeight, int _value) {
			lives = _lives;
			attack = _attack;
			attTime = _attTime;
			mass = _mass;
			maxSpeed = _maxSpeed;
			maxForce = _maxForce;
			range = _range;
			aiWeight = _aiWeight;
			value = _value;
		}
	}
}
