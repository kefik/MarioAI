package cz.cuni.mff.aspect.evolution.controller

import cz.cuni.mff.aspect.evolution.utils.MarioGameplayEvaluator
import cz.cuni.mff.aspect.mario.controllers.MarioController
import cz.cuni.mff.aspect.mario.level.MarioLevel


/**
 * Interface representing controller evolution.
 */
interface ControllerEvolution {

    /**
     * Evolves a mario agent controller, being provided with level generator.
     *
     * @return the evolved agent controller
     */
    fun evolve(levels: Array<MarioLevel>, fitness: MarioGameplayEvaluator<Float>, objective: MarioGameplayEvaluator<Float>): MarioController

}
