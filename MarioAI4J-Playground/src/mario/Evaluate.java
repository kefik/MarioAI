package mario;

import java.util.HashMap;
import java.util.Map;

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
		System.out.println("EVALUATING " + level.name());
		System.out.println("EVALUATING " + level.getOptions());
		
		MarioRunResults results = EvaluateAgentConsole.evaluate(getEvaluationOptions(seed, level));
		
		printResults(level, results);
		
		return results;
	}
	
	public static void printResults(LevelConfig level, MarioRunResults results) {
		System.out.println(level.name());
		System.out.println("  +-- VICTORIES:  " + results.totalVictories + " / " + results.getTotalRuns() + " (" + ((double)results.totalVictories / (double)results.getTotalRuns()) + "%)");
		System.out.println("  +-- AVG   TIME: " + ((double)results.totalTimeSpent / (double)results.getTotalRuns() ) + "s");
		System.out.println("  +-- TOTAL TIME: " + results.totalTimeSpent + "s");
		System.out.println("-------------------");
	}
	
	public static void evaluateLevels(int seed, LevelConfig... configs) {
		Map<LevelConfig, MarioRunResults> results = new HashMap<LevelConfig, MarioRunResults>();
		
		for (LevelConfig level : configs) {
			MarioRunResults r = evaluateLevel(seed, level);
			results.put(level,  r);
		}
		
		System.out.println("===================================");
		System.out.println("RESULTS (Maps per level: " + MAPS_COUNT + ", Map reptitions: " + MAP_REPETITIONS);
		System.out.println("===================================");
		
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
		
		System.out.println("=======");
		System.out.println("RESULTS");
		System.out.println("=======");
		
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
