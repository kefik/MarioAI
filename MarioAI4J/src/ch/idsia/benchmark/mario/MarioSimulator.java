/*
 * Copyright (c) 2009-2010, Sergey Karakovskiy and Julian Togelius
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Mario AI nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package ch.idsia.benchmark.mario;

import ch.idsia.agents.IAgent;
import ch.idsia.agents.controllers.keyboard.CheaterKeyboardAgent;
import ch.idsia.benchmark.mario.engine.generalization.Enemy;
import ch.idsia.benchmark.mario.engine.input.MarioInput;
import ch.idsia.benchmark.mario.environments.IEnvironment;
import ch.idsia.benchmark.mario.environments.MarioEnvironment;
import ch.idsia.benchmark.mario.options.FastOpts;
import ch.idsia.benchmark.mario.options.MarioOptions;
import ch.idsia.tools.EvaluationInfo;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Karakovskiy, sergey at idsia dot ch
 * Date: Mar 1, 2010 
 * Time: 1:50:37 PM 
 * Package: ch.idsia.scenarios
 * 
 * Wraps execution of concrete {@link IAgent} within {@link MarioEnvironment} using preset {@link MarioOptions}, which are combined
 * together with passed 'options' during the construction.
 * <br/><br/>
 * Use {@link MarioSimulator#run(IAgent)} to execute the agent and receive {@link EvaluationInfo} from the run.
 * <br/><br/>
 * This class has {@link #main(String[])} method that starts the simulator visualized with {@link CheaterKeyboardAgent} 
 * (i.e., you can manually control Mario using arrows + 'A'Jump + 'S'Attack).
 * 
 * @author Sergey Karakovskiy, sergey at idsia dot ch
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class MarioSimulator {
	
	static {
		MarioOptions.javaInit();
	}
	
	private String options;

	public MarioSimulator(String options) {
		this.options = options;
	}
	
	public MarioSimulator(String... options) {
		this.options = "";
		boolean first = true;
		for (String option : options) {
			if (first) this.options += option;
			else first = false;
			this.options += " " + option;			
		}
	}
	
	public synchronized EvaluationInfo run(IAgent agent) {
		System.out.println("[MarioSimulator] run(" + (agent == null ? "NULL" : agent.getClass().getName()) + ")");
		if (agent == null) {
			System.err.println("[MarioSimulator] agent is NULL! Aborting!");
			throw new RuntimeException("Agent is NULL! Please specify correct agent to run within the simulator.");
		}
		
		System.out.println("[MarioSimulator] Initializing MarioOptions..");
		
		MarioOptions.reset(options);
		
		System.out.println("[MarioSimulator] Initializing the environment and the agent...");
		
		IEnvironment environment = MarioEnvironment.getInstance();
		environment.reset(agent);
				
		System.out.println("[MarioSimulator] SIMULATION RUNNING!");
		
		while (!environment.isLevelFinished()) {
			// UPDATE THE ENVIRONMENT
			environment.tick();
			// PUSH NEW PERCEPTS TO THE AGENT
			agent.observe(environment);
			// LET AGENT PERFORM ITS ACTION-SELECTION
			MarioInput actions = agent.actionSelection();
			// PROPAGATE ACTIONS TO THE ENVIRONMENT
			environment.performAction(actions);
			// NOTIFY AGENT ABOUT CURRENT INTERMEDIATE REWARD
			agent.receiveReward(environment.getIntermediateReward());
		}
		
		System.out.println("[MarioSimulator] SIMULATION ENDED!");
		
		EvaluationInfo result = environment.getEvaluationInfo();
		
		System.out.println("[MarioSimulator] RESULT:");
		System.out.println(result.toString());
		
		System.out.println("[MarioSimulator] Simulator terminated.");
		
		return result.clone(); // result is shared instance ... we must clone it to maintain sanity 		
	}
	
	public static void main(String[] args) {
		String options = FastOpts.VIS_ON_2X + FastOpts.L_ENEMY(Enemy.GOOMBA, Enemy.SPIKY, Enemy.GREEN_KOOPA) + FastOpts.L_RANDOMIZE + FastOpts.AI_ZL_0_0;
		
		MarioSimulator simulator = new MarioSimulator(options);
		
		IAgent agent = new CheaterKeyboardAgent();
		
		EvaluationInfo result = simulator.run(agent);
		
		System.exit(0);
	}

}