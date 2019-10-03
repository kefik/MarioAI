package cz.cuni.mff.aspect.evolution.controller

import cz.cuni.mff.aspect.evolution.fitnessDistanceJumpsSpecialsHurtsKills
import cz.cuni.mff.aspect.extensions.getDoubleValues
import cz.cuni.mff.aspect.mario.controllers.MarioController
import cz.cuni.mff.aspect.mario.controllers.ann.ControllerArtificialNetwork
import cz.cuni.mff.aspect.mario.controllers.ann.SimpleANNController
import cz.cuni.mff.aspect.mario.level.MarioLevel
import io.jenetics.*
import io.jenetics.engine.Engine
import io.jenetics.engine.EvolutionResult
import java.util.function.Function


/**
 * Implementation of a simple evolution of ANN agent.
 */
class NeuroControllerEvolution(private val controllerNetwork: ControllerArtificialNetwork,
                               private val generationsCount: Long = DEFAULT_GENERATIONS_COUNT,
                               private val populationSize: Int = DEFAULT_POPULATION_SIZE) :
    ControllerEvolution {

    private lateinit var levels: Array<MarioLevel>

    override fun evolve(levels: Array<MarioLevel>): MarioController {
        this.levels = levels
        val genotype = this.createInitialGenotype()
        val engine = this.createEvolutionEngine(genotype)
        val result = this.doEvolution(engine)

        println("Best fitness - ${result.bestFitness}")

        val resultGenes = result.bestPhenotype.genotype.getDoubleValues()
        val controllerNetwork = this.controllerNetwork.newInstance()
        controllerNetwork.setNetworkWeights(resultGenes)

        println(resultGenes.contentToString())

        return SimpleANNController(controllerNetwork)
    }

    private fun createInitialGenotype(): Genotype<DoubleGene> {
        return Genotype.of(DoubleChromosome.of(-5.0, 5.0, this.controllerNetwork.weightsCount))
    }

    private fun createEvolutionEngine(genotype: Genotype<DoubleGene>): Engine<DoubleGene, Float> {
        return Engine.builder(fitness, genotype)
                .optimize(Optimize.MAXIMUM)
                .populationSize(this.populationSize)
                .alterers(SinglePointCrossover(0.3), Mutator(0.05))
                .survivorsSelector(EliteSelector(5))
                .offspringSelector(RouletteWheelSelector())
                .mapping { evolutionResult ->
                    println("new gen: ${evolutionResult.generation} (best fitness: ${evolutionResult.bestFitness})")
                    evolutionResult
                }
                .build()
    }

    private fun doEvolution(evolutionEngine: Engine<DoubleGene, Float>): EvolutionResult<DoubleGene, Float> {
        return evolutionEngine.stream()
                .limit(this.generationsCount)
                .collect(EvolutionResult.toBestEvolutionResult<DoubleGene, Float>())
    }

    private val fitness = Function<Genotype<DoubleGene>, Float> { genotype -> fitness(genotype) }
    private fun fitness(genotype: Genotype<DoubleGene>): Float {
        val networkWeights: DoubleArray = genotype.getDoubleValues()
        val controllerNetwork = this.controllerNetwork.newInstance()
        controllerNetwork.setNetworkWeights(networkWeights)

        val controller = SimpleANNController(controllerNetwork)

        return fitnessDistanceJumpsSpecialsHurtsKills(controller, this.levels)
    }

    companion object {
        private const val DEFAULT_POPULATION_SIZE = 50
        private const val DEFAULT_GENERATIONS_COUNT: Long = 200
    }

}
