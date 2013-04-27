package cat.atridas87.ld26.gameobjects;

import javax.vecmath.Vector2f;

import org.lwjgl.opengl.GL11;

import cat.atridas87.ld26.render.Model;
import cat.atridas87.ld26.render.ShaderManager;

public class Bot {
	
	private static final float DISTANCE_CHANGE_CONTROL_POINT = 25;

	private static final Model models[];

	private final boolean player;
	private final Type type;
	private final Lane lane;

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

	private Vector2f calcSteeringLane() {
		
		Vector2f targetPos = lane.controlPoints[controlPoint];
		
		Vector2f desiredVelocityLane = new Vector2f();
		desiredVelocityLane.x = targetPos.x - position.x;
		desiredVelocityLane.y = targetPos.y - position.y;
		
		if(desiredVelocityLane.lengthSquared() < DISTANCE_CHANGE_CONTROL_POINT * DISTANCE_CHANGE_CONTROL_POINT)
		{
			int nextControlPoint = controlPoint + (player? 1 : -1);
			if(nextControlPoint >= 0 && nextControlPoint < lane.controlPoints.length) {
				controlPoint = nextControlPoint;
			}
		}
		
		desiredVelocityLane.normalize();
		desiredVelocityLane.scale(type.maxSpeed);
		
		
		return desiredVelocityLane;
	}
	

	private Vector2f calcSteering() {
		return calcSteeringLane();
	}

	private void calcPosition(float _dt) {
		Vector2f force = calcSteering();

		if (force.lengthSquared() > type.maxForce * type.maxForce) {
			float len = force.length();
			force.scale(type.maxForce / len);
		}

		Vector2f acceleration = new Vector2f(force.x / type.mass, force.y
				/ type.mass);
		
		//if(type == Type.BASIC)
		//	System.out.println("x: " + acceleration.x + " y: " + acceleration.y);

		velocity.x += acceleration.x * _dt;
		velocity.y += acceleration.y * _dt;


		if (velocity.lengthSquared() > type.maxSpeed * type.maxSpeed) {
			float len = velocity.length();
			velocity.scale(type.maxSpeed / len);
		}

		//if(type == Type.BASIC)
		//	System.out.println("x: " + velocity.x + " y: " + velocity.y);


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
		BASIC(5, 25, 1.f, 1, 20, 500),
		TANK(2, 50, 5.f, 3, 15f, 500),
		SUPER(15, 40, 0.75f, 2, 20, 500);

		private final int lives, attack;
		private final float attTime, mass, maxSpeed, maxForce;

		private Type(int _lives, int _attack, float _attTime, float _mass,
				float _maxSpeed, float _maxForce) {
			lives = _lives;
			attack = _attack;
			attTime = _attTime;
			mass = _mass;
			maxSpeed = _maxSpeed;
			maxForce = _maxForce;
		}
	}
}
