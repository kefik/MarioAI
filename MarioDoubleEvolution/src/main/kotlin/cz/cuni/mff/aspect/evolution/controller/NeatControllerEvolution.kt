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
import java.util.*


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
        val pool = Pool()
        pool.initializePool()

        var topGenome = Genome()
        var generation = 0

        while (generation < this.generationsCount) {
            pool.evaluateFitness(evolution)

            topGenome = pool.topGenome
            println("GEN $generation, top genome: ${topGenome.points}")
            pool.breedNewGeneration()
            generation++
        }

        this.topGenome = topGenome
        val network = NeatAgentNetwork(this.networkSettings, this.topGenome)

        NeatAIStorage.storeAi(NeatAIStorage.FIRST_NEAT_AI, this.topGenome)

        return SimpleANNController(network)
    }

}