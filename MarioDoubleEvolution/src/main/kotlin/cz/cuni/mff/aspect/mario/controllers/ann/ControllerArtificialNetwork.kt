package cz.cuni.mff.aspect.mario.controllers.ann

import ch.idsia.agents.controllers.modules.Entities
import ch.idsia.agents.controllers.modules.Tiles
import cz.cuni.mff.aspect.mario.controllers.MarioAction


/**
 * Interface for artificial networks used in {@link SimpleANNController} to control Mario.
 */
interface ControllerArtificialNetwork : Comparable<ControllerArtificialNetwork> {

    fun chooseAction(tiles: Tiles, entities: Entities): List<MarioAction>

}
