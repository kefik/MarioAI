package cz.cuni.mff.aspect.evolution.controller

import cz.cuni.mff.aspect.mario.controllers.MarioController
import cz.cuni.mff.aspect.mario.controllers.MockMarioController
import cz.cuni.mff.aspect.mario.level.MarioLevel


/**
 * Mock agent controller evolution which doesn't evolve anything.
 */
class MockControllerEvolution : ControllerEvolution {

    override fun evolve(levels: Array<MarioLevel>): MarioController {
        return MockMarioController()
    }

}