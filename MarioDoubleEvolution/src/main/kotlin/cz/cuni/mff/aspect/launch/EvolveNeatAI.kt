package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.evolution.controller.NeatControllerEvolution
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.ann.NetworkSettings
import cz.cuni.mff.aspect.mario.level.original.Stage1Level1
import kotlin.system.exitProcess


fun main() {
    evolveNeatAI()
    exitProcess(0)
}

fun evolveNeatAI() {
    val level = Stage1Level1

    val controllerEvolution: ControllerEvolution = NeatControllerEvolution(NetworkSettings(5, 5, 0, 2, 5))
    val resultController = controllerEvolution.evolve(arrayOf(level))

    val marioSimulator = GameSimulator()
    marioSimulator.playMario(resultController, level, true)
}
