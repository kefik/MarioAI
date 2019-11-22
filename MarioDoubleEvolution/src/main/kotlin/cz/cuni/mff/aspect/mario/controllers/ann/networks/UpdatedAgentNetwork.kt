package cz.cuni.mff.aspect.mario.controllers.ann.networks

import ch.idsia.agents.controllers.modules.Entities
import ch.idsia.agents.controllers.modules.Tiles
import ch.idsia.benchmark.mario.engine.generalization.MarioEntity
import cz.cuni.mff.aspect.mario.controllers.MarioAction
import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.MultiLayerConfiguration
import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.layers.DenseLayer
import org.deeplearning4j.nn.conf.layers.OutputLayer
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.cpu.nativecpu.NDArray
import org.nd4j.linalg.learning.config.Nesterovs
import java.io.Serializable


/**
 * Neural network controlling [SimpleANNController].
 *
 * The network contains 18 input nodes (3x3 matrix for tiles, and another 3x3 matrix for enemies). It contains
 * one hidden layer with 5 neurons. The output layer has 4 neurons, corresponding to 4 mario actions (run left / right,
 * jump and special).
 */
// TODO: remove 'updated' from the name (won't it break loading of the serialized objects?)
class UpdatedAgentNetwork(private val receptiveFieldSizeRow: Int = 3,
                          private val receptiveFieldSizeColumn: Int = 3,
                          private val receptiveFieldRowOffset: Int = 0,
                          private val receptiveFieldColumnOffset: Int = 1,
                          private val hiddenLayerSize: Int = 7
) : ControllerArtificialNetwork,
    Serializable {

    private val network: MultiLayerNetwork = this.createNetwork()

    override fun compareTo(other: ControllerArtificialNetwork): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val weightsCount: Int get() = this.inputLayerSize * this.hiddenLayerSize + this.hiddenLayerSize * OUTPUT_LAYER_SIZE
    private val inputLayerSize: Int get() = 2 * this.receptiveFieldSizeRow * this.receptiveFieldSizeColumn * 4

    override fun newInstance(): ControllerArtificialNetwork =
        UpdatedAgentNetwork(
            this.receptiveFieldSizeRow,
            this.receptiveFieldSizeColumn,
            this.receptiveFieldRowOffset,
            this.receptiveFieldColumnOffset,
            this.hiddenLayerSize
        )

    override fun chooseAction(tiles: Tiles, entities: Entities, mario: MarioEntity): List<MarioAction> {
        val input = this.createInput(tiles, entities, mario)
        val output = this.network.output(NDArray(arrayOf(input)))
        val actions: ArrayList<MarioAction> = ArrayList()

        this.addActionIfOutputActivated(actions, output, 0, MarioAction.RUN_LEFT)
        this.addActionIfOutputActivated(actions, output, 1, MarioAction.RUN_RIGHT)
        this.addActionIfOutputActivated(actions, output, 2, MarioAction.JUMP)
        this.addActionIfOutputActivated(actions, output, 3, MarioAction.SPECIAL)

        return actions
    }

    override fun setNetworkWeights(weights: DoubleArray) {
        val weightsTable = this.network.paramTable()
        val weightsKeysIterator = weightsTable.keys.iterator()
        var currentWeight = 0

        while (weightsKeysIterator.hasNext()) {
            val weightTableKey = weightsKeysIterator.next()
            // Types of keys: x_W (weights from layer x to x+1); x_b (weights of bias from layer x to x+1)
            val layerWeightsValues = weightsTable[weightTableKey] ?: continue

            if (weightTableKey.contains("W")) {
                val shape = layerWeightsValues.shape()
                val layerWeights = this.network.getParam(weightTableKey)
                for (x in 0 until shape[0]) {
                    for (y in 0 until shape[1]) {
                        layerWeights.putScalar(x, y, weights[currentWeight])
                        currentWeight++
                    }
                }
            }

        }
    }

    private fun createNetwork(): MultiLayerNetwork {
        val multiLayerConf: MultiLayerConfiguration = NeuralNetConfiguration.Builder()
            .seed(123).learningRate(0.1).iterations(1).optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).updater(Nesterovs(0.9)).biasInit(1.0)
            .list()
            .layer(0, DenseLayer.Builder().nIn(this.inputLayerSize).nOut(this.hiddenLayerSize).weightInit(WeightInit.XAVIER).activation(Activation.RELU).build())
            .layer(1, OutputLayer.Builder().nIn(this.hiddenLayerSize).nOut(OUTPUT_LAYER_SIZE).weightInit(WeightInit.XAVIER).activation(Activation.SIGMOID).build())
            .pretrain(false).backprop(false)
            .build()

        val multiLayerNetwork = MultiLayerNetwork(multiLayerConf)
        multiLayerNetwork.init()

        return multiLayerNetwork
    }

    private fun createInput(tiles: Tiles, entities: Entities, mario: MarioEntity): DoubleArray {
        return NetworkInputBuilder()
            .tiles(tiles)
            .entities(entities)
            .mario(mario)
            .receptiveFieldSize(this.receptiveFieldSizeRow, this.receptiveFieldSizeColumn)
            .receptiveFieldOffset(this.receptiveFieldRowOffset, this.receptiveFieldColumnOffset)
            .buildDouble()
    }

    private fun addActionIfOutputActivated(actions: ArrayList<MarioAction>, output: INDArray, outputIndex: Int, action: MarioAction) {
        if ((output.getScalar(0, outputIndex).element() as Float) > CHOOSE_ACTION_THRESHOLD) {
            actions.add(action)
        }
    }

    companion object {
        private const val OUTPUT_LAYER_SIZE = 4
        private const val CHOOSE_ACTION_THRESHOLD = 0.95
    }
}
