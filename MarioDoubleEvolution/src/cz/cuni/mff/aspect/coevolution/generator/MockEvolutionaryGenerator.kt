package cz.cuni.mff.aspect.coevolution.generator

import cz.cuni.mff.aspect.coevolution.agent.EvolutionaryAgent
import cz.cuni.mff.aspect.coevolution.game.MarioMap
import cz.cuni.mff.aspect.coevolution.game.MockMap

class MockEvolutionaryGenerator : EvolutionaryGenerator {

    override fun evolve(agent: EvolutionaryAgent) {

    }

    override fun generateMap(): MarioMap {
        return MockMap
    }
}