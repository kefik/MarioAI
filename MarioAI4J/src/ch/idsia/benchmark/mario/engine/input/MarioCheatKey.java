package ch.idsia.benchmark.mario.engine.input;

import ch.idsia.benchmark.mario.MarioSimulator;

/**
 * Represents cheats that are "hacked in" the {@link MarioSimulator} or {@link VisualizationComponent}.
 * 
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class MarioCheatKey {

	// NOT IMPLEMENTED, pause is hacked within {@link MarioAIHijackBase}
	//public static final MarioKey CHEAT_KEY_PAUSE              = new MarioKey(6,  "CHEAT_KEY_PAUSE",              "C_KP");
	
	// NOT IMPLEMENTED
	//public static final MarioKey CHEAT_KEY_DUMP_CURRENT_WORLD = new MarioKey(7,  "CHEAT_KEY_DUMP_CURRENT_WORLD", "C_KDCW");
	
	
	// NOT IMPLEMENTED
	//public static final MarioKey CHEAT_KEY_LIFE_UP            = new MarioKey(8,  "CHEAT_KEY_LIFE_UP",            "C_KLU");
	
	public static final MarioKey CHEAT_KEY_WIN                = new MarioKey(9,  "CHEAT_KEY_WIN",                "C_W");
	
	// NOT IMPLEMENTED
	//public static final MarioKey CHEAT_KEY_OBSERVE_LEVEL      = new MarioKey(10, "CHEAT_KEY_WIN",                "C_OL");
	
}
