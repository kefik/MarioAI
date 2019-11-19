package cz.cuni.mff.aspect.evolution.utils

import io.jenetics.Alterer
import io.jenetics.Mutator
import io.jenetics.NumericGene
import java.lang.String.format
import io.jenetics.internal.math.base.clamp

import java.util.Random

/**
 * Updated [io.jenetics.GaussianMutator] with added possibility to define standard deviation.
 */
class UpdatedGaussianMutator<G : NumericGene<*, G>, C : Comparable<C>> @JvmOverloads constructor(
    private val standardDeviation: Double,
    probability: Double = Alterer.DEFAULT_ALTER_PROBABILITY
) : Mutator<G, C>(probability) {

    override fun mutate(gene: G, random: Random): G {
        val min = gene.getMin().toDouble()
        val max = gene.getMax().toDouble()
        val std = (max - min) * this.standardDeviation

        val value = gene.doubleValue()
        val gaussian = random.nextGaussian()
        return gene.newInstance(clamp(gaussian * std + value, min, max))
    }

    override fun toString(): String {
        return format("%s[p=%f]", javaClass.simpleName, _probability)
    }

}
