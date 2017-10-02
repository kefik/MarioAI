package cz.cuni.mff.amis.mario.tournament;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Iterator;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;

import ch.idsia.agents.IAgent;
import ch.idsia.benchmark.mario.engine.generalization.Enemy;
import ch.idsia.benchmark.mario.options.FastOpts;
import ch.idsia.benchmark.mario.options.MarioOptions;
import ch.idsia.utils.MarioLog;
import cz.cuni.mff.amis.mario.tournament.run.MarioRunResults;

public class EvaluateAgentConsole {
	
	private static final char ARG_SEED_SHORT = 's';
	
	private static final String ARG_SEED_LONG = "seed";
	
	private static final char ARG_PROTOTYPE_OPTIONS_SHORT = 'o';
	
	private static final String ARG_PROTOTYPE_OPTIONS_LONG = "prototype-options";
	
	private static final char ARG_RUNS_COUNT_SHORT = 'c';
	
	private static final String ARG_RUNS_COUNT_LONG = "runs-count";
	
	private static final char ARG_ONE_RUN_REPETITIONS_SHORT = 'r';
	
	private static final String ARG_ONE_RUN_REPETITIONS_LONG = "one-run-repetitions";
	
	private static final char ARG_AGENT_FQCN_SHORT = 'a';
	
	private static final String ARG_AGENT_FQCN_LONG = "agent-fqcn";
	
	private static final char ARG_AGENT_ID_SHORT = 'i';
	
	private static final String ARG_AGNET_ID_LONG = "agent-id";
	
	private static final char ARG_RESULT_DIR_SHORT = 'd';
	
	private static final String ARG_RESULT_DIR_LONG = "result-dir";
	
	private static JSAP jsap;

	private static int seed = 0;

	private static String levelOptions;
	
	private static int runCount;
	
	private static int oneLevelRepetitions;
	
	private static String agentFQCN;
	
	private static Class<?> agentClass;
	
	private static IAgent agent;
	
	private static String agentId;
	
	private static String resultDir;
	
	private static File resultDirFile;

	private static boolean headerOutput = false;

	private static JSAPResult config;

		
	
	private static void fail(String errorMessage) {
		fail(errorMessage, null);
	}

	private static void fail(String errorMessage, Throwable e) {
		header();
		MarioLog.info("ERROR: " + errorMessage);
		MarioLog.info("");
		if (e != null) {
			e.printStackTrace();
			MarioLog.info("");
		}		
        MarioLog.info("Usage: java -jar marioai4j-tournament.jar ");
        MarioLog.info("                " + jsap.getUsage());
        MarioLog.info("");
        MarioLog.info(jsap.getHelp());
        MarioLog.info("");
        throw new RuntimeException("FAILURE: " + errorMessage);
	}

	private static void header() {
		if (headerOutput) return;
		MarioLog.info("");
		MarioLog.info("=========================");
		MarioLog.info("MarioAI4J Agent Evaluator");
		MarioLog.info("=========================");
		MarioLog.info("");
		headerOutput = true;
	}
		
