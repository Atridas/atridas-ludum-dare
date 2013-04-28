package cat.atridas87.ld26;

public class GameParameters {

	
	
	// BOTS
	public final static int BASIC_BOT_LIVES = 25;
	public final static int BASIC_BOT_ATTACK = 5;
	public final static float BASIC_BOT_ATTACK_TIME = 1.f;
	public final static float BASIC_BOT_RANGE = 25;
	public final static float BASIC_BOT_AI_WEIGHT = 5;
	public final static int BASIC_BOT_VALUE = 10;
	
	public final static float BASIC_BOT_MASS = 1;
	public final static float BASIC_BOT_MAX_SPEED = 10;
	public final static float BASIC_BOT_MAX_FORCE = 500;


	public final static int TANK_BOT_LIVES = 100;
	public final static int TANK_BOT_ATTACK = 2;
	public final static float TANK_BOT_ATTACK_TIME = 5.f;
	public final static float TANK_BOT_RANGE = 10;
	public final static float TANK_BOT_AI_WEIGHT = 2;
	public final static int TANK_BOT_VALUE = 15;
	
	public final static float TANK_BOT_MASS = 3f;
	public final static float TANK_BOT_MAX_SPEED = 7.5f;
	public final static float TANK_BOT_MAX_FORCE = 500;


	public final static int SUPER_BOT_LIVES = 40;
	public final static int SUPER_BOT_ATTACK = 10;
	public final static float SUPER_BOT_ATTACK_TIME = 0.75F;
	public final static float SUPER_BOT_RANGE = 50.f;
	public final static float SUPER_BOT_AI_WEIGHT = 1;
	public final static int SUPER_BOT_VALUE = 60;
	
	public final static float SUPER_BOT_MASS = 2;
	public final static float SUPER_BOT_MAX_SPEED = 10;
	public final static float SUPER_BOT_MAX_FORCE = 500;
	
	// Towers
	public static final int TOWER_LIVES = 500;
	
	public static final float TOWER_RANGE = 75;
	public static final float TOWER_ATTACK = 10;
	public static final float TOWER_COOL_DOWN = 1.5f;

	public static final int TOWER_POINTS = 2000;

	//HOME
	public static final int HOME_LIVES = 5000;
	
	// costs
	public static final int COINS_PER_SECOND_PER_LEVEL[] = { 10, 15, 20, 25, 30, 35, 40 };
	public static final int MAX_COINS_PER_LEVEL[] = { 500, 875, 1250, 1625, 2000, 2750, 3500 };
	public static final int COST_LEVEL[] = { 250, 550, 850, 1200, 1600, 2000, 2500 };

	public static final int NUM_BOTS[][] = {{3,0,0}, {0,3,0}, {0,0,3}, {0,9,0}, {15,0,0}, {12,6,0}, {12,0,3}, {15,9,3}};
	public static final int COST_FORMATION[] = {50, 75, 150, 200, 240, 300, 320, 550};

	public static final int NUM_LEVELS = COINS_PER_SECOND_PER_LEVEL.length;
	

	// OTHER
	public static final float TIME_BETWEEN_BOTS = 1f;

	public static final float AI_TIME_BTWN_LEVELS[] =  {  30,   45,   60,   75,   90,  105,  120};
	public static final float AI_LEVEL_TIME_TO_ADD[] = {  10,    8,    7,   6f,    5,   3f,    2, 1.25f};
}
