package cz.cuni.mff.aspect

import cz.cuni.mff.aspect.coevolution.MarioCoEvolver
import cz.cuni.mff.aspect.evolution.algorithm.grammar.GenesToSentenceConverter
import cz.cuni.mff.aspect.evolution.algorithm.grammar.getString
import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.evolution.controller.NeuroControllerEvolution
import cz.cuni.mff.aspect.evolution.levels.grammar.GrammarLevelEvolution
import cz.cuni.mff.aspect.evolution.levels.LevelEvolution
import cz.cuni.mff.aspect.evolution.levels.grammar.LevelGrammar
import cz.cuni.mff.aspect.evolution.levels.mock.MockLevelEvolution
import cz.cuni.mff.aspect.grammar.RandomGrammarSentenceGenerator
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
    //doGrammarEvolution()
    //loadLevel()
    //generateSentences()
    //playSentence()
    evolveAI()
}

fun doGrammarEvolution() {
    val controller = EvolvedControllers.jumpingSimpleANNController()
    val levelEvolution = GrammarLevelEvolution()
    val levels = levelEvolution.evolve(controller)
    val firstLevel = levels.first()

    LevelStorage.storeLevel("current.lvl", firstLevel)

    GameSimulator().playMario(controller, firstLevel, true)
}

fun loadLevel() {
    val controller = EvolvedControllers.jumpingSimpleANNController()
    val level = LevelStorage.loadLevel("ge_first_enemies.lvl")

    GameSimulator().playMario(controller, level, true)
}

fun generateSentences() {
    val controller = EvolvedControllers.jumpingSimpleANNController()
    val levelSentence = RandomGrammarSentenceGenerator(LevelGrammar.get()).generate()
    val level = GrammarLevelEvolution().createLevelFromSentence(levelSentence)

    LevelStorage.storeLevel("randomsentence3.lvl", level)

    GameSimulator().playMario(controller, level, true)
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
    println(simulator.finalDistance)
}

fun evolveAI() {
    val controllerEvolution: ControllerEvolution = NeuroControllerEvolution()
    val level = LevelStorage.loadLevel("ge_first_enemies.lvl")
    val resultController = controllerEvolution.evolve(arrayOf(level)

    val marioSimulator = GameSimulator()
    marioSimulator.playMario(resultController, level, true)
}