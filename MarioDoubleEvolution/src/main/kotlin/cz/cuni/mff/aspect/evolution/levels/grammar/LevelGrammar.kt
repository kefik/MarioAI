package cz.cuni.mff.aspect.evolution.levels.grammar

import cz.cuni.mff.aspect.evolution.algorithm.grammar.Grammar
import cz.cuni.mff.aspect.evolution.algorithm.grammar.NonTerminal
import cz.cuni.mff.aspect.evolution.algorithm.grammar.ProductionRule


object LevelGrammar {

    val level = NonTerminal("LEVEL")

    private val blockSequence = NonTerminal("BLOCK SEQUENCE")
    private val block = NonTerminal("LEVEL BLOCK")

    private val nothing = NothingChunkTerminal()
    private val start = StartChunkTerminal()
    private val path = PathChunkTerminal()
    private val boxes = BoxesChunkTerminal()
    private val secrets = SecretsChunkTerminal()

    private val grammar = Grammar(arrayOf(
        ProductionRule(level, arrayOf(start, blockSequence)),

        ProductionRule(blockSequence, arrayOf(block)),
        ProductionRule(blockSequence, arrayOf(block, blockSequence)),
        ProductionRule(block, arrayOf(nothing)),
        ProductionRule(block, arrayOf(path)),
        ProductionRule(block, arrayOf(boxes)),
        ProductionRule(block, arrayOf(secrets))
    ), level)

    fun get(): Grammar = grammar

}
