package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.evolution.algorithm.grammar.GenesToSentenceConverter
import cz.cuni.mff.aspect.evolution.algorithm.grammar.getString
import cz.cuni.mff.aspect.evolution.levels.grammar.GrammarLevelEvolution
import cz.cuni.mff.aspect.evolution.levels.grammar.LevelGrammar
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.EvolvedControllers


fun main() {
    playSentence()
}


fun playSentence() {
    val controller = EvolvedControllers.jumpingSimpleANNController()
    val genes = arrayOf<Byte>(0, 0, 0, 0, 0, 0, 0, 0).toByteArray()
    val levelSentence = GenesToSentenceConverter(LevelGrammar.get()).convert(genes)
    println(levelSentence.getString())
    val level = GrammarLevelEvolution().createLevelFromSentence(levelSentence)
    println(level.pixelWidth)

    val simulator = GameSimulator()
    simulator.playMario(controller, level, true)
    println(simulator.statistics.finalMarioDistance)
}