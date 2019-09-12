package cz.cuni.mff.aspect.evolution.algorithm.grammar


open class IncorrectGrammarException(message: String) : Exception(message)


class MissingStartingSymbolRule(startingSymbol: NonTerminal, productionRules: Array<ProductionRule>) :
        IncorrectGrammarException("The grammar has no rule for starting symbol '$startingSymbol'. \n${productionRules.joinToString("\n")}")


class NoRuleForGivenSymbol(symbol: Symbol) :
        Exception("The grammar does not contain a rule for given symbol '${symbol.value}'")


class Grammar(private val productionRules: Array<ProductionRule>, private val startingSymbol: NonTerminal) {

    private val productionMap: Map<NonTerminal, MutableList<ProductionRule>>

    init {
        productionMap = mutableMapOf()

        for (productionRule in this.productionRules) {
            if (!productionMap.containsKey(productionRule.from)) {
                productionMap[productionRule.from] = mutableListOf()
            }
            productionMap[productionRule.from]?.add(productionRule)
        }

        if (productionMap[this.startingSymbol].isNullOrEmpty()) {
            throw MissingStartingSymbolRule(this.startingSymbol, this.productionRules)
        }
    }

    fun getRules(nonTerminal: Symbol): List<ProductionRule> =
        this.productionMap[nonTerminal] ?: throw NoRuleForGivenSymbol(nonTerminal)

}


typealias GrammarSentence = Array<Symbol>
