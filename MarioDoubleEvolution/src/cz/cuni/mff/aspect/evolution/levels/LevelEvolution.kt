package cz.cuni.mff.aspect.evolution.levels

import cz.cuni.mff.aspect.mario.MarioAgent
import cz.cuni.mff.aspect.mario.level.MarioLevel


/**
 * Interface representing evolution of mario levels. The evolution can return multiple levels.
 */
interface LevelEvolution {

    fun evolve(agent: MarioAgent): Array<MarioLevel>

}