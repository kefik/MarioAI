package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.evolution.levels.grammar.GrammarLevelEvolution
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.EvolvedControllers
import cz.cuni.mff.aspect.storage.LevelStorage


fun main() {
    grammarEvolution()
}

fun grammarEvolution() {
    val controller = EvolvedControllers.currentBestANNController()
    val levelEvolution = GrammarLevelEvolution()
    val levels = levelEvolution.evolve(controller)
    val firstLevel = levels.first()

    LevelStorage.storeLevel("current.lvl", firstLevel)

    GameSimulator().playMario(controller, firstLevel, true)
}