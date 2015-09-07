package ch.idsia.agents.controllers.examples;

import ch.idsia.agents.AgentOptions;
import ch.idsia.agents.IAgent;
import ch.idsia.agents.controllers.MarioHijackAIBase;
import ch.idsia.benchmark.mario.MarioSimulator;
import ch.idsia.benchmark.mario.engine.generalization.Enemy;
import ch.idsia.benchmark.mario.engine.input.MarioInput;
import ch.idsia.benchmark.mario.engine.input.MarioKey;
import ch.idsia.benchmark.mario.options.FastOpts;

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
		String options = FastOpts.FAST_VISx2_02_JUMPING + FastOpts.L_ENEMY(Enemy.GOOMBA);
		
		MarioSimulator simulator = new MarioSimulator(options);
		
		IAgent agent = new Agent04_Shooter();
		
		simulator.run(agent);
		
		System.exit(0);
	}
}