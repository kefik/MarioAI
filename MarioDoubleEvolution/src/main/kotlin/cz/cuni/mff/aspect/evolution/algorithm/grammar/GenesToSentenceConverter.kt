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

        var currentIndex = 0
        var wrapsCount = 0
        var firstNonTerminal = currentSentence.find { it.expandable }

        while (firstNonTerminal != null && wrapsCount < maxWrapsCount) {
            val ruleIndex: Int = unsignedGenes[currentIndex++]
            val ruleCandidates = this.grammar.getRules(firstNonTerminal)
            val ruleToUse = ruleCandidates[ruleIndex % ruleCandidates.size]

            val firstNonTerminalIndex = currentSentence.indexOf(firstNonTerminal)
            currentSentence.removeAt(firstNonTerminalIndex)
            currentSentence.addAll(firstNonTerminalIndex, ruleToUse.to.toList())

            if (currentIndex >= unsignedGenes.size) {
                currentIndex = 0
                wrapsCount++
            }

            firstNonTerminal = currentSentence.find { it.expandable }
        }

        if (wrapsCount == maxWrapsCount) {
            return emptyArray()
        }

        return currentSentence.toTypedArray()
    }

}


typealias GrammarSentence = Array<Symbol>