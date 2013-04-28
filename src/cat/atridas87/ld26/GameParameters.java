package cat.atridas87.ld26;

public class GameParameters {

	
	
	// BOTS
	public final static int BASIC_BOT_LIVES = 100;
	public final static int BASIC_BOT_ATTACK = 5;
	public final static float BASIC_BOT_ATTACK_TIME = 1.f;
	public final static float BASIC_BOT_RANGE = 50;
	public final static float BASIC_BOT_AI_WEIGHT = 5;
	public final static int BASIC_BOT_VALUE = 10;
	
	public final static float BASIC_BOT_MASS = 1;
	public final static float BASIC_BOT_MAX_SPEED = 10;
	public final static float BASIC_BOT_MAX_FORCE = 500;


	public final static int TANK_BOT_LIVES = 500;
	public final static int TANK_BOT_ATTACK = 2;
	public final static float TANK_BOT_ATTACK_TIME = 5.f;
	public final static float TANK_BOT_RANGE = 20;
	public final static float TANK_BOT_AI_WEIGHT = 2;
	public final static int TANK_BOT_VALUE = 15;
	
	public final static float TANK_BOT_MASS = 3f;
	public final static float TANK_BOT_MAX_SPEED = 7.5f;
	public final static float TANK_BOT_MAX_FORCE = 500;


	public final static int SUPER_BOT_LIVES = 180;
	public final static int SUPER_BOT_ATTACK = 10;
	public final static float SUPER_BOT_ATTACK_TIME = 0.75F;
	public final static float SUPER_BOT_RANGE = 75.f;
	public final static float SUPER_BOT_AI_WEIGHT = 1;
	public final static int SUPER_BOT_VALUE = 60;
	
	public final static float SUPER_BOT_MASS = 2;
	public final static float SUPER_BOT_MAX_SPEED = 10;
	public final static float SUPER_BOT_MAX_FORCE = 500;


	public static final float DISTANCE_CHANGE_CONTROL_POINT = 35;
	public static final float EVADE_TOWERS_AT = 20;
	public static final float EVADE_BOTS_AT = 10;
	
	
	// Towers
	public static final int TOWER_LIVES = 2500;
	
	public static final float TOWER_RANGE = 75;
	public static final float TOWER_ATTACK = 45;
	public static final float TOWER_COOL_DOWN = 1.5f;

	public static final int TOWER_POINTS = 2000;
	

	public static final float TOWER_0_X = 125;
	public static final float TOWER_0_Y = 15;
	
	public static final float TOWER_1_X = 325;
	public static final float TOWER_1_Y = 35;
	
	public static final float TOWER_2_X = 425;
	public static final float TOWER_2_Y = 15;
	
	public static final float TOWER_3_X = 510;
	public static final float TOWER_3_Y = 110;
	
	public static final float TOWER_4_X = 585;
	public static final float TOWER_4_Y = 175;
	
	public static final float TOWER_5_X = 565;
	public static final float TOWER_5_Y = 275;
	
	public static final float TOWER_6_X = 585;
	public static final float TOWER_6_Y = 475;
	
	public static final float TOWER_7_X = 415;
	public static final float TOWER_7_Y = 165;
	
	public static final float TOWER_8_X = 385;
	public static final float TOWER_8_Y = 235;
	
	public static final float TOWER_9_X = 515;
	public static final float TOWER_9_Y = 40;
	
	public static final float TOWER_10_X = 560;
	public static final float TOWER_10_Y = 85;

	//HOME
	public static final int HOME_LIVES = 5000;
	
	// costs
	public static final int COINS_PER_SECOND_PER_LEVEL[] = { 5, 10, 15, 20, 25, 30, 35 };
	public static final int MAX_COINS_PER_LEVEL[] = { 500, 875, 1250, 1625, 2000, 2750, 3500 };
	public static final int COST_LEVEL[] = { 250, 550, 850, 1200, 1600, 2000, 2500 };

	public static final int NUM_BOTS[][] = {{3,0,0}, {0,3,0}, {0,0,3}, {0,9,0}, {15,0,0}, {12,6,0}, {12,0,3}, {15,9,3}};
	public static final int COST_FORMATION[] = {50, 75, 150, 200, 240, 300, 320, 550};

	public static final int NUM_LEVELS = COINS_PER_SECOND_PER_LEVEL.length;
	

	// OTHER
	public static final float TIME_BETWEEN_BOTS = 0.75f;

	public static final float AI_TIME_BTWN_LEVELS[] =  {  30,   45,   60,   75,   90,  105,  120};
	public static final float AI_LEVEL_TIME_TO_ADD[] = {   5.f,    3.375f,    2.25f,   1.5f,    1f,   0.78f,    0.625f, 0.5f};
	//public static final float AI_LEVEL_TIME_TO_ADD[] = {  10,    8,    7,   6f,    5,   3f,    2, 1.25f};
	

	public static final float FX_VOLUME_MUSIC = 0.5f;
	
	public static final float FX_VOLUME_BOT_DESTROYED = 0.33f;
	public static final float FX_VOLUME_NEW_BOT = 0.25f;
	public static final float FX_VOLUME_SHOT = 0.25f;
	public static final float FX_VOLUME_TOUCH = 0.25f;
	public static final float FX_VOLUME_TOWER_DESTROYED = 0.75f;
}
