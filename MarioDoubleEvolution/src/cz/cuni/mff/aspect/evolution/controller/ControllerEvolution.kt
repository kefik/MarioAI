package cz.cuni.mff.aspect.evolution.controller

import cz.cuni.mff.aspect.evolution.generator.EvolutionaryGenerator
import cz.cuni.mff.aspect.mario.controllers.MarioController


/**
 * Interface representing controller evolution.
 */
interface ControllerEvolution {

    /**
     * Evolves a mario agent controller, being provided with level generator.
     *
     * @return the evolved agent controller
     */
    // TODO: this should be some generic Generator, not EvolutionaryGenerator
    fun evolve(levelGenerator: EvolutionaryGenerator): MarioController

}
