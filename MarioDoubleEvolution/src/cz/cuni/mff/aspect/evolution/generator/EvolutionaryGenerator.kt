package cz.cuni.mff.aspect.evolution.generator

import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.mario.Map

interface EvolutionaryGenerator {

    fun generateMap(): Map

    fun evolve(agent: ControllerEvolution)

}