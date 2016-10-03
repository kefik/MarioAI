package ch.idsia.agents.controllers.examples;

import java.awt.Graphics;

import ch.idsia.agents.AgentOptions;
import ch.idsia.agents.IAgent;
import ch.idsia.agents.controllers.MarioHijackAIBase;
import ch.idsia.benchmark.mario.MarioSimulator;
import ch.idsia.benchmark.mario.engine.LevelScene;
import ch.idsia.benchmark.mario.engine.VisualizationComponent;
import ch.idsia.benchmark.mario.engine.generalization.Enemy;
import ch.idsia.benchmark.mario.engine.input.MarioInput;
import ch.idsia.benchmark.mario.engine.input.MarioKey;
import ch.idsia.benchmark.mario.environments.IEnvironment;
import ch.idsia.benchmark.mario.options.FastOpts;
import ch.idsia.tools.EvaluationInfo;

/**
 * Your custom agent! Feel free to fool around!
 * 
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class MyAgent extends MarioHijackAIBase implements IAgent {

	private boolean shooting = false;
	
	@Override
	public void reset(AgentOptions options) {
		super.reset(options);
	}
	
	@Override
	public void debugDraw(VisualizationComponent vis, LevelScene level,	IEnvironment env, Graphics g) {
		super.debugDraw(vis, level, env, g);
		// provide custom visualization using 'g'
		String debug = "";
		if (enemyAhead()) {
			debug += "|ENEMY AHEAD|";
		}
		if (brickAhead()) {
			debug += "|BRICK AHEAD|";
		}
		if (mario != null && mario.onGround) {
			debug += "|ON GROUND|";
		}
		VisualizationComponent.drawStringDropShadow(g, debug, 0, 12, 1);
	}

	private boolean enemyAhead() {
		return
				   e.danger(1, 0) || e.danger(1, -1) 
				|| e.danger(2, 0) || e.danger(2, -1)
				|| e.danger(3, 0) || e.danger(2, -1);
	}
	
	private boolean brickAhead() {
		return
				   t.brick(1, 0) || t.brick(1, -1) 
				|| t.brick(2, 0) || t.brick(2, -1)
				|| t.brick(3, 0) || t.brick(3, -1);
	}

	public MarioInput actionSelectionAI() {
		// ALWAYS RUN RIGHT
		control.runRight();
		
		// ENEMY || BRICK AHEAD => JUMP
		// WARNING: do not press JUMP if UNABLE TO JUMP!
		if (enemyAhead() || brickAhead()) control.jump();		
		
		// If in the air => keep JUMPing
		if (!mario.onGround) control.jump();

		control.shoot();
		
		return action;
	}
	
	public static void main(String[] args) {
		// IMPLEMENTS END-LESS RUNS
		while (true) {
			String options = FastOpts.VIS_ON_2X + FastOpts.LEVEL_02_JUMPING + FastOpts.L_ENEMY(Enemy.GOOMBA, Enemy.SPIKY) + FastOpts.L_RANDOMIZE;
			
			MarioSimulator simulator = new MarioSimulator(options);
			
			IAgent agent = new Agent04_Shooter();
			
			EvaluationInfo info = simulator.run(agent);
			
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
	
}