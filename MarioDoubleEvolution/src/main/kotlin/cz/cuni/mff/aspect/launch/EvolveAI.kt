package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.evolution.controller.NeatControllerEvolution
import cz.cuni.mff.aspect.evolution.controller.NeuroControllerEvolution
import cz.cuni.mff.aspect.evolution.levels.TrainingLevelsSet
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.ann.NetworkSettings
import cz.cuni.mff.aspect.mario.controllers.ann.networks.UpdatedAgentNetwork
import cz.cuni.mff.aspect.mario.level.MarioLevel
import cz.cuni.mff.aspect.mario.level.custom.PathWithHolesLevel
import cz.cuni.mff.aspect.mario.level.original.*
import kotlin.system.exitProcess


fun main() {
    evolveAI()
    exitProcess(0)
}


fun evolveAI() {
    val controllerANN = UpdatedAgentNetwork(5, 5, 0, 2, 5)
    val controllerEvolution: ControllerEvolution = NeuroControllerEvolution(controllerANN, 200, 50)
    // val level = LevelStorage.loadLevel("ge_first_enemies_2.lvl")
    //val levels = Stage4Level1Split.levels
    val levels = arrayOf<MarioLevel>(PathWithHolesLevel)
    val resultController = controllerEvolution.evolve(levels)

    val marioSimulator = GameSimulator()

    marioSimulator.playMario(resultController, PathWithHolesLevel, true)
}
