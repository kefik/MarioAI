package mario;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import ch.idsia.utils.MarioLog;
import cz.cuni.mff.amis.mario.tournament.EvaluateAgentConsole;
import cz.cuni.mff.amis.mario.tournament.run.MarioRunResults;

/**
 * This class is designed to quickly evaluate quality of {@link MarioAgent} AI.
 * 
 * Check {@link #main(String[])} method where you can easily run evaluation for 
 * certain levels all for all level configs.
 * 
 * Note that if you add custom level config into {@link LevelConfig}, {@link #evaluateAll(int)} will 
 * automatically pick it up ;-)
 */
public class Evaluate {
	
	/**
	 * Hoa many randomized level configs we should evaluate per level.
	 */
	public static final int MAPS_COUNT = 200;
	
	/**
	 * How many times we should eveluate the same level configuration.
	 */
	public static final int MAP_REPETITIONS = 1;
	
	private static String[] getEvaluationOptions(int seed, String levelOptions) {
		return new String[] {
				  "-s", String.valueOf(seed) // "seed"
				, "-o", levelOptions
				, "-c", String.valueOf(MAPS_COUNT)  // level-count
				, "-r", String.valueOf(MAP_REPETITIONS)  // one-run-repetitions
				, "-a", "mario.MarioAgent"
				, "-i", "MarioAI"   // agent-id
				, "-d", "./results" // result-dir"	
		};
	}
	
	private static String[] getEvaluationOptions(int seed, LevelConfig level) {
		return getEvaluationOptions(seed, level.getOptionsVisualizationOff());		
	}
	
	public static MarioRunResults evaluateLevel(int seed, LevelConfig level) {
		MarioLog.info("EVALUATING " + level.name());
		MarioLog.info("EVALUATING " + level.getOptions());
		
		MarioRunResults results = EvaluateAgentConsole.evaluate(getEvaluationOptions(seed, level));
		
		printResults(level, results);
		
		return results;
	}
	
	public static void printResults(LevelConfig level, MarioRunResults results) {
		MarioLog.info(level.name());
		MarioLog.info("  +-- VICTORIES:  " + results.totalVictories + " / " + results.getTotalRuns() + " (" + (100 * (double)results.totalVictories / (double)results.getTotalRuns()) + "%)");
		MarioLog.info("  +-- AVG   TIME: " + ((double)results.totalTimeSpent / (double)results.getTotalRuns() ) + "s");
		MarioLog.info("  +-- TOTAL TIME: " + results.totalTimeSpent + "s");
		MarioLog.info("-------------------");
	}
	
	public static void evaluateLevels(int seed, LevelConfig... configs) {
		Map<LevelConfig, MarioRunResults> results = new HashMap<LevelConfig, MarioRunResults>();
		
		for (LevelConfig level : configs) {
			MarioRunResults r = evaluateLevel(seed, level);
			results.put(level,  r);
		}
		
		MarioLog.info("===================================");
		MarioLog.info("RESULTS (Maps per level: " + MAPS_COUNT + ", Map reptitions: " + MAP_REPETITIONS);
		MarioLog.info("===================================");
		
		for (LevelConfig level : configs) { 
			printResults(level, results.get(level));
		}
	}
	
	public static void evaluateAll(int seed) {
		Map<LevelConfig, MarioRunResults> results = new HashMap<LevelConfig, MarioRunResults>();
		
		for (LevelConfig level : LevelConfig.values()) {
			MarioRunResults r = evaluateLevel(seed, level);
			results.put(level,  r);
		}
		
		MarioLog.info("=======");
		MarioLog.info("RESULTS");
		MarioLog.info("=======");
		
		for (LevelConfig level : LevelConfig.values()) { 
			printResults(level, results.get(level));
		}
		
	}
	
	/**
	 * Simple way how to evaluate your {@link MarioAgent}.
	 * @param args
	 */
	public static void main(String[] args)  {
		// Change the seed to receive evaluation for different levels ~ it alters procedural generation of level maps.
		int masterSeed = 20;
		
		evaluateLevel(masterSeed, LevelConfig.LEVEL_0_FLAT);
		//evaluateLevel(masterSeed, LevelConfig.LEVEL_1_JUMPING);
		//evaluateLevel(masterSeed, LevelConfig.LEVEL_2_GOOMBAS);
		//evaluateLevel(masterSeed, LevelConfig.LEVEL_3_TUBES);
		//evaluateLevel(masterSeed, LevelConfig.LEVEL_4_SPIKIES);
		
		//evaluateLevels(masterSeed, LevelConfig.LEVEL_0_FLAT, LevelConfig.LEVEL_1_JUMPING, LevelConfig.LEVEL_2_GOOMBAS);
		
		//evaluateAll(masterSeed);
	}

}
