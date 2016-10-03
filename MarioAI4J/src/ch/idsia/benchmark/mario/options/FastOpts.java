package ch.idsia.benchmark.mario.options;

import ch.idsia.benchmark.mario.engine.SimulatorOptions.ReceptiveFieldMode;
import ch.idsia.benchmark.mario.engine.generalization.Enemy;
import ch.idsia.benchmark.mario.engine.generalization.EntityGeneralizer;
import ch.idsia.benchmark.mario.engine.generalization.TileGeneralizer;
import ch.idsia.benchmark.mario.environments.MarioEnvironment;
import ch.idsia.benchmark.mario.options.MarioOptions.BoolOption;
import ch.idsia.benchmark.mario.options.MarioOptions.IntOption;
import ch.idsia.benchmark.mario.options.MarioOptions.StringOption;

/**
 * For your convinience - build options by connecting respective options or use FAST_xxx prebuilt options.
 * <br/><br/> 
 * For complete list of parameters / options inspect {@link MarioOptions} class.
 * 
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class FastOpts {
	
	static {
		// FORCE MarioOptions TO INITIALIZE FIRST! So we can use all the parameters as needed.
		MarioOptions.javaInit();
	}
	
	// ==========
	// AI OPTIONS
	// ==========
	
	/**
	 * Generalization level for TILEs, see {@link TileGeneralizer}.
	 * @param level
	 * @return
	 */
	public static String AI_TILE_Z(int level) {
		return " " + IntOption.AI_TILE_GENERALIZATION_ZLEVEL.getParam() + " " + level;
	}
	
	/**
	 * Generalization level for ENTITYies, see {@link EntityGeneralizer}.
	 */
	public static String AI_ENTITY_Z(int level) {
		return " " + IntOption.AI_ENTITY_GENERALIZATION_ZLEVEL.getParam() + " " + level;
	}
	
	/**
	 * Generalization levels: Tiles = 0, Entities = 0
	 */
	public static final String AI_ZL_0_0 = AI_TILE_Z(0) + AI_ENTITY_Z(0);
	
	/**
	 * Generalization levels: Tiles = 1, Entities = 0
	 */
	public static final String AI_ZL_1_0 = AI_TILE_Z(1) + AI_ENTITY_Z(0);
	
	/**
	 * Generalization levels: Tiles = 1, Entities = 1
	 */
	public static final String AI_ZL_1_1 = AI_TILE_Z(1) + AI_ENTITY_Z(1);
	
	/**
	 * Mario receptive field, see {@link MarioEnvironment#reset()}
	 * @param width
	 * @param height
	 * @return
	 */
	public static String AI_RECEPTIVE_FIELD(int width, int height) {
		return " " + IntOption.AI_RECEPTIVE_FIELD_WIDTH.getParam() + " " + width + IntOption.AI_RECEPTIVE_FIELD_HEIGHT.getParam() + " " + height;
	}
	
	// =======================
	// LEVEL GENERATOR OPTIONS
	// =======================
	
	/**
	 * Graphics will be rendered with "overground" tile set.  
	 */
	public static final String L_OVERGROUND = ""
					+ " " + IntOption.LEVEL_TYPE.getParam() + " 0";
	
	/**
	 * Graphics will be rendered with "underground" tile set. Also such a level contain ceiling.  
	 */
	public static final String L_UNDERGROUND = ""
			+ " " + IntOption.LEVEL_TYPE.getParam() + " 1";
	
	/**
	 * Graphics will be rendered with "castle" tile set.  
	 */
	public static final String L_CASTLE = ""
			+ " " + IntOption.LEVEL_TYPE.getParam() + " 2";
	
	/**
	 * Level will be generated as 'length'-tile long.
	 * @param lenght
	 * @return
	 */
	public static String L_LENGTH(int length) {
		return " " + IntOption.LEVEL_LENGTH.getParam() + " " + length;
	}
	
	/**
	 * Level will be generated as 256 tiles long.
	 */
	public static final String L_LENGTH_256 = ""
			+ " " + IntOption.LEVEL_LENGTH.getParam() + " 256";
	
	/**
	 * Level will be generated as 512 tiles long.
	 */
	public static final String L_LENGTH_512 = ""
			+ " " + IntOption.LEVEL_LENGTH.getParam() + " 512";
	
	/**
	 * Level will be generated as 1024 tiles long.
	 */
	public static final String L_LENGTH_1024 = ""
			+ " " + IntOption.LEVEL_LENGTH.getParam() + " 1024";
	
	/**
	 * Enable generation of blocks.
	 */
	public static final String L_BLOCKS_ON = ""
			+ " " + BoolOption.LEVEL_BLOCKS.getParam() + " on";
	
	/**
	 * Disable generation of blocks.
	 */
	public static final String L_BLOCKS_OFF = ""
			+ " " + BoolOption.LEVEL_BLOCKS.getParam() + " off";
	
	/**
	 * Enable generation of cannons.
	 */
	public static final String L_CANNONS_ON = ""
			+ " " + BoolOption.LEVEL_CANNONS.getParam() + " on";
	
	/**
	 * Disable generation of cannons.
	 */
	public static final String L_CANNONS_OFF = ""
			+ " " + BoolOption.LEVEL_CANNONS.getParam() + " off";
	
	/**
	 * Enable generation of coins.
	 */
	public static final String L_COINS_ON = ""
			+ " " + BoolOption.LEVEL_COINS.getParam() + " on";
	
	/**
	 * Disable generation of coins.
	 */
	public static final String L_COINS_OFF = ""
			+ " " + BoolOption.LEVEL_COINS.getParam() + " off";
	
	/**
	 * Enable generation of dead ends.
	 */
	public static final String L_DEAD_ENDS_ON = ""
			+ " " + BoolOption.LEVEL_DEAD_ENDS.getParam() + " on";
	
	/**
	 * Disable generation of dead ends.
	 */
	public static final String L_DEAD_ENDS_OFF = ""
			+ " " + BoolOption.LEVEL_DEAD_ENDS.getParam() + " off";
	
	/**
	 * Generate level as FLAT (no need to set any other options).
	 */
	public static final String L_FLAT = ""
			+ " " + BoolOption.LEVEL_FLAT.getParam() + " on";
	
	/**
	 * DO NOT generate level as FLAT.
	 */
	public static final String L_FLAT_OFF = ""
			+ " " + BoolOption.LEVEL_FLAT.getParam() + " off";
	
	/**
	 * Enable generation of gaps.
	 */
	public static final String L_GAPS_ON = ""
			+ " " + BoolOption.LEVEL_GAPS.getParam() + " on";
	
	/**
	 * Disable generation of gaps.
	 */
	public static final String L_GAPS_OFF = ""
			+ " " + BoolOption.LEVEL_GAPS.getParam() + " off";
	
	/**
	 * Enable generation of hidden blocks.
	 */
	public static final String L_HIDDEN_BLOCKS_ON = ""
			+ " " + BoolOption.LEVEL_HIDDEN_BLOCKS.getParam() + " on";
	
	/**
	 * Disable generation of hidden blocks.
	 */
	public static final String L_HIDDEN_BLOCKS_OFF = ""
			+ " " + BoolOption.LEVEL_HIDDEN_BLOCKS.getParam() + " off";
	
	/**
	 * Enable generation of platforms over the ground (ones you can jump on from below)..
	 */
	public static final String L_PLATFORMS_ON = ""
			+ " " + BoolOption.LEVEL_PLATFORMS.getParam() + " on";
	
	/**
	 * Disable generation of platforms over the ground (ones you can jump on from below).
	 */
	public static final String L_PLATFORMS_OFF= ""
			+ " " + BoolOption.LEVEL_PLATFORMS.getParam() + " off";
	
	/**
	 * Enable generation of ladders.
	 */
	public static final String L_LADDERS_ON = ""
			+ " " + BoolOption.LEVEL_LADDERS.getParam() + " on";
	
	/**
	 * Disable generation of ladders.
	 */
	public static final String L_LADDERS_OFF= ""
			+ " " + BoolOption.LEVEL_LADDERS.getParam() + " off";
	
	/**
	 * Generate random level (randomize the seed).
	 */
	public static final String L_RANDOMIZE = ""
			+ " " + IntOption.LEVEL_RANDOM_SEED.getParam() + " -1";
	
	/**
	 * Enable generation of tubes.
	 */
	public static final String L_TUBES_ON = ""
			+ " " + BoolOption.LEVEL_TUBES.getParam() + " on";
	
	/**
	 * Disable generation of tubes.
	 */
	public static final String L_TUBES_OFF= ""
			+ " " + BoolOption.LEVEL_TUBES.getParam() + " off";

	/**
	 * Level difficulty 0-10 (default: 0).
	 * @param i
	 * @return
	 */
	public static String L_DIFFICULTY(int i) {
		return " " + IntOption.LEVEL_DIFFICULTY.getParam() + " " + i;
	}
	
	public static String L_ENEMY(Enemy... enemies) {
		if (enemies == null || enemies.length == 0) return "";
		
		String result = " " + StringOption.LEVEL_CREATURES.getParam() + " ";
	
		boolean first = true;
		for (Enemy enemy : enemies) {
			if (first) first = false;
			else result += ",";
			result += enemy.getShorthand();
		}
		
		return result;
	}

	public static String L_RANDOM_SEED(int seed) {
		return " " + IntOption.LEVEL_RANDOM_SEED.getParam() + " " + seed;
	}
	
	// ==================
	// SIMULATION OPTIONS
	// ==================
	
	/**
	 * Mario will start SMALL.
	 */
	public static final String S_MARIO_SMALL = ""
					+ " " + IntOption.SIMULATION_MARIO_START_MODE.getParam() + " 0";
	
	/**
	 * Mario will start LARGE.
	 */
	public static final String S_MARIO_LARGE = ""
			+ " " + IntOption.SIMULATION_MARIO_START_MODE.getParam() + " 1";
	
	/**
	 * Mario will start LARGE and will be able to FIRE.
	 */
	public static final String S_MARIO_FIRE = ""
			+ " " + IntOption.SIMULATION_MARIO_START_MODE.getParam() + " 2";
	
	/**
	 * Time limit for the level will be set to 200 marioseconds (suited for 256-length levels).
	 */
	public static final String S_TIME_LIMIT_200 = ""
			+ " " + IntOption.SIMULATION_TIME_LIMIT.getParam() + " 200";
	
	/**
	 * Time limit for the level will be set to 200 marioseconds (suited for 512-length levels).
	 */
	public static final String S_TIME_LIMIT_400 = ""
			+ " " + IntOption.SIMULATION_TIME_LIMIT.getParam() + " 400";
	
	/**
	 * Time limit for the level will be set to 200 marioseconds (suited for 1024-length levels).
	 */
	public static final String S_TIME_LIMIT_800 = ""
			+ " " + IntOption.SIMULATION_TIME_LIMIT.getParam() + " 800";
	
	/**
	 * Creatures will not be moving as usual, they will be frozen at positions they were generated at.
	 */
	public static final String S_FREEZE_CREATURES = ""
			+ " " + BoolOption.SIMULATION_FROZEN_CREATURES.getParam() + " on";
	
	/**
	 * Mario will be invulnerable for the whole time of its run.
	 */
	public static final String S_MARIO_INVULNERABLE = ""
			+ " " + BoolOption.SIMULATION_MARIO_INVULNERABLE.getParam() + " on";
	
	/**
	 * Mario can get its powers back by pressing SPEED.
	 */
	public static final String S_POWER_RESTORATION = ""
			+ " " + BoolOption.SIMULATION_POWER_RESTORATION.getParam() + " on";
	
	/**
	 * Simulation starts frozen from the start ... you have to press SPACE to unfreeze it.
	 */
	public static final String S_GAMEPLAY_STOPEED = ""
			+ " " + BoolOption.SIMULATION_GAMEPLAY_STOPPED.getParam() + " on";
	
	// =====================
	// VISUALIZATION OPTIONS
	// =====================
	
	/**
	 * Enable visualization.
	 */
	public static final String VIS_ON = ""
			        + " " + BoolOption.VISUALIZATION.getParam() + " on";
	
	/**
	 * Disable visualization in order to run HEADLESS (simulator is ticking much much faster, evaluates an agent almost immediatelly).
	 */
	public static final String VIS_OFF = ""
			        + " " + BoolOption.VISUALIZATION.getParam() + " off";
	
	/**
	 * Auto-scale the visualization by 2x. (I.e. the window will be 2x larger in both dimensions.)
	 */
	public static final String VIS_SCALE_2X = ""
					+ " " + BoolOption.VISUALIZATION_2X_SCALE.getParam() + " on";
	
	/**
	 * Enable visualization + Auto-scale the visualization by 2x. (I.e. the window will be 2x larger in both dimensions.)
	 */
	public static final String VIS_ON_2X = VIS_ON + VIS_SCALE_2X;
	
	/**
	 * Whether to visualize receptive field around Mario.
	 * @param mode
	 * @return
	 */
	public static String VIS_FIELD(ReceptiveFieldMode mode) {
		if (mode == null) return "";
		return " " + IntOption.VISUALIZATION_RECEPTIVE_FIELD.getParam() + " " + mode.getCode();
	}
	
	// ==============
	// FAST SHORTCUTS
	// ==============
	
	/**
	 * Flat level. WARNING: enemy cannot be generated into this level ({@link #L_ENEMY(Enemy...)} will not work with this option).
	 */
	public static final String LEVEL_01_FLAT = L_FLAT;
	
	/**
	 * Level with hills.
	 */
	public static final String LEVEL_02_JUMPING = L_FLAT_OFF + L_BLOCKS_OFF + L_CANNONS_OFF + L_COINS_OFF + L_DEAD_ENDS_OFF + L_GAPS_OFF + L_HIDDEN_BLOCKS_OFF
												  	          + L_PLATFORMS_OFF + L_LADDERS_OFF + L_TUBES_OFF;
	
	/**
	 * Level with hills + coins.
	 */
	public static final String LEVEL_03_COLLECTING = L_FLAT_OFF + L_BLOCKS_OFF + L_CANNONS_OFF + L_COINS_ON + L_DEAD_ENDS_OFF + L_GAPS_OFF + L_HIDDEN_BLOCKS_OFF
	  	       												     + L_PLATFORMS_OFF + L_LADDERS_OFF + L_TUBES_OFF;
	
	/**
	 * Level with hills + block (can be crushed) + coins.
	 */
	public static final String LEVEL_04_BLOCKS = L_FLAT_OFF + L_BLOCKS_ON + L_CANNONS_OFF + L_COINS_ON + L_DEAD_ENDS_OFF + L_GAPS_OFF + L_HIDDEN_BLOCKS_OFF
													         + L_PLATFORMS_OFF + L_LADDERS_OFF + L_TUBES_OFF;
	
	/**
	 * Level with hills + gaps (holes in the ground).
	 */
	public static final String LEVEL_05_GAPS = L_FLAT_OFF + L_BLOCKS_OFF + L_CANNONS_OFF + L_COINS_OFF + L_DEAD_ENDS_OFF + L_GAPS_ON + L_HIDDEN_BLOCKS_OFF
	         										       + L_PLATFORMS_OFF + L_LADDERS_OFF + L_TUBES_OFF + L_DIFFICULTY(1);	
	
	/**
	 * Level with hills + GOOMBA (walking mushroom).
	 */
	public static final String LEVEL_06_GOOMBA = L_FLAT_OFF + L_BLOCKS_OFF + L_CANNONS_OFF + L_COINS_OFF + L_DEAD_ENDS_OFF + L_GAPS_OFF + L_HIDDEN_BLOCKS_OFF
		       														     + L_PLATFORMS_OFF + L_LADDERS_OFF + L_TUBES_OFF + L_ENEMY(Enemy.GOOMBA);
	
	/**
	 * Level with hills + GOOMBA (walking mushroom) + SPIKY (walking thorned something) + Visualization(x2)
	 */
	public static final String LEVEL_07_SPIKY = L_FLAT_OFF + L_BLOCKS_OFF + L_CANNONS_OFF + L_COINS_OFF + L_DEAD_ENDS_OFF + L_GAPS_OFF + L_HIDDEN_BLOCKS_OFF
			     										    + L_PLATFORMS_OFF + L_LADDERS_OFF + L_TUBES_OFF + L_ENEMY(Enemy.GOOMBA, Enemy.SPIKY);
	
}
