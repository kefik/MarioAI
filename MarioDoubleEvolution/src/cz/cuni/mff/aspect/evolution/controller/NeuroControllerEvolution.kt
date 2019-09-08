package cz.cuni.mff.aspect.evolution.controller

import cz.cuni.mff.aspect.evolution.generator.EvolutionaryGenerator
import cz.cuni.mff.aspect.evolution.generator.MockEvolutionaryGenerator
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.MarioController
import cz.cuni.mff.aspect.mario.controllers.SimpleANNController
import cz.cuni.mff.aspect.mario.controllers.SimpleAgentNetwork
import io.jenetics.*
import io.jenetics.engine.Engine
import io.jenetics.engine.EvolutionResult
import java.util.function.Function


/**
 * Implementation of a simple evolution of ANN agent.
 */
class NeuroControllerEvolution : ControllerEvolution {

    override fun evolve(levelGenerator: EvolutionaryGenerator): MarioController {
        val genotype = this.createInitialGenotype()
        val engine = this.createEvolutionEngine(genotype)
        val result = this.doEvolution(engine)

        val resultGenes = this.genotypeToList(result.bestPhenotype.genotype)
        val controllerNetwork = SimpleAgentNetwork()
        controllerNetwork.setNetworkWeights(resultGenes)

        return SimpleANNController(controllerNetwork)
    }

    private fun createInitialGenotype(): Genotype<DoubleGene> {
        return Genotype.of(DoubleChromosome.of(-1.0, 1.0, SimpleAgentNetwork.TOTAL_WEIGHTS_COUNT))
    }

    private fun createEvolutionEngine(genotype: Genotype<DoubleGene>): Engine<DoubleGene, Float> {
        return Engine.builder(fitness, genotype)
                .optimize(Optimize.MAXIMUM)
                .populationSize(POPULATION_SIZE)
                .alterers(SinglePointCrossover(0.2), Mutator(0.30))
                .survivorsSelector(EliteSelector(2))
                .offspringSelector(TournamentSelector(3))
                .mapping { evolutionResult ->
                    println("new gen: ${evolutionResult.generation} (best fitness: ${evolutionResult.bestFitness})")
                    evolutionResult
                }
                .build()
    }

    private fun doEvolution(evolutionEngine: Engine<DoubleGene, Float>): EvolutionResult<DoubleGene, Float> {
        return evolutionEngine.stream()
                .limit(GENERATIONS_COUNT)
                .collect(EvolutionResult.toBestEvolutionResult<DoubleGene, Float>())
    }

    private val fitness = Function<Genotype<DoubleGene>, Float> { genotype -> fitness(genotype) }
    private fun fitness(genotype: Genotype<DoubleGene>): Float {
        val networkWeights: List<Double> = this.genotypeToList(genotype)

        val network = SimpleAgentNetwork()
        network.setNetworkWeights(networkWeights)

        val controller = SimpleANNController(network)

        val marioSimulator = GameSimulator()
        marioSimulator.playMario(controller, MockEvolutionaryGenerator(), false)

        return marioSimulator.finalDistance
    }

    private fun genotypeToList(genotype: Genotype<DoubleGene>): List<Double> {
        return genotype.chromosome.toSeq().toList<DoubleGene>().map { it.allele }
    }

    companion object {
        private const val POPULATION_SIZE = 50
        private const val GENERATIONS_COUNT: Long = 20
    }

}

