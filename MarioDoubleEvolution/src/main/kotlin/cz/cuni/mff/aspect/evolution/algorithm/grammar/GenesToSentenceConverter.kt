package cz.cuni.mff.aspect.evolution.algorithm.grammar

import kotlin.math.abs


class GenesToSentenceConverter(private val grammar: Grammar) {

    fun convert(genes: ByteArray, maxWrapsCount: Int = 100): GrammarSentence {
        val unsignedGenes = genes.map { it.toInt() + abs(Byte.MIN_VALUE.toInt()) }.toTypedArray()
        return this.convertUnsigned(unsignedGenes, maxWrapsCount)
    }

    // TODO: waiting for UByteArray to become not experimental only
    fun convertUnsigned(unsignedGenes: Array<Int>, maxWrapsCount: Int = 100): GrammarSentence {
        val currentSentence = mutableListOf<Symbol>(this.grammar.startingSymbol)

        var firstNonTerminal = currentSentence.find { it.expandable }
        val genesIterator = CircularIterator(unsignedGenes)

        while (firstNonTerminal != null && genesIterator.wrapsCount < maxWrapsCount) {
            val ruleIndex: Int = genesIterator.next()
            val ruleCandidates = this.grammar.getRules(firstNonTerminal)
            val ruleToUse = ruleCandidates[ruleIndex % ruleCandidates.size]

            val firstNonTerminalIndex = currentSentence.indexOf(firstNonTerminal)
            currentSentence.removeAt(firstNonTerminalIndex)
            currentSentence.addAll(firstNonTerminalIndex, ruleToUse.to.toList())

            firstNonTerminal = currentSentence.find { it.expandable }
        }

        if (genesIterator.wrapsCount > 0) println("Wraps count: ${genesIterator.wrapsCount}")
        if (genesIterator.wrapsCount == maxWrapsCount) {
            return emptyArray()
        }

        return currentSentence.toTypedArray()
    }

}


typealias GrammarSentence = Array<Symbol>