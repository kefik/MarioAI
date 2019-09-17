package cz.cuni.mff.aspect.evolution.algorithm.grammar

class ProductionRule(val from: NonTerminal, val to: GrammarSentence) {
    override fun toString(): String = "${this.from.value} -> ${this.to.getString()}"
}


class NonTerminal(value: String) : Symbol(value, true)


class Terminal(value: String) : Symbol(value, false)


open class Symbol(val value: String, val expandable: Boolean) {
    override fun toString(): String = value
}
