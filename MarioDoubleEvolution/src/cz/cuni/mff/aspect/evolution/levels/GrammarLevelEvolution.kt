package cz.cuni.mff.aspect.evolution.levels

import cz.cuni.mff.aspect.evolution.algorithm.grammar.*
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.MarioAgent
import cz.cuni.mff.aspect.mario.level.ChunkedMarioLevel
import cz.cuni.mff.aspect.mario.level.MarioLevel
import cz.cuni.mff.aspect.mario.level.MarioLevelChunk
import cz.cuni.mff.aspect.mario.level.PathMarioLevelChunk

class GrammarLevelEvolution : LevelEvolution {

    private lateinit var agent: MarioAgent

    override fun evolve(agent: MarioAgent): Array<MarioLevel> {
        this.agent = agent
        val grammarEvolution = GrammarEvolution(LevelGrammar.get(), this::fitness)
        val resultSentence = grammarEvolution.evolve(POPULATION_SIZE, GENERATIONS_COUNT)
        val resultLevel = this.createLevelFromSentence(resultSentence)

        return arrayOf(resultLevel)
    }

    private fun fitness(sentence: GrammarSentence): Float {
        print("Computing fitness for sentence: \n${sentence.getString()}\n")
        if (sentence.isEmpty())
            return 0f

        val level = this.createLevelFromSentence(sentence)
        val marioSimulator = GameSimulator()

        synchronized(Lock) {
            marioSimulator.playMario(this.agent, level, false)
        }

        return marioSimulator.finalDistance
    }

    private fun createLevelFromSentence(sentence: GrammarSentence): MarioLevel {
        val levelChunks = mutableListOf<MarioLevelChunk>()
        for (symbol in sentence) {
            when (symbol) {
                LevelGrammar.path -> levelChunks.add(PathMarioLevelChunk())
                // TODO: better exception
                else -> throw Exception("Unknown terminal in sentence! ${symbol.value}")
            }
        }

        return ChunkedMarioLevel(levelChunks.toTypedArray())
    }

    companion object {
        private const val POPULATION_SIZE = 20
        private const val GENERATIONS_COUNT = 50L
    }

}


object LevelGrammar {

    val level = NonTerminal("LEVEL")
    private val blockSequence = NonTerminal("BLOCK SEQUENCE")
    private  val block = NonTerminal("LEVEL BLOCK")
    val path = Terminal("path")

    private val grammar = Grammar(arrayOf(
            ProductionRule(level, arrayOf(blockSequence)),
            ProductionRule(blockSequence, arrayOf(block)),
            ProductionRule(blockSequence, arrayOf(block, blockSequence)),
            ProductionRule(block, arrayOf(path))
    ), level)

    fun get(): Grammar = grammar

}
