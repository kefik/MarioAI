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

package ch.idsia.agents.controllers;

import java.io.IOException;

import ch.idsia.agents.AgentOptions;
import ch.idsia.agents.IAgent;
import ch.idsia.benchmark.mario.engine.Replayer;
import ch.idsia.benchmark.mario.engine.input.MarioInput;
import ch.idsia.benchmark.mario.environments.IEnvironment;
import ch.idsia.utils.MarioLog;

/**
 * Created by IntelliJ IDEA. 
 * User: Sergey Karakovskiy, sergey.karakovskiy@gmail.com 
 * Date: Oct 9, 2010 
 * Time: 2:08:47 AM 
 * Package: ch.idsia.agents.controllers
 * 
 * @author Sergey Karakovskiy, sergey.karakovskiy@gmail.com
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class ReplayAgent implements IAgent {

	private Replayer replayer;
	private MarioInput keys;
	private String name;

	public ReplayAgent(String name) {
		setName("Replay<" + name + ">");
	}
	
	@Override
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setReplayer(Replayer replayer) {
		this.replayer = replayer;
	}

	@Override
	public void reset(AgentOptions options) {
	}

	@Override
	public MarioInput actionSelection() {
		// handle the "Out of time" case
		try {
			keys.reset();
			replayer.readAction(keys);
		} catch (IOException e) {
			MarioLog.error("[Mario AI Exception] : ReplayAgent is not able to read next action");
			e.printStackTrace();
		}
		return keys;
	}

	@Override
	public void observe(final IEnvironment environment) {
	}

	@Override
	public void receiveReward(final float intermediateReward) {
	}
	
}
