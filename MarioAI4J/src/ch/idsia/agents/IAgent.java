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

package ch.idsia.agents;

import ch.idsia.benchmark.mario.engine.input.MarioInput;
import ch.idsia.benchmark.mario.engine.input.MarioKey;
import ch.idsia.benchmark.mario.environments.IEnvironment;

/**
 * Created by IntelliJ IDEA. 
 * User: Sergey Karakovskiy 
 * Date: Mar 28, 2009 
 * Time: 8:46:42 PM 
 * package: ch.idsia.controllers.agents
 * 
 * @author Sergey Karakovskiy, sergey@idsia.ch
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public interface IAgent {

	/**
	 * Returns name of the agent.
	 * @return name of the agent
	 */
	String getName();

	/**
	 * Called before the agent is executed for the first time or in-between respective tasks/scenarios.
	 * <br/><br/>
	 * You should clear all dynamic/intermediate data.
	 * 
	 * @param options
	 */
	void reset(AgentOptions options);
	
	/**
	 * Agent is given new percepts to process and save for the next {@link #actionSelection()}.
	 * 
	 * @param environment
	 */
	void observe(IEnvironment environment);	
	
	/**
	 * Agent should now perform an action-selection and return actions it wants to perform.
	 * 
	 * @return Actions to perform
	 */
	MarioInput actionSelection();

	/**
	 * Agent is informed about 'intermediateReward' it accumulated so-far.
	 * 
	 * @param intermediateReward
	 */
	void receiveReward(float intermediateReward);

}
