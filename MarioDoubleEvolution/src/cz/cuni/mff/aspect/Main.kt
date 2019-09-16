package cz.cuni.mff.aspect

import cz.cuni.mff.aspect.coevolution.MarioCoEvolver
import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.evolution.controller.NeuroControllerEvolution
import cz.cuni.mff.aspect.evolution.levels.DirectEncodedLevelEvolution
import cz.cuni.mff.aspect.evolution.levels.LevelEvolution
import cz.cuni.mff.aspect.evolution.levels.MockLevelEvolution
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.EvolvedControllers
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
    val controller = EvolvedControllers.jumpingSimpleANNController()
    val levelEvolution = DirectEncodedLevelEvolution()
    val levels = levelEvolution.evolve(controller)

    GameSimulator().playMario(controller, levels.first(), true)
}
