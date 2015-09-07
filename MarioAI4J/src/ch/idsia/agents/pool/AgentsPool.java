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

package ch.idsia.agents.pool;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import ch.idsia.agents.IAgent;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Karakovskiy, firstname_at_idsia_dot_ch
 * Date: May 9, 2009 
 * Time: 8:28:06 PM 
 * Package: ch.idsia.controllers.agents
 * 
 * @author Sergey Karakovskiy, firstname_at_idsia_dot_ch
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */

public final class AgentsPool {
	private static IAgent currentAgent = null;
	private static AgentLoader agentLoader = AgentLoader.getInstance();

	public static void addAgent(IAgent agent) {
		agentsHashMap.put(agent.getName(), agent);
	}

	public static IAgent loadAgent(String agentFQCN, boolean isPunj) {
		return agentLoader.loadAgent(agentFQCN, isPunj);
	}

	public static Collection<IAgent> getAgentsCollection() {
		return agentsHashMap.values();
	}

	public static Set<String> getAgentsNames() {
		return AgentsPool.agentsHashMap.keySet();
	}

	public static IAgent getAgentByName(String agentName) {
		// There is only one case possible;
		IAgent ret = AgentsPool.agentsHashMap.get(agentName);
		if (ret == null)
			ret = AgentsPool.agentsHashMap.get(agentName.split(":")[0]);
		return ret;
	}

	public static IAgent getCurrentAgent() {
		if (currentAgent == null)
			currentAgent = (IAgent) getAgentsCollection().toArray()[0];
		return currentAgent;
	}

	public static void setCurrentAgent(IAgent agent) {
		currentAgent = agent;
	}

	// public static void setCurrentAgent(String agentWOXName)
	// {
	// setCurrentAgent(AgentsPool.loadAgent(agentWOXName));
	// }

	static HashMap<String, IAgent> agentsHashMap = new LinkedHashMap<String, IAgent>();
}
