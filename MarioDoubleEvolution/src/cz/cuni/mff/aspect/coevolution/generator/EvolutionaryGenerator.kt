package cz.cuni.mff.aspect.coevolution.generator

import cz.cuni.mff.aspect.coevolution.agent.EvolutionaryAgent
import cz.cuni.mff.aspect.coevolution.game.MarioMap

interface EvolutionaryGenerator {

    fun generateMap(): MarioMap

    fun evolve(agent: EvolutionaryAgent)

}