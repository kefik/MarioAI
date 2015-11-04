package cz.cuni.mff.amis.mario.tournament.run;

import java.util.ArrayList;
import java.util.List;

import cz.cuni.mff.amis.mario.tournament.EvaluationInfos;
import cz.cuni.mff.amis.mario.tournament.MarioConfig;

public class MarioRunResults extends EvaluationInfos {
	
	private List<MarioConfig> configs = new ArrayList<MarioConfig>();
	private List<MarioRunResult> runResults = new ArrayList<MarioRunResult>();
	
	public void addRunResults(MarioRunResult... results) {
		for (MarioRunResult result : results) {
			runResults.add(result);
			configs.add(result.getConfig());
			addResults(result.getResults());
		}
	}
	
	public void addRunResults(List<MarioRunResult> results) {
		for (MarioRunResult result : results) {
			runResults.add(result);
			configs.add(result.getConfig());
			addResults(result.getResults());
		}
	}

	public List<MarioRunResult> getRunResults() {
		return runResults;
	}

	public List<MarioConfig> getConfigs() {
		return configs;
	}
	
}
