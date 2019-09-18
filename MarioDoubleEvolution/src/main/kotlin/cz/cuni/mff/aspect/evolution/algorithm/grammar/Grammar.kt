package cz.cuni.mff.aspect.evolution.algorithm.grammar


open class IncorrectGrammarException(message: String) : Exception(message)


class MissingStartingSymbolRule(startingSymbol: NonTerminal, productionRules: Array<ProductionRule>) :
        IncorrectGrammarException("The grammar has no rule for starting symbol '$startingSymbol'. \n${productionRules.joinToString("\n")}")


class NoRuleForGivenSymbol(symbol: Symbol) :
        Exception("The grammar does not contain a rule for given symbol '${symbol.value}'")


class Grammar(private val productionRules: Array<ProductionRule>, val startingSymbol: NonTerminal) {

    private val productionMap: Map<String, MutableList<ProductionRule>>

    init {
        productionMap = mutableMapOf()

        for (productionRule in this.productionRules) {
            if (!productionMap.containsKey(productionRule.from.value)) {
                productionMap[productionRule.from.value] = mutableListOf()
            }
            productionMap[productionRule.from.value]?.add(productionRule)
        }

        if (productionMap[this.startingSymbol.value].isNullOrEmpty()) {
            throw MissingStartingSymbolRule(this.startingSymbol, this.productionRules)
        }
    }

    fun getRules(nonTerminal: Symbol): List<ProductionRule> =
        this.productionMap[nonTerminal.value] ?: throw NoRuleForGivenSymbol(nonTerminal)

}

