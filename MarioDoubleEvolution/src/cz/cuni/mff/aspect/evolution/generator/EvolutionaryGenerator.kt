package cz.cuni.mff.aspect.evolution.generator

import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.mario.MarioMap

interface EvolutionaryGenerator {

    fun generateMap(): MarioMap

    fun evolve(agent: ControllerEvolution)

}