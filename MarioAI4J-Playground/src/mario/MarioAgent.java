package mario;

import java.awt.Graphics;

import ch.idsia.agents.AgentOptions;
import ch.idsia.agents.IAgent;
import ch.idsia.agents.controllers.MarioHijackAIBase;
import ch.idsia.benchmark.mario.MarioSimulator;
import ch.idsia.benchmark.mario.engine.LevelScene;
import ch.idsia.benchmark.mario.engine.VisualizationComponent;
import ch.idsia.benchmark.mario.engine.input.MarioInput;
import ch.idsia.benchmark.mario.environments.IEnvironment;
import ch.idsia.tools.EvaluationInfo;

/**
 * Code your custom agent here!
 * 
 * Change {@link #actionSelection()} implementation to alter the behavior of your Mario.
 * 
 * Change {@link #debugDraw(VisualizationComponent, LevelScene, IEnvironment, Graphics)} to draw custom debug stuff.
 * 
 * You can change the type of level you want to play in {@link #main(String[])}.
 * 
 * Once you have your agent ready, you may use {@link Evaluate} class to benchmark the quality of your AI. 
 */
public class MarioAgent extends MarioHijackAIBase implements IAgent {

	@Override
	public void reset(AgentOptions options) {
		super.reset(options);
	}
	
	@Override
	public void debugDraw(VisualizationComponent vis, LevelScene level,	IEnvironment env, Graphics g) {
		super.debugDraw(vis, level, env, g);
		if (mario == null) return;

		// provide custom visualization using 'g'
		
		// EXAMPLE DEBUG VISUALIZATION
		String debug = "MY DEBUG STRING";
		VisualizationComponent.drawStringDropShadow(g, debug, 0, 26, 1);
	}

	public MarioInput actionSelectionAI() {
		// ALWAYS RUN RIGHT
		control.runRight();
		
		control.sprint();
		
		// RETURN THE RESULT
		return action;
	}
	
	public static void main(String[] args) {
		// UNCOMMENT THE LINE OF THE LEVEL YOU WISH TO RUN
		
		LevelConfig level = LevelConfig.LEVEL_0_FLAT;
		//LevelConfig level = LevelConfig.LEVEL_1_JUMPING;
		//LevelConfig level = LevelConfig.LEVEL_2_GOOMBAS;
		//LevelConfig level = LevelConfig.LEVEL_3_TUBES;
		
		// CREATE SIMULATOR
		MarioSimulator simulator = new MarioSimulator(level.getOptions());
		
		// CREATE SIMULATOR AND RANDOMIZE LEVEL GENERATION
		// -- if you wish to use this, comment out the line above and uncomment line below
		//MarioSimulator simulator = new MarioSimulator(level.getOptionsRandomized());
		
		// INSTANTIATE YOUR AGENT
		IAgent agent = new MarioAgent();
		
		// RUN THE SIMULATION
		EvaluationInfo info = simulator.run(agent);
		
		// CHECK RESULT
		switch (info.getResult()) {
		case LEVEL_TIMEDOUT:
			System.out.println("LEVEL TIMED OUT!");
			break;
			
		case MARIO_DIED:
			System.out.println("MARIO KILLED");
			break;
			
		case SIMULATION_RUNNING:
			System.out.println("SIMULATION STILL RUNNING?");
			throw new RuntimeException("Invalid evaluation info state, simulation should not be running.");
			
		case VICTORY:
			System.out.println("VICTORY!!!");
			break;
		}
	}
	
}