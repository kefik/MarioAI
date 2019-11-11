package cz.woitee.endlessRunners.evolution.utils

import io.jenetics.Gene
import io.jenetics.Genotype
import io.jenetics.Phenotype
import io.jenetics.engine.Evaluator
import io.jenetics.internal.util.Concurrency
import io.jenetics.util.ISeq
import io.jenetics.util.Seq
import java.util.*
import java.util.concurrent.Executor
import java.util.function.Function
import java.util.stream.Stream


/**
 * A custom implementation of concurrent evaluator (mostly rewriting io.jenetics.engine.ConcurrentEvaluator to Kotlin),
 * which was necessary, for the original has only internal visibility.
 *
 *
 * Additionally we deal with some of the issues when using jenetics. First, we provide a simple option to reevaluate all,
 * even surviving individuals in each generation. Secondly, we can distribute seeds consistently to fitness evaluations,
 * such that we have reproducible results.
 */

class MyConcurrentEvaluator<G : Gene<*, G>, C : Comparable<C>>(
    private val executor: Executor,
    private val function: Function<in Genotype<G>, out C>,
    private val alwaysEvaluate: Boolean = false,
    private val seed: Long? = null
) : Evaluator<G, C> {

    private val random = if (seed != null) Random(seed) else Random()
    private val seedMap = HashMap<Int, Long>()

    /**
     * Evaluate implementation, possibly reevaluating all individuals in a population.
     */
    override fun eval(population: Seq<Phenotype<G, C>>): ISeq<Phenotype<G, C>> {
        seedMap.clear()
        population.forEach {
            val genotypeId = System.identityHashCode(it.genotype)
            seedMap[genotypeId] = random.nextLong()
        }

        // Evaluation happens twice per evolution step in jenetics
        // But we should do the "always evaluation" only once, since it would be wasteful otherwise
        val shouldReevaluateAll = alwaysEvaluate && isEverybodyEvaluated(population)

        return if (shouldReevaluateAll) {
            val populationStream = population.stream()
            // The only way to un-evaluate a phenotype is to create a new one
            // val phenotypes = populationStream.map { pt -> pt.withGeneration(pt.generation); }
            evaluate(populationStream)
        } else {
            val originalEvaluated = population.filter { pt -> pt.isEvaluated }
            val originalNotEvaluated = population.stream().filter { pt -> !pt.isEvaluated }
            val evaluated: Seq<Phenotype<G, C>> = evaluate(originalNotEvaluated)

            evaluated.prepend(originalEvaluated).asISeq()
        }
    }

    private fun evaluate(phenotypes: Stream<Phenotype<G, C>>): ISeq<Phenotype<G, C>> {
        val phenotypeRunnables = phenotypes
            .map { pt -> PhenotypeFitness(pt, function) }
            .collect(ISeq.toISeq())

        val concurrency = Concurrency.with(executor)
        concurrency.execute(phenotypeRunnables)
        concurrency.close()

        return phenotypeRunnables.map { it.phenotype() }
    }

    /**
     * Returns a seed a specific genotype should be evaluated with.
     */
    fun seedForGenotype(genotype: Genotype<G>): Long {
        return seedMap[System.identityHashCode(genotype)]!!
    }

    /**
     * Returns whether all of the individuals are already evaluated in a population.
     */
    private fun isEverybodyEvaluated(population: Iterable<Phenotype<G, C>>): Boolean {
        for (phenotype in population) {
            if (!phenotype.isEvaluated) return false
        }
        return true
    }
}

private class PhenotypeFitness<G : Gene<*, G>, C : Comparable<C>> internal constructor(
    private val _phenotype: Phenotype<G, C>,
    private val function: Function<in Genotype<G>, out C>
) : Runnable {
    private lateinit var fitness: C

    override fun run() {
        fitness = function.apply(_phenotype.genotype)
    }

    internal fun phenotype(): Phenotype<G, C> {
        return _phenotype.withFitness(fitness)
    }

}