package ch.idsia.agents.controllers.examples;

import ch.idsia.agents.AgentOptions;
import ch.idsia.agents.IAgent;
import ch.idsia.agents.controllers.MarioHijackAIBase;
import ch.idsia.benchmark.mario.MarioSimulator;
import ch.idsia.benchmark.mario.engine.generalization.Enemy;
import ch.idsia.benchmark.mario.engine.generalization.Entity;
import ch.idsia.benchmark.mario.engine.input.MarioInput;
import ch.idsia.benchmark.mario.engine.input.MarioKey;
import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.mario.options.FastOpts;
import ch.idsia.tools.EvaluationInfo;

/**
 * Agent that sprints forward, jumps and shoots.
 * 
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class Agent04_Shooter extends MarioHijackAIBase implements IAgent {

	private boolean shooting = false;
	
	@Override
	public void reset(AgentOptions options) {
		super.reset(options);
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
		action.press(MarioKey.RIGHT);
		
		// ENEMY || BRICK AHEAD => JUMP
		// WARNING: do not press JUMP if UNABLE TO JUMP!
		action.set(MarioKey.JUMP, (enemyAhead() || brickAhead()) && mario.mayJump);
		
		
		
		// If in the air => keep JUMPing
		if (!mario.onGround) {
			action.press(MarioKey.JUMP);
		}
		
		if (mario.mayShoot) {
			if (shooting) {
				shooting = false;
				action.release(MarioKey.SPEED);
			} else 
			if (action.isPressed(MarioKey.SPEED)) {				
				action.release(MarioKey.SPEED);
			} else {
				shooting = true;
				action.press(MarioKey.SPEED);
			}
		} else {
			if (shooting) {
				shooting = false;
				action.release(MarioKey.SPEED);
			}
		}
		
		return action;
	}
	
	public static void main(String[] args) {
		// IMPLEMENTS END-LESS RUNS
		while (true) {
			String options = FastOpts.VIS_ON_2X + FastOpts.LEVEL_02_JUMPING + FastOpts.L_ENEMY(Enemy.GOOMBA, Enemy.SPIKY) + FastOpts.L_TUBES_ON + FastOpts.L_RANDOMIZE;
			
			MarioSimulator simulator = new MarioSimulator(options);
			
			IAgent agent = new Agent04_Shooter();
			
			EvaluationInfo info = simulator.run(agent);
			
			switch (info.marioStatus) {
			case Mario.STATUS_RUNNING:
				if (info.timeLeft <= 0) {
					System.out.println("LEVEL TIMED OUT!");
				} else {
					throw new RuntimeException("Invalid state...");
				}
				break;
			case Mario.STATUS_WIN:
				System.out.println("VICTORY");
				break;
			case Mario.STATUS_DEAD:
				System.out.println("MARIO KILLED");
				break;
			}
		}
		
		//System.exit(0);
	}
}