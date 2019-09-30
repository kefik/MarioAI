package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.evolution.levels.grammar.GrammarLevelEvolution
import cz.cuni.mff.aspect.evolution.levels.grammar.LevelGrammar
import cz.cuni.mff.aspect.grammar.RandomGrammarSentenceGenerator
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.EvolvedControllers
import cz.cuni.mff.aspect.storage.LevelStorage


fun main() {
    generateSentence()
}


fun generateSentence() {
    val controller = EvolvedControllers.jumpingSimpleANNController()
    val levelSentence = RandomGrammarSentenceGenerator(LevelGrammar.get()).generate()
    val level = GrammarLevelEvolution().createLevelFromSentence(levelSentence)

    LevelStorage.storeLevel("randomsentence3.lvl", level)

    GameSimulator().playMario(controller, level, true)
}