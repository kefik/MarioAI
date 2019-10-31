package cz.cuni.mff.aspect.mario.controllers.ann.networks;

import ch.idsia.agents.controllers.modules.Entities
import ch.idsia.agents.controllers.modules.Tiles
import ch.idsia.benchmark.mario.engine.generalization.MarioEntity
import com.evo.NEAT.Genome
import cz.cuni.mff.aspect.mario.controllers.MarioAction
import cz.cuni.mff.aspect.mario.controllers.ann.NetworkSettings


class NeatAgentNetwork(private val networkSettings: NetworkSettings, private val genome: Genome) : ControllerArtificialNetwork {
    override fun chooseAction(tiles: Tiles, entities: Entities, mario: MarioEntity): List<MarioAction> {
        val input: FloatArray = this.createInput(tiles, entities, mario)
        val output: FloatArray = this.genome.evaluateNetwork(input)
        val actions: ArrayList<MarioAction> = ArrayList()

        this.addActionIfOutputActivated(actions, output, 0, MarioAction.RUN_LEFT)
        this.addActionIfOutputActivated(actions, output, 1, MarioAction.RUN_RIGHT)
        this.addActionIfOutputActivated(actions, output, 2, MarioAction.JUMP)
        this.addActionIfOutputActivated(actions, output, 3, MarioAction.SPECIAL)

        return actions
    }

    override fun newInstance(): ControllerArtificialNetwork {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setNetworkWeights(weights: DoubleArray) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun compareTo(other: ControllerArtificialNetwork): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val weightsCount: Int get() = this.inputLayerSize * this.networkSettings.hiddenLayerSize + this.networkSettings.hiddenLayerSize * OUTPUT_LAYER_SIZE
    private val inputLayerSize: Int get() = 2 * this.networkSettings.receptiveFieldSizeRow * this.networkSettings.receptiveFieldSizeColumn

    // TODO: generalise this pls :(
    private fun createInput(tiles: Tiles, entities: Entities, mario: MarioEntity): FloatArray {
        val marioX = mario.egoCol
        val marioY = mario.egoRow

        val flatTiles = MutableList(this.networkSettings.receptiveFieldSizeRow * this.networkSettings.receptiveFieldSizeColumn) { 0.0f }
        val flatEntities = MutableList(this.networkSettings.receptiveFieldSizeRow * this.networkSettings.receptiveFieldSizeColumn) { 0.0f }
        val receptiveFieldRowMiddle: Int = this.networkSettings.receptiveFieldSizeRow / 2
        val receptiveFieldColumnMiddle: Int = this.networkSettings.receptiveFieldSizeColumn / 2

        for (i in 0 until this.networkSettings.receptiveFieldSizeRow * this.networkSettings.receptiveFieldSizeColumn) {
            val row =
                i / this.networkSettings.receptiveFieldSizeRow - receptiveFieldRowMiddle + this.networkSettings.receptiveFieldRowOffset
            val column =
                i % this.networkSettings.receptiveFieldSizeColumn - receptiveFieldColumnMiddle + this.networkSettings.receptiveFieldColumnOffset

            val tileAtPosition = tiles.tileField[marioY + row][marioX + column]
            val tileCode = when (tileAtPosition.code) {
                -60 -> -1.0
                else -> tileAtPosition.code.toDouble()
            }
            flatTiles[i] = tileCode.toFloat()

            val entitiesAtPosition = entities.entityField[marioY + row][marioX + column]
            flatEntities[i] = if (entitiesAtPosition.size > 0) entitiesAtPosition[0].type.code.toFloat() else 0.0f
        }

        return FloatArray(this.inputLayerSize + 2) {
            when {
                it == this.inputLayerSize + 1 -> mario.dX
                it == this.inputLayerSize + 0 -> mario.dY
                it >= flatEntities.size -> flatTiles[it - flatEntities.size]
                else -> flatEntities[it]
            }
        }
    }


    // TODO: generalise this too!
    private fun addActionIfOutputActivated(actions: ArrayList<MarioAction>, output: FloatArray, outputIndex: Int, action: MarioAction) {
        if ((output[outputIndex]) > CHOOSE_ACTION_THRESHOLD) {
            actions.add(action)
        }
    }

    companion object {
        private const val OUTPUT_LAYER_SIZE = 4
        private const val CHOOSE_ACTION_THRESHOLD = 0.95
    }

}
