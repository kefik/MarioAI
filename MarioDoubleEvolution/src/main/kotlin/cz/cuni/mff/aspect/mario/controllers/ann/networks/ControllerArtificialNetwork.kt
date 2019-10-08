package cz.cuni.mff.aspect.mario.controllers.ann.networks

import ch.idsia.agents.controllers.modules.Entities
import ch.idsia.agents.controllers.modules.Tiles
import ch.idsia.benchmark.mario.engine.generalization.MarioEntity
import cz.cuni.mff.aspect.mario.controllers.MarioAction


/**
 * Interface for artificial networks used in {@link SimpleANNController} to control Mario.
 */
interface ControllerArtificialNetwork : Comparable<ControllerArtificialNetwork> {

    val weightsCount: Int

    fun setNetworkWeights(weights: DoubleArray)

    fun chooseAction(tiles: Tiles, entities: Entities, mario: MarioEntity): List<MarioAction>

    fun newInstance(): ControllerArtificialNetwork

}
