package cz.cuni.mff.aspect.doubleev.generator

import cz.cuni.mff.aspect.doubleev.agent.EvolutionaryAgent
import cz.cuni.mff.aspect.doubleev.game.MarioMap
import cz.cuni.mff.aspect.doubleev.game.MockMap

class MockEvolutionaryGenerator : EvolutionaryGenerator {

    override fun evolve(agent: EvolutionaryAgent) {

    }

    override fun generateMap(): MarioMap {
        return MockMap
    }
}