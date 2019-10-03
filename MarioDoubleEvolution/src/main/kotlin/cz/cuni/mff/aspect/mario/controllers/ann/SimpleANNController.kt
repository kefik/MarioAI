package cz.cuni.mff.aspect.mario.controllers.ann

import ch.idsia.agents.controllers.modules.Entities
import ch.idsia.agents.controllers.modules.Tiles
import ch.idsia.benchmark.mario.engine.generalization.MarioEntity
import cz.cuni.mff.aspect.mario.controllers.MarioAction
import cz.cuni.mff.aspect.mario.controllers.MarioController


/**
 * A very simple controller which uses simple ANN to control mario agent.
 */
class SimpleANNController(private var network: ControllerArtificialNetwork) : MarioController {

    override fun playAction(tiles: Tiles, entities: Entities, mario: MarioEntity): List<MarioAction> {
        return this.network.chooseAction(tiles, entities, mario)
    }

}
