package cz.cuni.mff.aspect.evolution.algorithm.grammar

import java.io.Serializable


class ProductionRule(val from: NonTerminal, val to: GrammarSentence) {
    override fun toString(): String = "${this.from.value} -> ${this.to.getString()}"
}


class NonTerminal(value: String) : Symbol(value, true) {
    override fun copy(): NonTerminal = NonTerminal(value)
}


open class Terminal(value: String) : Symbol(value, false) {
    override fun copy(): Terminal = Terminal(value)
}


open class Symbol(val value: String, val expandable: Boolean): Serializable {
    override fun toString(): String = value
    open fun takeParameters(iterator: Iterator<Int>) { }
    open fun copy(): Symbol = Symbol(value, expandable)
}
