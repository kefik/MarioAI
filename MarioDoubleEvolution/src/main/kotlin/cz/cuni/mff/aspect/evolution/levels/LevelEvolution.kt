package cz.cuni.mff.aspect.evolution.levels

import cz.cuni.mff.aspect.mario.controllers.MarioController
import cz.cuni.mff.aspect.mario.level.MarioLevel


/**
 * Interface representing evolution of mario levels. The evolution can return multiple levels.
 */
interface LevelEvolution {

    fun evolve(controller: MarioController): Array<MarioLevel>

}