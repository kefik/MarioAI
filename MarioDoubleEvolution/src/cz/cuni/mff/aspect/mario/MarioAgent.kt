package cz.cuni.mff.aspect.mario

import ch.idsia.agents.AgentOptions
import ch.idsia.agents.IAgent
import ch.idsia.agents.controllers.MarioHijackAIBase
import ch.idsia.benchmark.mario.engine.input.MarioInput
import cz.cuni.mff.aspect.doubleev.agent.EvolutionaryAgent

class MarioAgent(private val _evolvedAgent: EvolutionaryAgent) : MarioHijackAIBase(), IAgent {

    override fun reset(options: AgentOptions) {
        super.reset(options)
    }

    override fun actionSelectionAI(): MarioInput {
        // control.runRight()
        control.jump()

        return action
    }

}