	private static void initJSAP() throws JSAPException {
		jsap = new JSAP();
		    	
    	FlaggedOption opt1 = new FlaggedOption(ARG_AGENT_FQCN_LONG)
        	.setStringParser(JSAP.STRING_PARSER)
        	.setRequired(true) 
        	.setShortFlag(ARG_AGENT_FQCN_SHORT)
        	.setLongFlag(ARG_AGENT_FQCN_LONG);    
        opt1.setHelp("Agent fully-qualified class name to evaluate. Must be present on classpath.");
        
        jsap.registerParameter(opt1);
        
        FlaggedOption opt2 = new FlaggedOption(ARG_AGNET_ID_LONG)
	    	.setStringParser(JSAP.STRING_PARSER)
	    	.setRequired(true) 
	    	.setShortFlag(ARG_AGENT_ID_SHORT)
	    	.setLongFlag(ARG_AGNET_ID_LONG);    
	    opt2.setHelp("Id of the agent that will serve as identificator within results file.");
	
	    jsap.registerParameter(opt2);
	    
	    FlaggedOption opt3 = new FlaggedOption(ARG_ONE_RUN_REPETITIONS_LONG)
	    	.setStringParser(JSAP.INTEGER_PARSER)
	    	.setRequired(false)
	    	.setDefault("10")
	    	.setShortFlag(ARG_ONE_RUN_REPETITIONS_SHORT)
	    	.setLongFlag(ARG_ONE_RUN_REPETITIONS_LONG);    
	    opt3.setHelp("How many times should be one MarioSimulator configuration (~ one generated level map) be repeated (in order to gain statistically sound data).");
	    
	    jsap.registerParameter(opt3);
	    
	    FlaggedOption opt31 = new FlaggedOption(ARG_PROTOTYPE_OPTIONS_LONG)
	    	.setStringParser(JSAP.STRING_PARSER)
	    	.setRequired(true) 
	    	.setShortFlag(ARG_PROTOTYPE_OPTIONS_SHORT)
	    	.setLongFlag(ARG_PROTOTYPE_OPTIONS_LONG);    
	    opt31.setHelp("List of options to be used for generating the level map.");
	
	    jsap.registerParameter(opt31);
	    
	    FlaggedOption opt32 = new FlaggedOption(ARG_RESULT_DIR_LONG)
	    	.setStringParser(JSAP.STRING_PARSER)
	    	.setRequired(false)
	    	.setDefault("./results")
	    	.setShortFlag(ARG_RESULT_DIR_SHORT)
	    	.setLongFlag(ARG_RESULT_DIR_LONG);    
	    opt32.setHelp("Directory where to output results, will be created if not exist.");
	    
	    jsap.registerParameter(opt32);
	    
	    FlaggedOption opt33 = new FlaggedOption(ARG_RUNS_COUNT_LONG)
	    	.setStringParser(JSAP.INTEGER_PARSER)
	    	.setRequired(false)
	    	.setDefault("100")
	    	.setShortFlag(ARG_RUNS_COUNT_SHORT)
	    	.setLongFlag(ARG_RUNS_COUNT_LONG);    
	    opt33.setHelp("How many different levels should an agent be evaluated in.");
	
	    jsap.registerParameter(opt33);
    
	    FlaggedOption opt6 = new FlaggedOption(ARG_SEED_LONG)
	    	.setStringParser(JSAP.INTEGER_PARSER)
	    	.setRequired(false)
	    	.setDefault("0")
	    	.setShortFlag(ARG_SEED_SHORT)
	    	.setLongFlag(ARG_SEED_LONG);    
	    opt6.setHelp("Master seed - seed to be used when generating seeds for respective levels.");
	
	    jsap.registerParameter(opt6);
   	}

	private static void readConfig(String[] args) {
		MarioLog.info("Parsing command arguments.");
		
		try {
	    	config = jsap.parse(args);
	    } catch (Exception e) {
	    	fail(e.getMessage());
	    	MarioLog.info("");
	    	e.printStackTrace();
	    	throw new RuntimeException("FAILURE!");
	    }
		
		if (!config.success()) {
			String error = "Invalid arguments specified.";
			Iterator errorIter = config.getErrorMessageIterator();
			if (!errorIter.hasNext()) {
				error += "\n-- No details given.";
			} else {
				while (errorIter.hasNext()) {
					error += "\n-- " + errorIter.next();
				}
			}
			fail(error);
    	}

		seed = config.getInt(ARG_SEED_LONG);

		levelOptions = config.getString(ARG_PROTOTYPE_OPTIONS_LONG);
		
		runCount = config.getInt(ARG_RUNS_COUNT_LONG);
		
		oneLevelRepetitions = config.getInt(ARG_ONE_RUN_REPETITIONS_LONG);
		
		agentFQCN = config.getString(ARG_AGENT_FQCN_LONG);
		
		agentId = config.getString(ARG_AGNET_ID_LONG);
		
		resultDir = config.getString(ARG_RESULT_DIR_LONG);
	    
	}
	
