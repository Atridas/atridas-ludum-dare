package cat.atridas87.ld26.gameobjects;

import java.util.Random;

import javax.vecmath.Vector2f;

import org.lwjgl.opengl.GL11;

import cat.atridas87.ld26.render.Model;
import cat.atridas87.ld26.render.ShaderManager;

public class Bot {

	private static final float DISTANCE_CHANGE_CONTROL_POINT = 25;
	private static final float EVADE_TOWERS_AT = 20;

	private static final Model models[];
	
	private static Random rnd = new Random();

	private final boolean player;
	private final Type type;
	private final Lane lane;

	private final float rangeToTower;

	private final Vector2f position;
	public int lives;

	private final Vector2f velocity = new Vector2f(0, 0);

	private int controlPoint;

	public Bot(boolean _player, Type _type, Lane _lane) {
		lane = _lane;
		controlPoint = _player ? 0 : lane.controlPoints.length - 1;
		Vector2f firstPoint = lane.controlPoints[controlPoint];

		player = _player;
		type = _type;
		position = new Vector2f(firstPoint);
		lives = type.lives;

		controlPoint += _player ? 1 : -1;
		
		rangeToTower = type.range + 7.5f;
	}

	public void render() {

		float r = player ? 1 : 0;
		float g = player ? 0 : 1;
		float b = player ? 1 : 0;

		// TODO pintar vida

		ShaderManager.instance.setColor(r, g, b, 1);
		ShaderManager.instance.setPosition(position.x, position.y);

		models[type.ordinal()].draw();
	}
	
	private Vector2f calcSteeringSeek(Vector2f targetPos) {

		Vector2f desiredVelocity = new Vector2f();
		desiredVelocity.x = targetPos.x - position.x;
		desiredVelocity.y = targetPos.y - position.y;

		desiredVelocity.normalize();
		desiredVelocity.scale(type.maxSpeed);

		return desiredVelocity;
	}
	
	private Vector2f calcSteeringArrive(Vector2f targetPos) {

		Vector2f desiredVelocity = new Vector2f();
		desiredVelocity.x = targetPos.x - position.x;
		desiredVelocity.y = targetPos.y - position.y;
		
		float dist = desiredVelocity.length();
		
		if(dist > 0) {
			float magicNumber = 1f;
			
			float speed = dist / magicNumber;
			
			//speed = (speed > type.maxSpeed) ? type.maxSpeed : speed;
			
			desiredVelocity.scale(speed / dist);

			desiredVelocity.x -= velocity.x;
			desiredVelocity.y -= velocity.y;

			return desiredVelocity;
		}

		desiredVelocity.x = 0;
		desiredVelocity.y = 0;

		return desiredVelocity;
	}

	private Vector2f calcSteeringEvade(Vector2f evadePoint, float evadeDistance, float evadeCosAngle) {
		
		Vector2f desiredVelocity = new Vector2f();
		desiredVelocity.x = evadePoint.x - position.x;
		desiredVelocity.y = evadePoint.y - position.y;
		
		if(desiredVelocity.lengthSquared() > evadeDistance * evadeDistance)
		{
			return new Vector2f(0,0);
		}
		
		float dist = desiredVelocity.length();
		
		desiredVelocity.scale( 1 / dist );
		
		Vector2f velocityVector = new Vector2f(velocity);
		velocityVector.normalize();
		
		float dot = velocityVector.dot(desiredVelocity);
		
		if(dot < evadeCosAngle) {
			return new Vector2f(0,0);
		}
		
		desiredVelocity.scale(type.maxSpeed * -5);
		
		
		return desiredVelocity;
	}

	private Vector2f calcSteeringWander() {

		Vector2f targetPos = new Vector2f(velocity);
		if(targetPos.x != 0 || targetPos.y != 0)
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
	
	private Vector2f calcSteeringArriveToTower(Tower tower) {
		Vector2f arrivePoint = new Vector2f();
		arrivePoint.x = position.x - tower.position.x;
		arrivePoint.y = position.y - tower.position.y;
		arrivePoint.normalize();
		arrivePoint.scale(rangeToTower / 2);
		arrivePoint.x += tower.position.x;
		arrivePoint.y += tower.position.y;
		
		return calcSteeringArrive(arrivePoint);
	}

	private Vector2f calcSteering() {
		Tower targetTower = Battleground.instance.getClosestTower(position);
		
		Vector2f steeringTower;
		if(targetTower.player == player) {
			steeringTower = calcSteeringEvade(targetTower.position, EVADE_TOWERS_AT, 0.5f);
		} else {
			Vector2f distToTower = new Vector2f();
			distToTower.x = targetTower.position.x - position.x;
			distToTower.y = targetTower.position.y - position.y;
			
			float range = (rangeToTower < 50) ? 50 : rangeToTower;
			
			if(distToTower.lengthSquared() < range * range)
			{
				return calcSteeringArriveToTower(targetTower);
			} else {
				steeringTower = new Vector2f();
			}
		}
		
		Vector2f lane = calcSteeringLane();
		Vector2f wander = calcSteeringWander();
		
		Vector2f result = new Vector2f(lane);
		result.add(steeringTower);
		result.add(wander);
		
		return result;
	}

	private void calcPosition(float _dt) {
		Vector2f force = calcSteering();

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

	public void update(float _dt) {
		calcPosition(_dt);
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
		BASIC(5, 25, 1.f, 1, 20, 500, 25), TANK(2, 50, 5.f, 3, 15f, 500, 10), SUPER(15,
				40, 0.75f, 2, 20, 500, 50);

		private final int lives, attack;
		private final float attTime, mass, maxSpeed, maxForce, range;

		private Type(int _lives, int _attack, float _attTime, float _mass,
				float _maxSpeed, float _maxForce, float _range) {
			lives = _lives;
			attack = _attack;
			attTime = _attTime;
			mass = _mass;
			maxSpeed = _maxSpeed;
			maxForce = _maxForce;
			range = _range;
		}
	}
}
