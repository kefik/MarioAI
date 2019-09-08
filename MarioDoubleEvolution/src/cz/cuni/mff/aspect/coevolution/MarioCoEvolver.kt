package cz.cuni.mff.aspect.coevolution

import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.evolution.generator.EvolutionaryGenerator
import cz.cuni.mff.aspect.mario.controllers.MarioController

class MarioCoEvolver {

    fun evolve(controllerEvolution: ControllerEvolution, generator: EvolutionaryGenerator, generations: Int = DEFAULT_GENERATIONS_NUMBER): CoevolutionResult {
        lateinit var resultController: MarioController

        for (generation in (0 until generations)) {
            println("COEVOLUTION GENERATION ${generation + 1}")
            resultController = controllerEvolution.evolve(generator)
            generator.evolve(controllerEvolution)
        }

        return CoevolutionResult(resultController)
    }

    companion object {
        private const val DEFAULT_GENERATIONS_NUMBER: Int = 1
    }
}