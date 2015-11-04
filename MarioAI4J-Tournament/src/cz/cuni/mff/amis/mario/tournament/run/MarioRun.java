package cz.cuni.mff.amis.mario.tournament.run;

import ch.idsia.agents.IAgent;
import ch.idsia.benchmark.mario.MarioSimulator;
import ch.idsia.tools.EvaluationInfo;
import cz.cuni.mff.amis.mario.tournament.MarioConfig;

public class MarioRun {
	
	private MarioConfig config;

	public MarioRun(MarioConfig config) {
		this.config = config;
	}
	
	public synchronized MarioRunResult run(IAgent agent) {
		MarioRunResult result = new MarioRunResult(config);
		for (int i = 0; i < config.getRepetitions(); ++i) {
			MarioSimulator simulator = new MarioSimulator(config.getOptions());
			EvaluationInfo info = simulator.run(agent);
			result.addResult(info);
		}
		return result;		
	}

}
