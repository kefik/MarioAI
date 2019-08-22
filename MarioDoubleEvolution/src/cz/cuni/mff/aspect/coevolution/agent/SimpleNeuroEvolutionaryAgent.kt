package cz.cuni.mff.aspect.coevolution.agent

import ch.idsia.agents.controllers.modules.Entities
import ch.idsia.agents.controllers.modules.Tiles
import ch.idsia.benchmark.mario.engine.generalization.Entity
import cz.cuni.mff.aspect.coevolution.generator.EvolutionaryGenerator
import cz.cuni.mff.aspect.coevolution.generator.MockEvolutionaryGenerator
import cz.cuni.mff.aspect.mario.MarioSimulator
import io.jenetics.*
import io.jenetics.engine.Engine
import io.jenetics.engine.EvolutionResult
import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.MultiLayerConfiguration
import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.layers.DenseLayer
import org.deeplearning4j.nn.conf.layers.OutputLayer
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.cpu.nativecpu.NDArray
import org.nd4j.linalg.learning.config.Nesterovs
import java.util.function.Function


/**
 * Implementation of a very simple neuroevolutionary agent.
 * TODO: trochu retardovany design... evolucia je zaroven agent -> pri pocutani fitness vytvorim noveho evolutionagenta pre kazdy chromosome a ...
 */
class SimpleNeuroEvolutionaryAgent : EvolutionaryAgent {

    private lateinit var bestChromosome: EvolutionResult<DoubleGene, Float>
    private lateinit var network: SimpleAgentNetwork

    override fun playAction(tiles: Tiles, entities: Entities): List<MarioAction> {
        return this.network.chooseAction(tiles, entities)
    }

    override fun evolve(levelGenerator: EvolutionaryGenerator) {
        val genotype = Genotype.of(DoubleChromosome.of(-1.0, 1.0, SimpleAgentNetwork.TOTAL_WEIGHTS_COUNT))

        val engine: Engine<DoubleGene, Float> =
                Engine.builder(fitness, genotype)
                        .optimize(Optimize.MAXIMUM)
                        .populationSize(POPULATION_SIZE)
                        .alterers(SinglePointCrossover(0.2), Mutator(0.30))
                        .survivorsSelector(EliteSelector(2))
                        .offspringSelector(TournamentSelector(3))
                        .mapping { evolutionResult ->
                            println("new gen: ${evolutionResult.generation} (best fitness: ${evolutionResult.bestFitness})")
                            this.network = SimpleAgentNetwork.createFromWeights(evolutionResult.bestPhenotype.genotype.chromosome.toSeq().toList().map {it.allele})
                            evolutionResult
                        }
                        .build()

        val result = engine.stream()
                    .limit(GENERATIONS_COUNT)
                .collect(EvolutionResult.toBestEvolutionResult<DoubleGene, Float>())

        this.bestChromosome = result

        val genes = result.bestPhenotype.genotype.chromosome.toSeq().toList()
        this.network = SimpleAgentNetwork.createFromWeights(genes.map { it.allele })
    }

    private val fitness = Function<Genotype<DoubleGene>, Float> { genotype -> fitness(genotype) }
    private fun fitness(gt: Genotype<DoubleGene>): Float {
        // println("COMPUTING FITNESS: ")
        val networkWeights: List<Double> = List<Double>(gt.chromosome.length()) { gt.chromosome.getGene(it).allele }
        val network = SimpleAgentNetwork.createFromWeights(networkWeights)
        val agent = SimpleNeuroEvolutionaryAgent()
        agent.network = network

        val marioSimulator = MarioSimulator()
        marioSimulator.playMario(agent, MockEvolutionaryGenerator(), false)

        println("Fitness: ${marioSimulator.finalDistance}")
        return marioSimulator.finalDistance
    }

    companion object {
        private const val POPULATION_SIZE = 50
        private const val GENERATIONS_COUNT: Long = 20
    }

}

/**
 * A simple neural network controlling mario.
 */
class SimpleAgentNetwork(private val network: MultiLayerNetwork) : Comparable<SimpleAgentNetwork> {

