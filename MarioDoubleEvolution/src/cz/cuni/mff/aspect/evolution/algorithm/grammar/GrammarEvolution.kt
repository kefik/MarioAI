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

    fun evolve(populationSize: Int, generationsCount: Long): GrammarSentence {
        val genotype = this.createInitialGenotype()
        val evolutionEngine = this.createEvolutionEngine(genotype, populationSize)
        val result = this.doEvolution(evolutionEngine, generationsCount)

        val genes = result.bestPhenotype.genotype.getByteValues()
        return this.getGrammarSentence(genes)
    }

    // TODO: is 5,9 correct?
    private fun createInitialGenotype(): Genotype<ByteGene> = Genotype.of(ByteChromosome.of(IntRange.of(5,9)))

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
        val currentSentence = mutableListOf<Symbol>()

        var currentIndex = 0
        var wrapsCount = 0
        var firstNonTerminal = currentSentence.find { it.expandable }
        while (firstNonTerminal != null && wrapsCount < MAX_WRAPS_COUNT) {
            val ruleIndex = genes[currentIndex++]
            val ruleCandidates = this.grammar.getRules(firstNonTerminal)
            val ruleToUse = ruleCandidates[ruleIndex % ruleCandidates.size]

            val firstNonTerminalIndex = currentSentence.indexOf(firstNonTerminal)
            currentSentence.removeAt(firstNonTerminalIndex)
            currentSentence.addAll(firstNonTerminalIndex, ruleToUse.to.toList())

            if (currentIndex >= genes.size) {
                currentIndex = 0
                wrapsCount++
            }

            firstNonTerminal = currentSentence.find { it.expandable }
        }

        if (wrapsCount == MAX_WRAPS_COUNT) {
            return emptyArray()
        }

        return currentSentence.toTypedArray()
    }

    companion object {
        private const val MAX_WRAPS_COUNT = 20
    }

}
