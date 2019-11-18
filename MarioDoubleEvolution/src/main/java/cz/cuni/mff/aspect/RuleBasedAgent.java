package cz.cuni.mff.aspect;

import java.awt.Graphics;

import ch.idsia.agents.AgentOptions;
import ch.idsia.agents.IAgent;
import ch.idsia.agents.controllers.MarioHijackAIBase;
import ch.idsia.agents.controllers.modules.Entities;
import ch.idsia.agents.controllers.modules.Tiles;
import ch.idsia.benchmark.mario.MarioSimulator;
import ch.idsia.benchmark.mario.engine.LevelScene;
import ch.idsia.benchmark.mario.engine.VisualizationComponent;
import ch.idsia.benchmark.mario.engine.generalization.EntityType;
import ch.idsia.benchmark.mario.engine.generalization.Tile;
import ch.idsia.benchmark.mario.engine.input.MarioControl;
import ch.idsia.benchmark.mario.engine.input.MarioInput;
import ch.idsia.benchmark.mario.environments.IEnvironment;
import ch.idsia.tools.EvaluationInfo;
import ch.idsia.utils.MarioLog;

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
public class RuleBasedAgent extends MarioHijackAIBase implements IAgent {

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

	/**
	 * Use {@link #e} member to query entities (Goombas, Spikies, Koopas, etc.) around Mario; see {@link EntityType} for complete list of entities.
	 * Important methods you will definitely need: {@link Entities#danger(int, int)} and {@link Entities#entityType(int, int)}.
	 * 
	 * Use {@link #t} member to query tiles (Bricks, Flower pots, etc.} around Mario; see {@link Tile} for complete list of tiles.
	 * Important method you will definitely need: {@link Tiles#brick(int, int)}.
	 * 
	 * Use {@link #control} to output actions (technically this method must return {@link #action} in order for {@link #control} to work.
	 * Note that all actions in {@link #control} runs in "parallel" (except {@link MarioControl#runLeft()} and {@link MarioControl#runRight()}, which cancels each other out in consecutive calls).
	 * Note that you have to call {@link #control} methods every {@link #actionSelectionAI()} tick (otherwise {@link #control} will think you DO NOT want to perform that action}. 
	 */
	@Override
	public MarioInput actionSelectionAI() {
		/*
		if (this.jumpOnHill() || jumpOverDanger()) {
			if (this.checkIfShouldStop()) {
				control.jump();
			} else {
				control.runLeft();
			}
		} else {
			this.shootGoombas();
			this.jumpIfDangerfBelow();

			if (this.checkIfRun()) {
				control.runRight();
			} else {
				control.runLeft();
			}
		}*/


/*
		this.shootGoombas();
		if (!this.checkIfShouldStop()) {
			control.runLeft();
		} else {
			if (this.jumpOnHill() || this.jumpOverDanger() || this.jumpIfDangerfBelow()) {
				control.jump();
			}
		}
		if (this.checkIfRun()) {
			control.runRight();
		} else {
			control.runLeft();
		}*/


		this.shootGoombas();
		if (this.jumpOnHill() || this.jumpOverDanger() || this.jumpIfDangerfBelow()) {
			control.jump();
		}
		if (this.checkIfRun()) {
			control.runRight();
		} else {
			control.runLeft();
		}

		return action;
	}

	private void jumpOverHole() {
		if (!t.brick(1,1)) {
			if (t.brick(2,1) || t.brick(3,1) || t.brick(4,1) || t.brick(5,1) ||
					t.brick(2,0) || t.brick(3,0) || t.brick(4,0) ||
					t.brick(2,-1) || t.brick(3,-1) || t.brick(4, -1) ||
					t.brick(2, -2) || t.brick(3, -2)) {
				control.jump();
			}
		}
		if (!t.brick(1, 2)) {
			if (t.brick(2,2) || t.brick(3,2) || t.brick(4,2) || t.brick(5,2) || t.brick(6,2) ||
					t.brick(2,1) || t.brick(3,1) || t.brick(4,1) || t.brick(5,1) ||
					t.brick(2,0) || t.brick(3,0) || t.brick(4,0) ||
					t.brick(2,-1) || t.brick(3,-1) || t.brick(4, -1) ||
					t.brick(2, -2) || t.brick(3, -2)) {
				control.jump();
			}
		}
	}

