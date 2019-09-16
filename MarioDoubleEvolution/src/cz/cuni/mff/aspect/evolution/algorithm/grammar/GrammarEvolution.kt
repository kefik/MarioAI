package cz.cuni.mff.aspect.evolution.algorithm.grammar

import cz.cuni.mff.aspect.evolution.algorithm.grammar.jenetics.ByteChromosome
import cz.cuni.mff.aspect.evolution.algorithm.grammar.jenetics.ByteGene
import cz.cuni.mff.aspect.extensions.getByteValues
import io.jenetics.*
import io.jenetics.engine.Engine
import io.jenetics.engine.EvolutionResult
import io.jenetics.util.IntRange
import java.util.function.Function


class GrammarEvolution(private val grammar: Grammar, private val fitnessComputation: (sentence: GrammarSentence) -> Float) {

    private val genesToSentenceConverter: GenesToSentenceConverter = GenesToSentenceConverter(this.grammar)

    fun evolve(populationSize: Int, generationsCount: Long): GrammarSentence {
        val genotype = this.createInitialGenotype()
        val evolutionEngine = this.createEvolutionEngine(genotype, populationSize)
        val result = this.doEvolution(evolutionEngine, generationsCount)

        val genes = result.bestPhenotype.genotype.getByteValues()
        return this.getGrammarSentence(genes)
    }

    // TODO: the size of the chromosome should definitely be customizable
    private fun createInitialGenotype(): Genotype<ByteGene> = Genotype.of(ByteChromosome.of(IntRange.of(10,30)))

    private fun createEvolutionEngine(initialGenotype: Genotype<ByteGene>, populationSize: Int): Engine<ByteGene, Float> =
        Engine.builder(fitness, initialGenotype)
            .optimize(Optimize.MAXIMUM)
            .populationSize(populationSize)
            .alterers(SinglePointCrossover(0.2), Mutator(0.30))
            .survivorsSelector(EliteSelector(2))
            .offspringSelector(TournamentSelector(3))
            .mapping { evolutionResult ->
                println("[GE] new gen: ${evolutionResult.generation} (best fitness: ${evolutionResult.bestFitness})")
                evolutionResult
            }
            .build()

    private fun doEvolution(evolutionEngine: Engine<ByteGene, Float>, generationsCount: Long): EvolutionResult<ByteGene, Float> =
        evolutionEngine.stream()
            .limit(generationsCount)
            .collect(EvolutionResult.toBestEvolutionResult<ByteGene, Float>())

    private val fitness = Function<Genotype<ByteGene>, Float> { genotype -> fitness(genotype) }
    private fun fitness(genotype: Genotype<ByteGene>): Float {
        val genes = genotype.getByteValues()
        val sentence = this.getGrammarSentence(genes)

        return this.fitnessComputation(sentence)
    }

    private fun getGrammarSentence(genes: ByteArray): GrammarSentence {
        return this.genesToSentenceConverter.convert(genes, MAX_WRAPS_COUNT)
    }

    companion object {
        private const val MAX_WRAPS_COUNT = 100
    }

}
