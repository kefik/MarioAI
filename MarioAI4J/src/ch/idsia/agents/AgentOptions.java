package ch.idsia.agents;

import ch.idsia.benchmark.mario.engine.generalization.MarioEntity;
import ch.idsia.benchmark.mario.environments.IEnvironment;

public class AgentOptions {

	public MarioEntity mario;
	
	public int receptiveFieldWidth;
	public int receptiveFieldHeight;
	public int marioEgoRow;
	public int marioEgoCol;
	
	public AgentOptions() {
	}
	
	public AgentOptions(IEnvironment environment) {
		mario = environment.getMario();
		
		this.receptiveFieldHeight = environment.getMario().receptiveFieldHeight;
		this.receptiveFieldWidth = environment.getMario().receptiveFieldWidth;
		this.marioEgoCol = environment.getMario().egoCol;
		this.marioEgoRow = environment.getMario().egoRow;
	}

	public AgentOptions(int receptiveFieldWidth, int receptiveFieldHeight, int marioEgoRow, int marioEgoCol) {
		this.receptiveFieldWidth = receptiveFieldWidth;
		this.receptiveFieldHeight = receptiveFieldHeight;
		this.marioEgoRow = marioEgoRow;
		this.marioEgoCol = marioEgoCol;
	}	
	
}