	private boolean jumpOnHill() {
		if (t.brick(2, 0) || t.brick(1,0)) {
			return true;
		}

		return false;
	}

	private void shootGoombas() {
		if (mario.isFalling()) {
			for (int i = 0; i < 5; ++i) {
				if (e.danger(i, 0) && e.shootable(i, 0)) {
					control.shoot();
				}
			}
		}
		if (e.danger(0, 1) || e.danger(1, 1) || e.danger(2, 1) ||
				e.danger(1, 2)) {
			control.shoot();
		}
	}

	private boolean jumpOverDanger() {
		for (int i = 0; i < 5; ++i) {
			if (e.danger(i, 0)) {
				return true;
			}
		}

		return false;
	}

	private boolean jumpIfDangerfBelow() {
		if (e.danger(0, 1) || e.danger(0, 2) || e.danger(0 ,3) ||
				e.danger(1, 1) || e.danger(1, 2)) {
			return true;
		}

		return false;
	}

	private boolean checkIfRun() {
		if (e.danger(1, -1) || e.danger(1, -2) || e.danger(2, -1) || e.danger(2, -2)) {
			return false;
		}

		if (!mario.mayShoot && !mario.onGround) {
			if (e.danger(1, 0) || e.danger(2,0) || e.danger(3, 0) ) {
				return false;
			}
		}

		return true;
	}

	private boolean checkIfShouldStop() {
		if (isSpikes(0, 1) || isSpikes(0, 2) || isSpikes(0, 3)) {
			return false;
		}
		if (isSpikes(3, 0) ||
				e.danger(1, -2) || e.danger(2, -2) || e.danger(3, -2) || e.danger(4, -1) || e.danger(5, -2)) {
			return false;
		}

		return true;
	}

	private boolean isSpikes(int x, int y) {
		return e.danger(x, y) && !e.shootable(x, y);
	}
	
	public static void main(String[] args) {
		// YOU MAY RISE LEVEL OF LOGGING, even though there are probably no inforamation you need to know...
		//MarioLog.setLogLevel(Level.ALL);
		
		// UNCOMMENT THE LINE OF THE LEVEL YOU WISH TO RUN
		
		//LevelConfig level = LevelConfig.LEVEL_0_FLAT;
		//LevelConfig level = LevelConfig.LEVEL_1_JUMPING;
		//LevelConfig level = LevelConfig.LEVEL_2_GOOMBAS;
		//LevelConfig level = LevelConfig.LEVEL_3_TUBES;
		LevelConfig level = LevelConfig.LEVEL_4_SPIKIES;
		
		// CREATE SIMULATOR
		MarioSimulator simulator = new MarioSimulator(level.getOptions());
		
		// CREATE SIMULATOR AND RANDOMIZE LEVEL GENERATION
		// -- if you wish to use this, comment out the line above and uncomment line below
		//MarioSimulator simulator = new MarioSimulator(level.getOptionsRandomized());
		
		// INSTANTIATE YOUR AGENT
		IAgent agent = new RuleBasedAgent();
		
		// RUN THE SIMULATION
		EvaluationInfo info = simulator.run(agent);
		
		// CHECK RESULT
		switch (info.getResult()) {
		case LEVEL_TIMEDOUT:
			MarioLog.warn("LEVEL TIMED OUT!");
			break;
			
		case MARIO_DIED:
			MarioLog.warn("MARIO KILLED");
			break;
			
		case SIMULATION_RUNNING:
			MarioLog.error("SIMULATION STILL RUNNING?");
			throw new RuntimeException("Invalid evaluation info state, simulation should not be running.");
			
		case VICTORY:
			MarioLog.warn("VICTORY!!!");
			break;
		}
	}
	
}