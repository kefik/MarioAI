package cz.cuni.mff.aspect.grammar

import cz.cuni.mff.aspect.evolution.algorithm.grammar.CircularIterator
import cz.cuni.mff.aspect.evolution.algorithm.grammar.Grammar
import cz.cuni.mff.aspect.evolution.algorithm.grammar.GrammarSentence
import cz.cuni.mff.aspect.evolution.algorithm.grammar.Symbol
import java.util.*
import kotlin.math.abs

class RandomGrammarSentenceGenerator(private val grammar: Grammar) {

    private val random: Random = Random()

    fun generate(maxLength: Int = 100, seed: Long? = null): GrammarSentence {
        if (seed != null)
            this.random.setSeed(seed)

        val currentSentence = mutableListOf<Symbol>(grammar.startingSymbol)
        var firstNonTerminal: Symbol? = currentSentence.find { it.expandable }
        val parametersIterator = CircularIterator(Array(100) {this.random.nextInt()} )

        while (firstNonTerminal != null && currentSentence.size < maxLength) {
            val applicableRules = grammar.getRules(firstNonTerminal)
            val randomApplicableRule = applicableRules[abs(this.random.nextInt()) % applicableRules.size]
            val rightSideOfRule = randomApplicableRule.to.map { s -> s.copy() }
            rightSideOfRule.forEach { it.takeParameters(parametersIterator) }

            val firstNonTerminalIndex: Int = currentSentence.indexOf(firstNonTerminal)
            currentSentence.removeAt(firstNonTerminalIndex)
            currentSentence.addAll(firstNonTerminalIndex, rightSideOfRule)

            firstNonTerminal = currentSentence.find { it.expandable }
        }

        return currentSentence.toTypedArray()
    }
}
