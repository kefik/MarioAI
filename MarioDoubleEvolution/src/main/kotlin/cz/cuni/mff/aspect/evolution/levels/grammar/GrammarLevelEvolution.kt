package cz.cuni.mff.aspect.evolution.levels.grammar

import cz.cuni.mff.aspect.evolution.algorithm.grammar.*
import cz.cuni.mff.aspect.evolution.levels.LevelEvolution
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.MarioAgent
import cz.cuni.mff.aspect.mario.controllers.MarioController
import cz.cuni.mff.aspect.mario.level.*

class GrammarLevelEvolution : LevelEvolution {

    private lateinit var controller: MarioController

    override fun evolve(controller: MarioController): Array<MarioLevel> {
        this.controller = controller
        val grammarEvolution = GrammarEvolution(LevelGrammar.get(), this::fitness)
        val resultSentence = grammarEvolution.evolve(POPULATION_SIZE, GENERATIONS_COUNT)
        val resultLevel = this.createLevelFromSentence(resultSentence)

        println("BEST SENTENCE: ${resultSentence.getString()}")

        return arrayOf(resultLevel)
    }

    private fun fitness(sentence: GrammarSentence): Float {
        // println("Computing fitness for sentence: (${sentence.size}) ${sentence.getString()}")
        if (sentence.isEmpty())
            return 0f

        val level = this.createLevelFromSentence(sentence)
        val marioSimulator = GameSimulator()
        val agent = MarioAgent(this.controller)

        marioSimulator.playMario(agent, level, false)

        return marioSimulator.finalDistance
    }

    private fun createLevelFromSentence(sentence: GrammarSentence): MarioLevel {
        val levelChunks = mutableListOf<MarioLevelChunk>()
        for (symbol in sentence) {
            when (symbol) {
                LevelGrammar.path -> levelChunks.add(PathMarioLevelChunk())
                LevelGrammar.nothing -> levelChunks.add(EmptyMarioLevelChunk())
                // TODO: better exception
                else -> throw Exception("Unknown terminal in sentence! ${symbol.value}")
            }
        }

        return ChunkedMarioLevel(levelChunks.toTypedArray())
    }

    companion object {
        private const val POPULATION_SIZE: Int = 30
        private const val GENERATIONS_COUNT: Long = 50L
    }

}
