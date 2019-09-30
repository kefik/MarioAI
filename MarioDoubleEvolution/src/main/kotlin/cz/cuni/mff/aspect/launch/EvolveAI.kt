package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.evolution.controller.NeuroControllerEvolution
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.storage.LevelStorage


fun main() {
    evolveAI()
}


fun evolveAI() {
    val controllerEvolution: ControllerEvolution = NeuroControllerEvolution()
    val level = LevelStorage.loadLevel("current.lvl")
    val resultController = controllerEvolution.evolve(arrayOf(level))

    val marioSimulator = GameSimulator()
    marioSimulator.playMario(resultController, level, true)
}