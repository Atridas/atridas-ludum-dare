package cat.atridas87.ld26.gameobjects;

import static cat.atridas87.ld26.GameParameters.*;

import javax.vecmath.Vector2f;

import org.lwjgl.opengl.GL11;

import cat.atridas87.ld26.render.Model;
import cat.atridas87.ld26.render.ShaderManager;
import cat.atridas87.ld26.sounds.Sounds;

public class Shot {

	private static final Model model;

	private static final float MAX_FORCE = 500;
	private static final float MAX_SPEED = 500;
	private static final float MASS = 1;

	private final Vector2f velocity = new Vector2f(0, 0);

	private final boolean player;
	private final float range;
	private final float attack;

	private boolean alive = true;

	private float distanciaFeta = 0;

	private final Vector2f position;
	private final Vector2f destination;

	public Shot(boolean _player, float _range, float _attack,
			Vector2f _position, Vector2f _destination) {

		player = _player;
		range = _range;
		attack = _attack;

		position = new Vector2f(_position);
		destination = new Vector2f(_destination);
	}

	private Vector2f calcSteeringSeek(Vector2f targetPos) {

		Vector2f desiredVelocity = new Vector2f();
		desiredVelocity.x = targetPos.x - position.x;
		desiredVelocity.y = targetPos.y - position.y;

		desiredVelocity.normalize();
		desiredVelocity.scale(MAX_SPEED);

		return desiredVelocity;
	}

	private void calcPosition(float _dt) {
		Vector2f force = calcSteeringSeek(destination);

		if (force.lengthSquared() > MAX_FORCE * MAX_FORCE) {
			float len = force.length();
			force.scale(MAX_FORCE / len);
		}

		Vector2f acceleration = new Vector2f(force.x / MASS, force.y / MASS);

		// if(type == Type.BASIC)
		// System.out.println("x: " + acceleration.x + " y: " + acceleration.y);

		velocity.x += acceleration.x * _dt;
		velocity.y += acceleration.y * _dt;

		if (velocity.lengthSquared() > MAX_SPEED * MAX_SPEED) {
			float len = velocity.length();
			velocity.scale(MAX_SPEED / len);
		}

		// if(type == Type.BASIC)
		// System.out.println("x: " + velocity.x + " y: " + velocity.y);

		position.x += velocity.x * _dt;
		position.y += velocity.y * _dt;

		distanciaFeta += velocity.length() * _dt;
	}

	public void update(float _dt) {
		if (!alive)
			return;
		calcPosition(_dt);

		if (distanciaFeta > range) {
			alive = false;
		}

		{
			Tower tower = Battleground.instance.getClosestTower(position);
			if (tower.player != player) {

				Vector2f distToTower = new Vector2f();
				distToTower.x = tower.position.x - position.x;
				distToTower.y = tower.position.y - position.y;

				if (distToTower.lengthSquared() < Tower.TOWER_WIDTH
						* Tower.TOWER_WIDTH / 4) {
					tower.live -= attack;
					alive = false;

					//Sounds.botDestroyed.play(1, FX_VOLUME_TOUCH);
					return;
				}
			}

		}
		{
			Bot bot = Battleground.instance.getClosestBot(position);
			if (bot != null && bot.player != player) {

				Vector2f distToBot = new Vector2f();
				distToBot.x = bot.position.x - position.x;
				distToBot.y = bot.position.y - position.y;

				if (distToBot.lengthSquared() < Bot.BOT_WIDTH * Bot.BOT_WIDTH
						/ 4) {
					bot.lives -= attack;
					alive = false;
					return;
				}
			}
		}
		{
			Vector2f enemyHomePosition = player ? Home.AI_HOME
					: Home.PLAYER_HOME;
			Vector2f distToHome = new Vector2f();
			distToHome.x = enemyHomePosition.x - position.x;
			distToHome.y = enemyHomePosition.y - position.y;

			if (distToHome.lengthSquared() < Home.HOME_WIDTH * Home.HOME_WIDTH
					/ 4) {
				Battleground.instance.getHome(!player).lives -= attack;
				alive = false;
				return;
			}

		}
	}

	public boolean alive() {
		return alive;
	}

	public void render() {
		if (!alive)
			return;

		float r = player ? 1 : 0;
		float g = player ? 0 : 1;
		float b = player ? 1 : 0;

		ShaderManager.instance.setColor(r, g, b, 1);
		ShaderManager.instance.setPosition(position.x, position.y);

		model.draw();
	}

	static {
		float positions[] = { 0, 0, 0 };
		short indexs[] = { 0 };

		model = new Model(positions, indexs, GL11.GL_POINTS);
	}

}
