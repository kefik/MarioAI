package cz.cuni.mff.aspect.doubleev

import cz.cuni.mff.aspect.doubleev.agent.EvolutionaryAgent
import cz.cuni.mff.aspect.doubleev.generator.EvolutionaryGenerator

class MarioEvolver {

    fun evolve(agent: EvolutionaryAgent, generator: EvolutionaryGenerator, generations: Int = DEFAULT_GENERATIONS_NUMBER) {
        for (generation in (0 until generations)) {
            println("GENERATION ${generation + 1}")
            agent.evolve(generator)
            generator.evolve(agent)
        }
    }

    companion object {
        private const val DEFAULT_GENERATIONS_NUMBER: Int = 5;
    }
}