package cz.cuni.mff.aspect.coevolution.agent

import cz.cuni.mff.aspect.coevolution.generator.EvolutionaryGenerator

interface EvolutionaryAgent {

    fun evolve(levelGenerator: EvolutionaryGenerator)

}