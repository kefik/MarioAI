package cz.cuni.mff.aspect.evolution.controller

import cz.cuni.mff.aspect.evolution.Fitness
import cz.cuni.mff.aspect.extensions.getDoubleValues
import cz.cuni.mff.aspect.mario.controllers.MarioController
import cz.cuni.mff.aspect.mario.controllers.ann.SimpleANNController
import cz.cuni.mff.aspect.mario.controllers.ann.networks.ControllerArtificialNetwork
import cz.cuni.mff.aspect.mario.level.MarioLevel
import cz.woitee.endlessRunners.evolution.utils.MyConcurrentEvaluator
import io.jenetics.*
import io.jenetics.engine.Engine
import io.jenetics.engine.EvolutionResult
import io.jenetics.internal.util.Concurrency
import io.jenetics.util.Factory
import java.util.concurrent.ForkJoinPool
import java.util.function.Function


/**
 * Implementation of an evolution of ANN agent controller.
 */
class NeuroControllerEvolution(
    private val controllerNetwork: ControllerArtificialNetwork,
    private val generationsCount: Long = DEFAULT_GENERATIONS_COUNT,
    private val populationSize: Int = DEFAULT_POPULATION_SIZE,
    private val parallel: Boolean = true
) : ControllerEvolution {
    
    private lateinit var levels: Array<MarioLevel>
    private lateinit var fitnessFunction: Fitness

    override fun evolve(levels: Array<MarioLevel>, fitness: Fitness): MarioController {
        this.levels = levels
        this.fitnessFunction = fitness

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
        return Genotype.of(DoubleChromosome.of(-2.0, 2.0, this.controllerNetwork.weightsCount))
    }

    private fun createEvolutionEngine(genotype: Genotype<DoubleGene>): Engine<DoubleGene, Float> {
        val executor = if (this.parallel) ForkJoinPool.commonPool() else Concurrency.SERIAL_EXECUTOR
        val evaluator = MyConcurrentEvaluator(executor, fitness, alwaysEvaluate = true)
        val genotypeFactory = Factory<Genotype<DoubleGene>> { genotype }

        return Engine.Builder(evaluator, genotypeFactory)
                .optimize(Optimize.MAXIMUM)
                .populationSize(this.populationSize)
                .alterers(Mutator(0.05))
                .survivorsSelector(EliteSelector(2))
                .offspringSelector(TournamentSelector())
                .build()
    }

    private fun doEvolution(evolutionEngine: Engine<DoubleGene, Float>): EvolutionResult<DoubleGene, Float> {
        return evolutionEngine.stream()
            .limit(this.generationsCount)
            .peek { evolutionResult ->
                println("new gen: ${evolutionResult.generation} (best fitness: ${evolutionResult.bestFitness})")
            }
            .collect(EvolutionResult.toBestEvolutionResult<DoubleGene, Float>())
    }

    private val fitness = Function<Genotype<DoubleGene>, Float> { genotype -> fitness(genotype) }
    private fun fitness(genotype: Genotype<DoubleGene>): Float {
        val networkWeights: DoubleArray = genotype.getDoubleValues()
        val controllerNetwork = this.controllerNetwork.newInstance()
        controllerNetwork.setNetworkWeights(networkWeights)

        val controller = SimpleANNController(controllerNetwork)

        return this.fitnessFunction(controller, this.levels)
    }

    companion object {
        private const val DEFAULT_POPULATION_SIZE = 50
        private const val DEFAULT_GENERATIONS_COUNT: Long = 200
    }

}
