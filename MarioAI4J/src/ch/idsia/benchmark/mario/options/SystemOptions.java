package ch.idsia.benchmark.mario.options;

import ch.idsia.benchmark.mario.options.MarioOptions.StringOption;

/**
 * Read System values from {@link MarioOptions}.
 * 
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class SystemOptions {

	/**
	 * Auto-adjusts certain SYSTEM options.
	 * <br/><br/>
	 * Currently do not do anything, reserved for future use.
	 */
	public static void reset() {		
	}
	
//	public static boolean isToolsConfigurator() {
//		return MarioOptions.getInstance().getBool(BoolOption.SYSTEM_TOOLS_CONFIGURATOR);
//	}	
	
	public static boolean isReplayFileName() {
		String value = MarioOptions.getInstance().getString(StringOption.SYSTEM_RECORDING_FILE_NAME);
		if (value.length() == 0) return false;
		if ("off".equalsIgnoreCase(value)) return false;
		if ("false".equalsIgnoreCase(value)) return false;
		return true;
	}
	
	public static String getReplayFileName() {
		return MarioOptions.getInstance().getString(StringOption.SYSTEM_RECORDING_FILE_NAME);
	}
	
	public static boolean isSaveLevelFileName() {
		String value = MarioOptions.getInstance().getString(StringOption.SYSTEM_SAVE_LEVEL_FILE_NAME);
		if (value.length() == 0) return false;
		if ("off".equalsIgnoreCase(value)) return false;
		if ("false".equalsIgnoreCase(value)) return false;
		return true;
	}
	
	public static String getSaveLevelFileName() {
		return MarioOptions.getInstance().getString(StringOption.SYSTEM_SAVE_LEVEL_FILE_NAME);
	}
	
	public static boolean isTraceFile() {
		String value = MarioOptions.getInstance().getString(StringOption.SYSTEM_TRACE_FILE_NAME);
		if (value.length() == 0) return false;
		if ("off".equalsIgnoreCase(value)) return false;
		if ("false".equalsIgnoreCase(value)) return false;
		return true;
	}
	
	public static String getTraceFileName() {
		if (!isTraceFile()) return "";
		String value = MarioOptions.getInstance().getString(StringOption.SYSTEM_TRACE_FILE_NAME);
		if ("on".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value)) {
			return "MarioAITrace.txt";
		}
		return value;
	}
	
}
