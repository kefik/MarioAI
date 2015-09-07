package ch.idsia.benchmark.mario.options;

import java.awt.Point;

import ch.idsia.benchmark.mario.options.MarioOptions.BoolOption;
import ch.idsia.benchmark.mario.options.MarioOptions.IntOption;
import ch.idsia.benchmark.mario.options.MarioOptions.StringOption;

/**
 * Read Level-Generator values from {@link MarioOptions}.
 *  
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class LevelOptions {

	/**
	 * Auto-adjusts certain LEVEL-GENERATOR options.
	 * <br/><br/>
	 * Currently do not do anything, reserved for future use.
	 */
	public static void reset() {
		if (isFlat()) {
			MarioOptions.getInstance().setBool(BoolOption.LEVEL_BLOCKS, false);
			MarioOptions.getInstance().setBool(BoolOption.LEVEL_COINS, false);
			MarioOptions.getInstance().setBool(BoolOption.LEVEL_TUBES, false);
			MarioOptions.getInstance().setBool(BoolOption.LEVEL_GAPS, false);
			MarioOptions.getInstance().setBool(BoolOption.LEVEL_DEAD_ENDS, false);
			MarioOptions.getInstance().setBool(BoolOption.LEVEL_CANNONS, false);
			MarioOptions.getInstance().setString(StringOption.LEVEL_CREATURES, "off");
		}
	}
		
	public static boolean isLadders() {
		return MarioOptions.getInstance().getBool(BoolOption.LEVEL_LADDERS);
	}
	
	public static boolean isDeadEnds() {
		return MarioOptions.getInstance().getBool(BoolOption.LEVEL_DEAD_ENDS);
	}
	
	public static boolean isCannons() {
		return MarioOptions.getInstance().getBool(BoolOption.LEVEL_CANNONS);
	}
	
	public static boolean isHillStraight() {
		return MarioOptions.getInstance().getBool(BoolOption.LEVEL_PLATFORMS);
	}
	
	public static boolean isTubes() {
		return MarioOptions.getInstance().getBool(BoolOption.LEVEL_TUBES);
	}
	
	public static boolean isCoins() {
		return MarioOptions.getInstance().getBool(BoolOption.LEVEL_COINS);
	}
	
	public static boolean isBlocks() {
		return MarioOptions.getInstance().getBool(BoolOption.LEVEL_BLOCKS);
	}
	
	public static boolean isGaps() {
		return MarioOptions.getInstance().getBool(BoolOption.LEVEL_GAPS);
	}
	
	public static boolean isHiddenBlocks() {
		return MarioOptions.getInstance().getBool(BoolOption.LEVEL_HIDDEN_BLOCKS);
	}
	
	public static boolean isFlat() {
		return MarioOptions.getInstance().getBool(BoolOption.LEVEL_FLAT);
	}
	
	public static int getGreenMushroomMode() {
		return MarioOptions.getInstance().getInt(IntOption.LEVEL_GREEN_MUSHROOM_MODE);
	}
	
	public static int getDifficulty() {
		return MarioOptions.getInstance().getInt(IntOption.LEVEL_DIFFICULTY);
	}
	
	public static int getLevelLength() {
		return MarioOptions.getInstance().getInt(IntOption.LEVEL_LENGTH);
	}
	
	public static int getRandomSeed() {
		return MarioOptions.getInstance().getInt(IntOption.LEVEL_RANDOM_SEED);
	}
	
	public static int getLevelType() {
		return MarioOptions.getInstance().getInt(IntOption.LEVEL_TYPE);
	}
	
	public static Point getMarioInitialPosition() {
		return new Point(MarioOptions.getInstance().getInt(IntOption.LEVEL_MARIO_INITIAL_POSITION_X), MarioOptions.getInstance().getInt(IntOption.LEVEL_MARIO_INITIAL_POSITION_Y));
	}
	
	public static int getLevelHeight() {
		return MarioOptions.getInstance().getInt(IntOption.LEVEL_HEIGHT);
	}
	
	public static int[] getLevelExit() {
		return new int[]{MarioOptions.getInstance().getInt(IntOption.LEVEL_EXIT_X), MarioOptions.getInstance().getInt(IntOption.LEVEL_EXIT_Y)};
	}

	public static boolean isLevelFileName() {
		String value = MarioOptions.getInstance().getString(StringOption.SYSTEM_LOAD_LEVEL_FILE_NAME);
		if (value.length() == 0) return false;
		if ("off".equalsIgnoreCase(value)) return false;
		if ("false".equalsIgnoreCase(value)) return false;
		return true;
	}
	
	public static String getLevelFileName() {
		return MarioOptions.getInstance().getString(StringOption.SYSTEM_LOAD_LEVEL_FILE_NAME);
	}
	
	public static String getEnemies() {
		return MarioOptions.getInstance().getString(StringOption.LEVEL_CREATURES);
	}
	
}
