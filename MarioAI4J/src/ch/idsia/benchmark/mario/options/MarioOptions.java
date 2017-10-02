package ch.idsia.benchmark.mario.options;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.idsia.benchmark.mario.MarioSimulator;
import ch.idsia.benchmark.mario.engine.level.LevelGenerator;
import ch.idsia.utils.MarioLog;

/**
 * Serves as a storage for all options that can adjust Mario AI / Level Generation / Simulation / System / Visualization.
 * <br/><br/>
 * These options can be used to initialize {@link MarioSimulator}.
 * <br/><br/>
 * You might want to use {@link FastOpts} to specify your custom option chain.
 * 
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class MarioOptions {
	
	/**
	 * This can be used to FORCE static initialization of MarioOptions.
	 */
	public static void javaInit() {		
	}
	
	private static Map<String, Object> options;
	
	public static enum BoolOption {
		
		AI_PUNCTUAL_JUDGE("punj", false, "Use Punctual Judge."),
						
		LEVEL_LADDERS("lla", false, "Allow generation of ladders within the level (arguments: on / off)."),
		
		/**
		 * Number of dead-ends determined by {@link LevelGenerator#odds} see {@link LevelGenerator#createLevel()}.
		 */
		LEVEL_DEAD_ENDS("lde", false, "Allow generation of dead ends (arguments: on / off)."),
		
		/**
		 * Number of cannons determined by {@link LevelGenerator#odds} see {@link LevelGenerator#createLevel()}.
		 */
		LEVEL_CANNONS("lca", true, "Allow generation of cannons (arguments: on / off)."),
		
		/**
		 * Number of platforms determined by {@link LevelGenerator#odds} see {@link LevelGenerator#createLevel()}.
		 */
		LEVEL_PLATFORMS("lhs", true, "Allow generation of platforms over the ground, ones you can jump on from below (arguments: on / off)."),
		
		/**
		 * Number of tubes determined by {@link LevelGenerator#odds} see {@link LevelGenerator#createLevel()}.
		 */
		LEVEL_TUBES("ltb", true, "Allow generation of tubes (arguments: on / off)."),
		LEVEL_COINS("lco", true, "Allow generation of coins (arguments: on / off)."),
		LEVEL_BLOCKS("lb", true, "Allow generation of blocks (arguments: on / off)."),
		
		/**
		 * Number of gaps determined by {@link LevelGenerator#odds} see {@link LevelGenerator#createLevel()}.
		 */
		LEVEL_GAPS("lg", true, "Allow generation of gaps (arguments: on / off)."),
		LEVEL_HIDDEN_BLOCKS("lhb", false, "Allow generation of hidden blocks (arguments: on / off)."),
		LEVEL_FLAT("lf", false, "Make the level flat (arguments: on / off) - auto-reset all required options."),
		
		SIMULATION_FROZEN_CREATURES("fc", false, "All creatures will be frozen, i.e., they won't move."),
		SIMULATION_MARIO_INVULNERABLE("i", false, "Whether Mario should be invincible."),
		SIMULATION_POWER_RESTORATION("pr", false, "Whether the mario should gain FIRE every time it triggers speed."),
		SIMULATION_GAMEPLAY_STOPPED("stop", false, "Gameplay is stopped by default."),
		
//		SYSTEM_TOOLS_CONFIGURATOR("tc", false, "Whether to enable tools configurator (???)."),
		
		VISUALIZATION("vis", true, "Whether to visualize the simulation."),
		VISUALIZATION_VIEW_ALWAYS_ON_TOP("vaot", true, "Whether to maintain visualizer window always on top."),
		VISUALIZATION_2X_SCALE("z", false, "Scale visualization 2x by default."),
		VISUALIZATION_GAME_VIEWER("gv", false, "Whether to initialize game viewer (???)."),
		VISUALIZATION_GAME_VIEWER_CONTINUOUS_UPDATED("gvc", false, "Whether to continuously update the game viewer (???)."),
		;
			
		private String param;
		private String help;
		private boolean defaultValue;

		private BoolOption(String param, boolean defaultValue, String help) {
			this.param = param;
			this.defaultValue = defaultValue;
			this.help = help;
			if (options == null) options = new HashMap<String, Object>();
			options.put(param, this);
		}

		public String getParam() {
			return param;
		}

		public String getHelp() {
			return help;
		}

		public boolean getDefaultValue() {
			return defaultValue;
		}
				
	}
	
	public static enum FloatOption {
		
		SIMULATION_GRAVITY_CREATURES("cgr", 1.0f, "Gravity constant used for creatures."),
		SIMULATION_GRAVITY_MARIO("mgr", 1.0f, "Gravity constant used for Mario."),
		SIMULATION_WIND_CREATURES("w", 0.0f, "Wind applied to creatures movement."),
		SIMULATION_WIND_MARIO("ice", 0.0f, "Wind applied to Mario movement."),
		SIMULATION_MARIO_JUMP_POWER("jp", 7.0f, "Mario jump power, how high Mario can jump to."),		
		;
			
		private String param;
		private String help;
		private float defaultValue;

		private FloatOption(String param, float defaultValue, String help) {
			this.param = param;
			this.defaultValue = defaultValue;
			this.help = help;
			if (options == null) options = new HashMap<String, Object>();
			options.put(param, this);
		}

		public String getParam() {
			return param;
		}

		public String getHelp() {
			return help;
		}

		public float getDefaultValue() {
			return defaultValue;
		}
		
	}
	
	public static enum IntOption {
		
		AI_RECEPTIVE_FIELD_HEIGHT("rfh", 19, "Receptive field tile height."),
		AI_RECEPTIVE_FIELD_WIDTH("rfw", 19, "Receptive field tile width."),
		AI_TILE_GENERALIZATION_ZLEVEL("zs", 0, "Tile generalization Z-Level."),
		AI_ENTITY_GENERALIZATION_ZLEVEL("ze", 0, "Entity generalization Z-Level."),
		AI_MARIO_EGO_ROW("mer", 9, "Mario ego row within reception field."),
		AI_MARIO_EGO_COLUMN("mec", 9, "Mario ego column within reception field."),
		
		LEVEL_GREEN_MUSHROOM_MODE("gmm", 0, "Green mushroom mode (currently does not do anything)."),		
		LEVEL_DIFFICULTY("ld", 0, "Level difficulty."),
		LEVEL_LENGTH("ll", 256, "Level length (tiles)."),
		LEVEL_RANDOM_SEED("ls", 0, "Level random seed. If >= 0 a concrete value will be used, if < 0, random value is used."),
		LEVEL_TYPE("lt", 0, "Level type (0 = TYPE_OVERGROUND, 1 = TYPE_UNDERGROUND, 2 = TYPE_CASTLE."),		
		LEVEL_MARIO_INITIAL_POSITION_X("mix", 32, "Mario spawn position X (in pixels)."),
		LEVEL_MARIO_INITIAL_POSITION_Y("miy", 32, "Mario spawn position Y (in pixels)."),		
		LEVEL_HEIGHT("lh", 15, "Level height."),
		LEVEL_EXIT_X("ex", 0, "Position-X of the exit within the level in tiles (if [0,0] -> moved to the right end of the level."),
		LEVEL_EXIT_Y("ey", 0, "Position-Y of the exit within the level in tiles (if [0,0] -> moved to the right end of the level."),
		
		SIMULATION_MARIO_START_MODE("mm", 2, "Mario start mode, 2 -> FIRE, 1 -> LARGE, 0 -> SMALL."),
		SIMULATION_TIME_LIMIT("tl", 200, "How many marioseconds Mario has to finish the level."),
		
		VISUALIZATION_FPS("fps", 24, "FPS of visualization."),
		VISUALIZATION_VIEW_LOCATION_X("vlx", 0, "Visualization window location x (in pixels)."),
		VISUALIZATION_VIEW_LOCATION_Y("vly", 0, "Visualization window location y (in pixels)."),
		VISUALIZATION_VIEWPORT_WIDTH("vw", 320, "Viewport width."),
		VISUALIZATION_VIEWPORT_HEIGHT("vh", 240, "Viewport height."),	
		VISUALIZATION_RECEPTIVE_FIELD("srf", 0, "Receptive field visualization type: 0 = none, 1 = GRID, 2 = GRID+TILES, 3 = GRID+ENTITIES"),		
		;
			
		private String param;
		private String help;
		private int defaultValue;

		private IntOption(String param, int defaultValue, String help) {
			this.param = param;
			this.defaultValue = defaultValue;
			this.help = help;
			if (options == null) options = new HashMap<String, Object>();
			options.put(param, this);
		}

		public String getParam() {
			return param;
		}

		public String getHelp() {
			return help;
		}

		public int getDefaultValue() {
			return defaultValue;
		}
		
	}

	public static enum StringOption {
		
		/**
		 * Does not work for {@link BoolOption#LEVEL_FLAT} levels.
		 */
		LEVEL_CREATURES("le", "off", "Which creatures to generate, GOOMBA => g, GOOMBA_WINGED => gw, RED_KOOPA => gk, RED_KOOPA_WINGED => gkw, GREEN_KOOPA => rk, GREEN_KOOPA_WINGED => rkw, SPIKY => s, SPIKY_WINGED => sw, WAVE_GOOMBA => gww"),
		
		SYSTEM_RECORDING_FILE_NAME("rec", "off", "Where to save the replay."),
		SYSTEM_SAVE_LEVEL_FILE_NAME("s", "off", "Where to save the level."),
		SYSTEM_LOAD_LEVEL_FILE_NAME("llf", "off", "Where to load the level from."),
		SYSTEM_TRACE_FILE_NAME("trace", "off", "Where to save Mario trace, output to std and specified file."),		
		
		;
		
		private String param;
		private String help;
		private String defaultValue;

		private StringOption(String param, String defaultValue, String help) {
			this.param = param;
			this.defaultValue = defaultValue;
			this.help = help;
			if (options == null) options = new HashMap<String, Object>();
			options.put(param, this);
		}

		public String getParam() {
			return param;
		}

		public String getHelp() {
			return help;
		}

		public String getDefaultValue() {
			return defaultValue;
		}
		
	}
	
	private static MarioOptions _instance = new MarioOptions();
	
	static {
		// force enum initialization ... otherwise, respective enum constructors need not to be invoked and we would not be able to parse arguments
		BoolOption.AI_PUNCTUAL_JUDGE.name();
		IntOption.AI_ENTITY_GENERALIZATION_ZLEVEL.name();
		FloatOption.SIMULATION_GRAVITY_CREATURES.name();
		StringOption.LEVEL_CREATURES.name();
	}
	
	private Map<BoolOption,   Boolean> bools   = new HashMap<BoolOption,   Boolean>();
	private Map<IntOption,    Integer> ints    = new HashMap<IntOption,    Integer>();
	private Map<FloatOption,  Float>   floats  = new HashMap<FloatOption,  Float>();
	private Map<StringOption, String>  strings = new HashMap<StringOption, String>();
		
	private MarioOptions() {		
	}
	
	public static MarioOptions getInstance() {
		return _instance;
	}
	
	public boolean getBool(BoolOption option) {
		if (bools.containsKey(option)) {
			boolean value = bools.get(option);
			MarioLog.trace("[MarioOptions] " + option.name() + "[-" + option.param + "] specified as: " + value);
			return value;
		}
		
		MarioLog.trace("[MarioOptions] " + option.name() + "[-" + option.param + "] default value used: " + option.defaultValue);
		
		return option.defaultValue;
	}
	
	public void setBool(BoolOption option, String strValue) {
		boolean value = ("on".equalsIgnoreCase(strValue) || "true".equals(strValue) ? true : false); 
		bools.put(option, value);
		MarioLog.trace("[MarioOptions] " + option.name() + "[-" + option.param + "] set as: " + value);
	}
	
	public void setBool(BoolOption option, boolean value) {
		bools.put(option, value);
		MarioLog.trace("[MarioOptions] " + option.name() + "[-" + option.param + "] set as: " + value);
	}
	
	public int getInt(IntOption option) {
		if (ints.containsKey(option)) {
			int value = ints.get(option);
			MarioLog.trace("[MarioOptions] " + option.name() + "[-" + option.param + "] specified as: " + value);
			return value;
		}
		
		MarioLog.trace("[MarioOptions] " + option.name() + "[-" + option.param + "] default value used: " + option.defaultValue);
		
		return option.defaultValue;
	}
	
	public void setInt(IntOption option, String strValue) {
		int value = 0;
		try {
			value = Integer.parseInt(strValue);
		} catch (Exception e) {
			MarioLog.error("[MarioOptions] ~ INVALID VALUE, CANNOT SET " + option.name() + "[-" + option.param + "] AS: " + strValue);
			return;
		}
		ints.put(option, value);
		MarioLog.trace("[MarioOptions] " + option.name() + "[-" + option.param + "] set as: " + value);
	}
	
	public void setInt(IntOption option, int value) {
		ints.put(option, value);
		MarioLog.trace("[MarioOptions] " + option.name() + "[-" + option.param + "] set as: " + value);
	}
	
	public float getFloat(FloatOption option) {
		if (floats.containsKey(option)) {
			float value = floats.get(option);
			MarioLog.trace("[MarioOptions] " + option.name() + "[-" + option.param + "] specified as: " + value);
			return value;
		}
		
		MarioLog.trace("[MarioOptions] " + option.name() + "[-" + option.param + "] default value used: " + option.defaultValue);
		
		return option.defaultValue;
	}
	
	public void setFloat(FloatOption option, String strValue) {
		float value = 0;
		try {
			value = Float.parseFloat(strValue);
		} catch (Exception e) {
			MarioLog.error("[MarioOptions] ~ INVALID VALUE, CANNOT SET " + option.name() + "[-" + option.param + "] AS: " + strValue);
			return;
		}
		floats.put(option, value);
		MarioLog.trace("[MarioOptions] " + option.name() + "[-" + option.param + "] set as: " + value);
	}
	
	public void setFloat(FloatOption option, float value) {
		floats.put(option, value);
		MarioLog.trace("[MarioOptions] " + option.name() + "[-" + option.param + "] set as: " + value);
	}
	
	public String getString(StringOption option) {
		if (strings.containsKey(option)) {
			String value = strings.get(option);
			MarioLog.trace("[MarioOptions] " + option.name() + "[-" + option.param + "] specified as: " + value);
			return value;
		}
		
		MarioLog.trace("[MarioOptions] " + option.name() + "[-" + option.param + "] default value used: " + option.defaultValue);
		
		return option.defaultValue;
	}
	
	public void setString(StringOption option, String value) {
		strings.put(option, value);
		MarioLog.trace("[MarioOptions] " + option.name() + "[-" + option.param + "] set as: " + value);
	}
	
	public void setOption(String argument, String value) {
		if (argument.startsWith("-")) argument = argument.substring(1);
		Object option = options.get(argument);
		if (option == null) {
			MarioLog.error("[MarioOptions] IGNORING UNRECOGNIZED PARAMETER [-" + argument + "] -> " + value);
		} else {
			setOption(option, value); 
		}
	}
	
	public void setOption(Object option, String value) {
		if (option instanceof BoolOption) {
			setBool((BoolOption)option, value);
		} else 
		if (option instanceof IntOption) {
			setInt((IntOption)option, value);
		} else
		if (option instanceof FloatOption) {
			setFloat((FloatOption)option, value);
		} else
		if (option instanceof StringOption) {
			setString((StringOption)option, value);
		} else {
			MarioLog.error("[MarioOptions] CANNOT SET " + option + " AS: " + value);
		}
	}
	
	// ==================
	// ADJUSTMENT METHODS
	// ==================
	
	public static void enable(BoolOption option) {
		getInstance().setBool(option, true);
	}
	
	public static void disable(BoolOption option) {
		getInstance().setBool(option, false);
	}
	
	public static void set(IntOption option, int value) {
		getInstance().setInt(option, value);
	}
	
	public static void set(FloatOption option, float value) {
		getInstance().setFloat(option, value);
	}
	
	public static void set(StringOption option, String value) {
		getInstance().setString(option, value);
	}
	
	public static void set(String argument, String value) {
		getInstance().setOption(argument, value);
	}
	
	/**
	 * Reads arguments from the string.
	 * @param args "-param1 value1 -param2 value2 ..." 
	 */
	public static void read(String... args) {
		if (args == null) return;
		
		List<String> processedArgs = new ArrayList<String>();
		for (String arg : args) {
			String[] split = arg.split(" ");
			for (String oneArg : split) {				
				if (oneArg.trim().length() == 0) continue;
				processedArgs.add(oneArg);
			}
		}
		args = processedArgs.toArray(new String[0]);		
		
		MarioLog.trace("[MarioOptions] Parsing " + args.length + " arguments ~ " + (args.length / 2) + " parameters:");
		for (String arg : args) {
			MarioLog.trace(" " + arg);
		}
		MarioLog.trace("");
		
		if (args.length % 2 != 0) {
			MarioLog.error("[MarioOptions] INVALID NUMBER OF ARGUMENTS (" + args.length + ")!");
			throw new RuntimeException("Invalid number of arguments (" + args.length + ").");
		}
				
		int index = 0;
		
		while (index+1 < args.length) {			
			String argument = args[index];
			String value = args[index+1];			

			MarioOptions.set(argument, value);
		
			index += 2;			
		}
		
		MarioLog.trace("[MarioOptions] Parameters parsed.");		
	}
	
	// ==============
	// INITIALIZATION 
	// ==============
	
	public static void reset(String... args) {
		read(args);
		reset();
	}
	
	public static void reset() {
		AIOptions.reset();
		LevelOptions.reset();
		SimulationOptions.reset();
		SystemOptions.reset();
		VisualizationOptions.reset();
	}
	
	// =====
	// UTILS
	// =====
	
	public static Object getOptionsAsStrings() {
		for (Object optionObj : options.keySet()) {
			
		}
		return null;
	}

	
}
