package cz.cuni.mff.aspect.doubleev.agent

import cz.cuni.mff.aspect.doubleev.game.MarioMap

interface EvolutionaryAgent {

    fun evolve(map: MarioMap)

}