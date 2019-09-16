package cz.cuni.mff.aspect.evolution.levels.grammar

import cz.cuni.mff.aspect.evolution.algorithm.grammar.Grammar
import cz.cuni.mff.aspect.evolution.algorithm.grammar.NonTerminal
import cz.cuni.mff.aspect.evolution.algorithm.grammar.ProductionRule
import cz.cuni.mff.aspect.evolution.algorithm.grammar.Terminal


object LevelGrammar {

    val level = NonTerminal("LEVEL")
    private val blockSequence = NonTerminal("BLOCK SEQUENCE")
    private  val block = NonTerminal("LEVEL BLOCK")
    val path = Terminal("path")
    val nothing = Terminal("nothing")

    private val grammar = Grammar(arrayOf(
        ProductionRule(level, arrayOf(blockSequence)),
        ProductionRule(blockSequence, arrayOf(block)),
        ProductionRule(blockSequence, arrayOf(block, blockSequence)),
        ProductionRule(block, arrayOf(path)),
        ProductionRule(block, arrayOf(nothing))
    ), level)

    fun get(): Grammar = grammar

}
