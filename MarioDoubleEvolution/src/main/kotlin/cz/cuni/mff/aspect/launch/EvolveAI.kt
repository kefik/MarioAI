package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.evolution.controller.NeuroControllerEvolution
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.ann.UpdatedAgentNetwork
import cz.cuni.mff.aspect.mario.level.original.Stage1Level1
import kotlin.system.exitProcess


fun main() {
    evolveAI()
    exitProcess(0)
}


fun evolveAI() {
    val controllerANN = UpdatedAgentNetwork(5, 5, 0, 2, 5)
    val controllerEvolution: ControllerEvolution = NeuroControllerEvolution(controllerANN, 200, 50)
    // val level = LevelStorage.loadLevel("ge_first_enemies_2.lvl")
    val level = Stage1Level1.getLevel()
    val resultController = controllerEvolution.evolve(arrayOf(level))

    val marioSimulator = GameSimulator()
    marioSimulator.playMario(resultController, level, true)
}