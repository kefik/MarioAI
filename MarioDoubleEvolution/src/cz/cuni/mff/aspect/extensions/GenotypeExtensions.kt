package cz.cuni.mff.aspect.extensions

import cz.cuni.mff.aspect.evolution.algorithm.grammar.jenetics.ByteGene
import io.jenetics.DoubleGene
import io.jenetics.Genotype
import io.jenetics.IntegerGene


// TODO: toDoubleArray
fun Genotype<DoubleGene>.getDoubleValues(): List<Double> {
    return this.chromosome.toSeq().toList<DoubleGene>().map { it.allele }
}

// TODO: toIntArray
fun Genotype<IntegerGene>.getIntValues(): List<Int> {
    return this.chromosome.toSeq().toList<IntegerGene>().map { it.allele }
}

fun Genotype<ByteGene>.getByteValues(): ByteArray {
    return this.chromosome.toSeq().toList<ByteGene>().map { it.allele }.toByteArray()
}