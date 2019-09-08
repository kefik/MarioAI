package cz.cuni.mff.aspect.coevolution

import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.evolution.levels.LevelEvolution
import cz.cuni.mff.aspect.mario.MarioAgent
import cz.cuni.mff.aspect.mario.controllers.MarioController
import cz.cuni.mff.aspect.mario.level.MarioLevel

class MarioCoEvolver {

    fun evolve(controllerEvolution: ControllerEvolution, generator: LevelEvolution, generations: Int = DEFAULT_GENERATIONS_NUMBER): CoevolutionResult {
        lateinit var resultController: MarioController
        lateinit var resultLevels: Array<MarioLevel>

        for (generation in (0 until generations)) {
            println("COEVOLUTION GENERATION ${generation + 1}")
            resultController = controllerEvolution.evolve(generator)
            resultLevels = generator.evolve(MarioAgent(resultController))
        }

        return CoevolutionResult(resultController, resultLevels)
    }

    companion object {
        private const val DEFAULT_GENERATIONS_NUMBER: Int = 1
    }
}