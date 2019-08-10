package cz.cuni.mff.aspect.doubleev

import cz.cuni.mff.aspect.doubleev.agent.EvolutionaryAgent
import cz.cuni.mff.aspect.doubleev.generator.EvolutionaryGenerator

class DoubleEvolution {

    private val _coevolutionGenerations: Int = 5;

    fun evolve(agent: EvolutionaryAgent, generator: EvolutionaryGenerator) {
        var map = generator.generateMap()

        for (generation in (0.._coevolutionGenerations)) {
            println("GENERATION $generation")
            agent.evolve(map)
            generator.evolve(agent)
            map = generator.generateMap()
        }
    }
}