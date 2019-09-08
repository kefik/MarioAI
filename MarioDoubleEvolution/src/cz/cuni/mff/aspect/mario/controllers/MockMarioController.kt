package cz.cuni.mff.aspect.mario.controllers

import ch.idsia.agents.controllers.modules.Entities
import ch.idsia.agents.controllers.modules.Tiles


/**
 * Mario controller, which does nothing.
 */
class MockMarioController : MarioController {

    override fun playAction(tiles: Tiles, entities: Entities): List<MarioAction> {
        return emptyList()
    }

}