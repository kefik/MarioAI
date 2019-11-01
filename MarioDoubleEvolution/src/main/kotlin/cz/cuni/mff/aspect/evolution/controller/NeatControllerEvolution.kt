package cz.cuni.mff.aspect.evolution.controller

import com.evo.NEAT.Environment
import com.evo.NEAT.Genome
import com.evo.NEAT.Pool
import cz.cuni.mff.aspect.evolution.fitnessDistanceLeastActions
import cz.cuni.mff.aspect.mario.controllers.MarioController
import cz.cuni.mff.aspect.mario.controllers.ann.NetworkSettings
import cz.cuni.mff.aspect.mario.controllers.ann.SimpleANNController
import cz.cuni.mff.aspect.mario.controllers.ann.networks.NeatAgentNetwork
import cz.cuni.mff.aspect.mario.level.MarioLevel
import cz.cuni.mff.aspect.storage.NeatAIStorage
import cz.cuni.mff.aspect.visualisation.EvolutionLineChart
import java.util.*
import kotlin.math.min


class NeatControllerEvolution(
    private val networkSettings: NetworkSettings,
    private var generationsCount: Int = 50
) : ControllerEvolution {

    private lateinit var topGenome: Genome

    class ControllerEvolution(private val levels: Array<MarioLevel>, private val networkSettings: NetworkSettings) : Environment {

        override fun evaluateFitness(population: ArrayList<Genome>) {
            for (genome in population) {
                val neatNetwork = NeatAgentNetwork(this.networkSettings, genome)
                val controller = SimpleANNController(neatNetwork)
                val fitness = fitnessDistanceLeastActions(controller, this.levels)
                genome.fitness = fitness
            }
        }

    }

    override fun evolve(levels: Array<MarioLevel>): MarioController {
        val evolution = ControllerEvolution(levels, this.networkSettings)
        val networkInputSize = NeatAgentNetwork(this.networkSettings, Genome(0,0)).inputLayerSize
        val networkOutputSize = 4
        val pool = Pool(100)
        val chart = EvolutionLineChart(label = "NEAT Evolution Stage 4 Level 1", hideNegative = true)

        pool.initializePool(networkInputSize, networkOutputSize)
        chart.show()

        var currentGeneration = listOf<Genome>()
        var generation = 1

        while (generation < this.generationsCount) {
            pool.evaluateFitness(evolution)
            val topGenome = pool.topGenome

            val averageFitness = this.getAverageFitness(currentGeneration)
            val minFitness = this.getMinFitness(currentGeneration)
            val maxFitness = topGenome.points
            if (currentGeneration.isNotEmpty()) {
                chart.update(generation, maxFitness.toDouble(), averageFitness.toDouble(), if (minFitness >= 0.0) minFitness.toDouble() else 0.0)
            }
            println("Neat gen: $generation: Fitness - Max: $maxFitness, Avg: $averageFitness, Min: $minFitness}")

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
        return population.fold(0.0f, { accumulator, genome -> accumulator + genome.points}) / population.size
    }

    private fun getMinFitness(population: List<Genome>): Float {
        return population.fold(Float.MAX_VALUE, { accumulator, genome -> min(accumulator, genome.points) })
    }

}