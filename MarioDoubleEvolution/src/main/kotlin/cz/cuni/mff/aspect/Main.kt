package cz.cuni.mff.aspect

import cz.cuni.mff.aspect.coevolution.MarioCoEvolver
import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.evolution.controller.NeuroControllerEvolution
import cz.cuni.mff.aspect.evolution.levels.grammar.GrammarLevelEvolution
import cz.cuni.mff.aspect.evolution.levels.LevelEvolution
import cz.cuni.mff.aspect.evolution.levels.mock.MockLevelEvolution
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.EvolvedControllers
import cz.cuni.mff.aspect.storage.LevelStorage
import kotlin.system.exitProcess


fun main() {
    // coevolution()
    playground()
    exitProcess(0)
}


fun coevolution() {
    val controllerEvolution: ControllerEvolution = NeuroControllerEvolution()
    val levelEvolution: LevelEvolution = MockLevelEvolution()

    val evolution = MarioCoEvolver()
    val evolutionResult = evolution.evolve(controllerEvolution, levelEvolution)

    val marioSimulator = GameSimulator()
    marioSimulator.playMario(evolutionResult.controller, evolutionResult.levels.first(), true)
}

fun playground() {
    // doGrammarEvolution()
    loadGrammarEvolution()
}

fun doGrammarEvolution() {
    val controller = EvolvedControllers.jumpingSimpleANNController()
    val levelEvolution = GrammarLevelEvolution()
    val levels = levelEvolution.evolve(controller)
    val firstLevel = levels.first()

    LevelStorage.storeLevel("asd.lvl", firstLevel)

    GameSimulator().playMario(controller, firstLevel, true)
}

fun loadGrammarEvolution() {
    val controller = EvolvedControllers.jumpingSimpleANNController()
    val level = LevelStorage.loadLevel(LevelStorage.FIRST_GRAMMAR_LEVEL)

    GameSimulator().playMario(controller, level, true)
}