	private static void sanityChecks() {
		MarioLog.info("Sanity checks...");
		
		// UT2004
		MarioLog.info("-- seed: " + seed);
		MarioLog.info("-- level options: " + levelOptions);
		MarioLog.info("-- run count: " + runCount);
		MarioLog.info("-- single level repetitions: " + oneLevelRepetitions);
		
		resultDirFile = new File(resultDir);
		MarioLog.info("-- result dir: " + resultDir + " --> " + resultDirFile.getAbsolutePath());
		
		if (!resultDirFile.exists()) {
			MarioLog.info("---- result dir does not exist, creating!");
			resultDirFile.mkdirs();
		}
		if (!resultDirFile.exists()) {
			fail("Result dir does not exists. Parsed as: " + resultDir + " --> " + resultDirFile.getAbsolutePath());
		}
		if (!resultDirFile.isDirectory()) {
			fail("Result dir is not a directory. Parsed as: " + resultDir + " --> " + resultDirFile.getAbsolutePath());
		}
		MarioLog.info("---- result directory exists, ok");
		
		MarioLog.info("-- resolving agent FQCN: " + agentFQCN);
		try {
			agentClass = Class.forName(agentFQCN);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (agentClass == null) {
			fail("Failed to find agent class: " + agentFQCN);
		}
		MarioLog.info("---- agent class found");
		Constructor agentCtor = null;
		try {
			agentCtor = agentClass.getConstructor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (agentCtor == null) {
			fail("Failed to locate parameterless constructor for agent class: " + agentClass.getName());
		}
		MarioLog.info("---- agent parameterless constructor found");
		try {
			agent = (IAgent) agentCtor.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (agent == null) {
			fail("Failed to construct the agent instance.");
		}
		
		MarioLog.info("---- agent instantiated");
		
	    MarioLog.info("Sanity checks OK!");
	}
	
	private static MarioRunResults evaluateAgent() {
		MarioLog.info("Evaluating...");
		EvaluateAgent evaluate = new EvaluateAgent(seed, levelOptions, runCount, oneLevelRepetitions, resultDirFile);
		return evaluate.evaluateAgent(agentId, agent);		
	}
		
	// ==============
	// TEST ARGUMENTS
	// ==============
	public static String[] getTestArgs() {
		return new String[] {
				  "-s", "20" // "seed"
				, "-o", // "level & visualization configuration" 
  				        //FastOpts.VIS_ON_2X + FastOpts.LEVEL_02_JUMPING + FastOpts.L_ENEMY(Enemy.GOOMBA) + FastOpts.L_TUBES_ON + FastOpts.L_RANDOMIZE
				        FastOpts.VIS_OFF + FastOpts.LEVEL_02_JUMPING + FastOpts.L_ENEMY(Enemy.GOOMBA, Enemy.SPIKY) + FastOpts.L_RANDOMIZE
				, "-c", "100"  // level-count
				, "-r", "5"  // one-run-repetitions
				, "-a", "ch.idsia.agents.controllers.examples.Agent04_Shooter" // agent-fqcn ... requires MarioAI4J-Agents on classpath!
				, "-i", "Shooter"   // agent-id
				, "-d", "./results" // result-dir"	
		};
	}
	
	public static MarioRunResults evaluate(String[] args) {
		// --------------
		// IMPLEMENTATION
		// --------------
		
		try {
				
			try {
				initJSAP();
			} catch (Exception e) {
				throw new RuntimeException("Failed to parse arguments.", e);
			}
				    
			header();
			    
			readConfig(args);
				    
			sanityChecks();
				    
			return evaluateAgent();
			
		} finally {
			cleanUp();
			MarioOptions.reset();
		}
	}
	
	
	private static void cleanUp() {
		seed = 0;
		levelOptions = null;
		runCount = 0;
		oneLevelRepetitions = 0;
		agentFQCN = null;
		agentClass = null;
		agent = null;
		agentId = null;
		resultDir = null;
		resultDirFile = null;
		headerOutput = false;
		config = null;
	}

	public static void main(String[] args) {
		// -----------
		// FOR TESTING
		// -----------
		//args = getTestArgs();		
		
	    evaluate(args);
	    
	    MarioLog.info("---// FINISHED //---");
	}

}
