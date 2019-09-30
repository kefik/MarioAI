package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.evolution.controller.NeuroControllerEvolution
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.storage.LevelStorage
import kotlin.system.exitProcess


fun main() {
    evolveAI()
    exitProcess(0)
}


fun evolveAI() {
    val controllerEvolution: ControllerEvolution = NeuroControllerEvolution()
    val level = LevelStorage.loadLevel("ge_first_enemies_2.lvl")
    // val level = Stage1Level1
    val resultController = controllerEvolution.evolve(arrayOf(level))

    val marioSimulator = GameSimulator()
    marioSimulator.playMario(resultController, level, true)
}