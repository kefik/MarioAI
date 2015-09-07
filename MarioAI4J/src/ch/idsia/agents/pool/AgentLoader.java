package ch.idsia.agents.pool;

import ch.idsia.agents.IAgent;
import ch.idsia.tools.punj.PunctualJudge;

/**
 * Created by IntelliJ IDEA. 
 * User: Sergey Karakovskiy, sergey.karakovskiy@gmail.com
 * Date: 15.03.11 
 * Time: 21:19 
 * Package: ch.idsia.agents
 * 
 * @author Sergey Karakovskiy, sergey.karakovskiy@gmail.com
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public final class AgentLoader {
	
	private static final AgentLoader _instance = new AgentLoader();

	private AgentLoader() {
	}

	public static AgentLoader getInstance() {
		return _instance;
	}

	/**
	 * Instantiate a new agent under 'agentFQCN' and optionally instrument it with a {@link PunctualJudge}.
	 * @param agentFQCN
	 * @param usePunctualJudge
	 * @return
	 */
	public IAgent loadAgent(String agentFQCN, boolean usePunctualJudge) {
		IAgent agent = null;

		try {
			agent = (IAgent) Class.forName(agentFQCN).newInstance();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("[~ Mario AI ~] " + agentFQCN + " is not a class name! Have you forget to add agent's library into Java classpath?");
		} catch (InstantiationException e) {
			throw new RuntimeException("[~ Mario AI ~] " + agentFQCN + " failed to instantiate!", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("[~ Mario AI ~] " + agentFQCN + " illegal access!", e);
		}

		if (usePunctualJudge) {
			try {
				PunctualJudge punj = new PunctualJudge();
				String classPath = agent.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
				String className = agent.getClass().getName().replace(".", "/")	+ ".class";
				byte[] byteClass = punj.instrumentClass(classPath + className);
				Class<?> c = punj.buildClass(byteClass, agent.getClass().getName());
				agent = (IAgent) c.newInstance();
			} catch (Exception e) {
				throw new RuntimeException("[~ Mario AI ~] Failed to instrument " + agentFQCN + " with PunctualJudge.", e);
			}
		}
		
		return agent;
	}
}
