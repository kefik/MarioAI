package cz.cuni.mff.aspect.doubleev.generator

import cz.cuni.mff.aspect.doubleev.agent.EvolutionaryAgent
import cz.cuni.mff.aspect.doubleev.game.MarioMap

interface EvolutionaryGenerator {

    fun generateMap(): MarioMap

    fun evolve(agent: EvolutionaryAgent)

}