    override fun compareTo(other: SimpleAgentNetwork): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun chooseAction(tiles: Tiles, entities: Entities): List<MarioAction> {
        val input = this.createInput(tiles, entities)
        val output = this.network.output(NDArray(Array(1){input}))
        val actions: ArrayList<MarioAction> = ArrayList()

        if ((output.getScalar(0, 0).element() as Float) > CHOOSE_ACTION_THRESHOLD) {
            actions.add(MarioAction.RUN_LEFT)
        }
        if ((output.getScalar(0, 1).element() as Float) > CHOOSE_ACTION_THRESHOLD) {
            actions.add(MarioAction.RUN_RIGHT)
        }
        if ((output.getScalar(0, 2).element() as Float) > CHOOSE_ACTION_THRESHOLD) {
            actions.add(MarioAction.JUMP)
        }
        if ((output.getScalar(0, 3).element() as Float) > CHOOSE_ACTION_THRESHOLD) {
            actions.add(MarioAction.SPECIAL)
        }

        return actions
    }

    fun createInput(tiles: Tiles, entities: Entities): DoubleArray {
        val fieldHalfSize = tiles.tileField.size / 2
        val receptiveFieldSquared = RECEPTIVE_FIELD_SIZE * RECEPTIVE_FIELD_SIZE
        val flatTiles: List<Double> = List(receptiveFieldSquared) {
            tiles.tileField[fieldHalfSize + (it / RECEPTIVE_FIELD_SIZE - RECEPTIVE_FIELD_SIZE / 2)][fieldHalfSize + (it % RECEPTIVE_FIELD_SIZE - RECEPTIVE_FIELD_SIZE / 2)].code.toDouble()
        }
        val flatEntities: List<Double> = List(receptiveFieldSquared) {
            val enemiesList = (entities.entityField[fieldHalfSize + (it / RECEPTIVE_FIELD_SIZE - RECEPTIVE_FIELD_SIZE / 2)][fieldHalfSize + (it % RECEPTIVE_FIELD_SIZE - RECEPTIVE_FIELD_SIZE / 2)] as List<Entity<*>>)
            if (enemiesList.isEmpty()) 0.0 else  enemiesList[0].type.code.toDouble()
        }

        return DoubleArray(INPUT_SIZE) {
            if (it >= receptiveFieldSquared) {
                flatTiles[it - receptiveFieldSquared]
            } else {
                flatEntities[it]
            }
        }
    }

    companion object {
        private const val RECEPTIVE_FIELD_SIZE = 3
        private const val INPUT_SIZE = RECEPTIVE_FIELD_SIZE * RECEPTIVE_FIELD_SIZE + RECEPTIVE_FIELD_SIZE * RECEPTIVE_FIELD_SIZE
        private const val HIDDEN_LAYER_SIZE = 5
        private const val OUTPUT_SIZE = 4
        const val TOTAL_WEIGHTS_COUNT = INPUT_SIZE * HIDDEN_LAYER_SIZE + HIDDEN_LAYER_SIZE * OUTPUT_SIZE

        const val CHOOSE_ACTION_THRESHOLD = 0.95

        // TODO: refactor me
        fun createFromWeights(weights: List<Double>): SimpleAgentNetwork {
            val multiLayerConf: MultiLayerConfiguration = NeuralNetConfiguration.Builder()
                    .seed(123).learningRate(0.1).iterations(1).optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).updater(Nesterovs(0.9)).biasInit(1.0)
                    .list()
                    .layer(0, DenseLayer.Builder().nIn(INPUT_SIZE).nOut(HIDDEN_LAYER_SIZE).weightInit(WeightInit.XAVIER).activation(Activation.RELU).build())
                    .layer(1, OutputLayer.Builder().nIn(HIDDEN_LAYER_SIZE).nOut(OUTPUT_SIZE).weightInit(WeightInit.XAVIER).activation(Activation.SIGMOID).build())
                    .pretrain(false).backprop(false)
                    .build()

            val multiLayerNetwork = MultiLayerNetwork(multiLayerConf)
            multiLayerNetwork.init()

            val paramTable = multiLayerNetwork.paramTable()
            val keys = paramTable.keys
            val it = keys.iterator()
            var currentWeight = 0

            while (it.hasNext()) {
                val key = it.next()
                // Types of keys: x_W (weights from layer x to x+1); x_b (weights of bias from layer x to x+1)
                val values = paramTable[key] ?: continue

                if (key.contains("W")) {
                    val shape = values.shape()
                    val layerWeights = multiLayerNetwork.getParam(key)
                    for (x in 0 until shape[0]) {
                        for (y in 0 until shape[1]) {
                            layerWeights.putScalar(x, y, weights[currentWeight])
                            currentWeight++
                        }
                    }
                }

            }


            return SimpleAgentNetwork(multiLayerNetwork)
        }
    }


}
