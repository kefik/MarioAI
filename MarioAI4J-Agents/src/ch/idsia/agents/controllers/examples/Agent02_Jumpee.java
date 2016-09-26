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

package ch.idsia.agents.controllers.examples;

import ch.idsia.agents.AgentOptions;
import ch.idsia.agents.IAgent;
import ch.idsia.agents.controllers.MarioHijackAIBase;
import ch.idsia.benchmark.mario.MarioSimulator;
import ch.idsia.benchmark.mario.engine.input.MarioInput;
import ch.idsia.benchmark.mario.engine.input.MarioKey;
import ch.idsia.benchmark.mario.options.FastOpts;

/**
 * Created by IntelliJ IDEA. 
 * User: Sergey Karakovskiy 
 * Date: Apr 25, 2009 
 * Time: 12:27:07 AM 
 * Package: ch.idsia.agents.controllers;
 * 
 * Agent that sprints forward and jumps whenever it can.
 * 
 * @author Sergey Karakovskiy
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class Agent02_Jumpee extends MarioHijackAIBase implements IAgent {
	
	@Override
	public void reset(AgentOptions options) {
		super.reset(options);
	}
	
	@Override
	public MarioInput actionSelectionAI() {
		// ALWAYS RUN RIGHT
		control.runRight();
		
		// ALWAYS SPRINT
		control.sprint();
		
		// JUMP IF YOU CAN!
		// ... this will make Mario to jump as high as possible
		control.jump();

		return action;
	}
	
	public static void main(String[] args) {
		// USE WORLD WITH NON-FLAT GROUND WITHOUT ENEMIES
		String options = FastOpts.VIS_ON_2X + FastOpts.LEVEL_02_JUMPING;
		
		// CREATE SIMULATOR
		MarioSimulator simulator = new MarioSimulator(options);
		
		// CREATE AGENT
		IAgent agent = new Agent02_Jumpee();
		
		// RUN SIMULATOR w/ AGENT
		simulator.run(agent);
		
		// TERMINATE
		System.exit(0);
	}
	
}
