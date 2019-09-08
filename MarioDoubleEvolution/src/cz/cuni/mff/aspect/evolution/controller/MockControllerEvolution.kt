package cz.cuni.mff.aspect.evolution.controller

import cz.cuni.mff.aspect.evolution.generator.EvolutionaryGenerator
import cz.cuni.mff.aspect.mario.controllers.MarioController
import cz.cuni.mff.aspect.mario.controllers.MockMarioController


/**
 * Mock agent controller evolution which doesn't evolve anything.
 */
class MockControllerEvolution : ControllerEvolution {

    override fun evolve(levelGenerator: EvolutionaryGenerator): MarioController {
        return MockMarioController()
    }

}