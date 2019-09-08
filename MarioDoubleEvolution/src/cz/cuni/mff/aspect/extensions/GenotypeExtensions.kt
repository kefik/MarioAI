package cz.cuni.mff.aspect.extensions

import io.jenetics.DoubleGene
import io.jenetics.Genotype
import io.jenetics.IntegerGene

fun Genotype<DoubleGene>.getDoubleValues(): List<Double> {
    return this.chromosome.toSeq().toList<DoubleGene>().map { it.allele }
}

fun Genotype<IntegerGene>.getIntValues(): List<Int> {
    return this.chromosome.toSeq().toList<IntegerGene>().map { it.allele }
}