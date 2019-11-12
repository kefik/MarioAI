package cz.cuni.mff.aspect.evolution.controller

import cz.cuni.mff.aspect.evolution.Fitness
import cz.cuni.mff.aspect.extensions.getDoubleValues
import cz.cuni.mff.aspect.mario.controllers.MarioController
import cz.cuni.mff.aspect.mario.controllers.ann.SimpleANNController
import cz.cuni.mff.aspect.mario.controllers.ann.networks.ControllerArtificialNetwork
import cz.cuni.mff.aspect.mario.level.MarioLevel
import cz.cuni.mff.aspect.visualisation.EvolutionLineChart
import cz.woitee.endlessRunners.evolution.utils.MarioEvaluator
import io.jenetics.*
import io.jenetics.engine.Engine
import io.jenetics.engine.EvolutionResult
import io.jenetics.internal.util.Concurrency
import io.jenetics.util.DoubleRange
import io.jenetics.util.Factory
import java.util.concurrent.ForkJoinPool


/**
 * Implementation of an evolution of ANN agent controller.
 */
class NeuroControllerEvolution(
    private val controllerNetwork: ControllerArtificialNetwork,
    private val generationsCount: Long = DEFAULT_GENERATIONS_COUNT,
    private val populationSize: Int = DEFAULT_POPULATION_SIZE,
    private val parallel: Boolean = true,
    private val alterers: Array<Alterer<DoubleGene, Float>> = arrayOf(Mutator(0.05)),
    private val survivorsSelector: Selector<DoubleGene, Float> = EliteSelector(2),
    private val offspringSelector: Selector<DoubleGene, Float> = TournamentSelector(2),
    private val weightsRange: DoubleRange = DoubleRange.of(-1.0, 1.0),
    chartLabel: String = "NeuroController evolution"
) : ControllerEvolution {

    private var chart = EvolutionLineChart(chartLabel, hideNegative = true)
    private lateinit var levels: Array<MarioLevel>
    private lateinit var fitnessFunction: Fitness<Float>
    private lateinit var objectiveFunction: Fitness<Float>

    override fun evolve(levels: Array<MarioLevel>, fitness: Fitness<Float>, objective: Fitness<Float>): MarioController {
        this.levels = levels
        this.fitnessFunction = fitness
        this.objectiveFunction = objective
        this.chart.show()

        val genotype = this.createInitialGenotype()
        val evaluator = this.createEvaluator()
        val engine = this.createEvolutionEngine(genotype, evaluator)
        val result = this.doEvolution(engine, evaluator)

        println("Best fitness - ${result.bestFitness}")

        val resultGenes = result.bestPhenotype.genotype.getDoubleValues()
        val controllerNetwork = this.controllerNetwork.newInstance()
        controllerNetwork.setNetworkWeights(resultGenes)

        println(resultGenes.contentToString())

        return SimpleANNController(controllerNetwork)
    }

    fun storeChart(path: String) {
        this.chart.save(path)
    }

    private fun createInitialGenotype(): Genotype<DoubleGene> {
        return Genotype.of(DoubleChromosome.of(this.weightsRange, this.controllerNetwork.weightsCount))
    }

    private fun createEvaluator(): MarioEvaluator<DoubleGene, Float> {
        val executor = if (this.parallel) ForkJoinPool.commonPool() else Concurrency.SERIAL_EXECUTOR

        return MarioEvaluator(
            executor,
            fitnessFunction,
            objectiveFunction,
            controllerNetwork,
            levels,
            alwaysEvaluate = true
        )
    }

    // TODO: this should get the genotype factory as a parameter
    private fun createEvolutionEngine(genotype: Genotype<DoubleGene>, evaluator: MarioEvaluator<DoubleGene, Float>): Engine<DoubleGene, Float> {
        val genotypeFactory = Factory<Genotype<DoubleGene>> { genotype }

        val engine = Engine.Builder(evaluator, genotypeFactory)
                .optimize(Optimize.MAXIMUM)
                .populationSize(this.populationSize)

        if (this.alterers.isNotEmpty()) {
            val (first, rest) = this.getFirstAndRest(this.alterers)
            engine.alterers(first, *rest)
        }

        return engine
            .survivorsSelector(this.survivorsSelector)
            .offspringSelector(this.offspringSelector)
            .build()
    }

    private inline fun <reified T> getFirstAndRest(data: Array<T>): Pair<T, Array<T>> {
        val rest = Array(data.size - 1) { data[it + 1] }
        return Pair(data[0], rest)
    }

    private fun doEvolution(evolutionEngine: Engine<DoubleGene, Float>, evaluator: MarioEvaluator<DoubleGene, Float>): EvolutionResult<DoubleGene, Float> {
        return evolutionEngine.stream()
            .limit(this.generationsCount)
            .peek {
                this.chart.update(it.generation.toInt(), it.bestFitness.toDouble(), this.getAverageFitness(it).toDouble(), it.worstFitness.toDouble())
                println("new gen: ${it.generation} (best fitness: ${it.bestFitness}, best objective: ${evaluator.getBestObjectiveFromLastGeneration()})")
            }
            .collect(EvolutionResult.toBestEvolutionResult<DoubleGene, Float>())
    }

    private fun getAverageFitness(evolutionResult: EvolutionResult<DoubleGene, Float>): Float {
        return evolutionResult.population.asList().fold(0.0f, {accumulator, genotype -> accumulator + genotype.fitness}) / evolutionResult.population.length()
    }

    companion object {
        private const val DEFAULT_POPULATION_SIZE = 50
        private const val DEFAULT_GENERATIONS_COUNT: Long = 200
    }

}
