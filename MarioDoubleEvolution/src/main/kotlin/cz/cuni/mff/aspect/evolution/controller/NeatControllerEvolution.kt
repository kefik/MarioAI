package cz.cuni.mff.aspect.evolution.controller

import com.evo.NEAT.Environment
import com.evo.NEAT.Genome
import com.evo.NEAT.Pool
import cz.cuni.mff.aspect.evolution.utils.MarioGameplayEvaluator
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.MarioController
import cz.cuni.mff.aspect.mario.controllers.ann.NetworkSettings
import cz.cuni.mff.aspect.mario.controllers.ann.SimpleANNController
import cz.cuni.mff.aspect.mario.controllers.ann.networks.NeatAgentNetwork
import cz.cuni.mff.aspect.mario.level.MarioLevel
import cz.cuni.mff.aspect.storage.NeatAIStorage
import cz.cuni.mff.aspect.visualisation.EvolutionLineChart
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.min


class NeatControllerEvolution(
    private val networkSettings: NetworkSettings,
    private var generationsCount: Int = 200,
    private val populationSize: Int = 150,
    private val chartName: String = "NEAT Evolution"
) : ControllerEvolution {

    private lateinit var topGenome: Genome

    class ControllerEvolutionEnvironment(private val levels: Array<MarioLevel>,
                                         private val networkSettings: NetworkSettings,
                                         private val fitness: MarioGameplayEvaluator<Float>,
                                         private val levelsCount: Int = 0) : Environment {

        override fun evaluateFitness(population: ArrayList<Genome>) {
            for (genome in population) {
                val neatNetwork = NeatAgentNetwork(this.networkSettings, genome)
                val controller = SimpleANNController(neatNetwork)

                val marioSimulator = GameSimulator()
                // TODO: levelsCount as parameter to the evolution
                val statistics = marioSimulator.playRandomLevels(controller, this.levels, 999, false)

                genome.fitness = fitness(statistics)
            }
        }
    }

    override fun evolve(levels: Array<MarioLevel>, fitness: MarioGameplayEvaluator<Float>, objective: MarioGameplayEvaluator<Float>): MarioController {
        val startTime = System.currentTimeMillis()
        val evolution = ControllerEvolutionEnvironment(levels, this.networkSettings, fitness)
        val networkInputSize = NeatAgentNetwork(this.networkSettings, Genome(0,0)).inputLayerSize
        val networkOutputSize = 4
        val pool = Pool(this.populationSize)
        val chart = EvolutionLineChart(label = this.chartName, hideNegative = true)

        var currentGeneration = pool.initializePool(networkInputSize, networkOutputSize)
        chart.show()

        var generation = 1

        while (generation < this.generationsCount) {
            pool.evaluateFitness(evolution)
            val topGenome = pool.topGenome

            val averageFitness = this.getAverageFitness(currentGeneration)
            val minFitness = this.getMinFitness(currentGeneration)
            val maxFitness = topGenome.points

            chart.update(generation, maxFitness.toDouble(), averageFitness.toDouble(), 0.0, 0.0)
            val currentTimeMillis = System.currentTimeMillis() - startTime
            val timeString = String.format("%02d min, %02d sec",
                TimeUnit.MILLISECONDS.toMinutes(currentTimeMillis),
                TimeUnit.MILLISECONDS.toSeconds(currentTimeMillis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentTimeMillis))
            );
            println("($timeString) Neat gen: $generation: Fitness - Max: $maxFitness, Avg: $averageFitness, Min: $minFitness}")

            currentGeneration = pool.breedNewGeneration()
            generation++
        }

        this.topGenome = pool.topGenome
        val network = NeatAgentNetwork(this.networkSettings, this.topGenome)

        NeatAIStorage.storeAi(NeatAIStorage.LATEST, this.topGenome)
        chart.save("latest.svg")

        return SimpleANNController(network)
    }

    private fun getAverageFitness(population: List<Genome>): Float {
        return population.fold(0.0f, { accumulator, genome -> accumulator + genome.points }) / population.size
    }

    private fun getMinFitness(population: List<Genome>): Float {
        return population.fold(Float.MAX_VALUE, { accumulator, genome -> min(accumulator, genome.points) })
    }

}
