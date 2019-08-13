package cz.cuni.mff.aspect.doubleev.agent

import cz.cuni.mff.aspect.doubleev.generator.EvolutionaryGenerator

interface EvolutionaryAgent {

    fun evolve(levelGenerator: EvolutionaryGenerator)

}