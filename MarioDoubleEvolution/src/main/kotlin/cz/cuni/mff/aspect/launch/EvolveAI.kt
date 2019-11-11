package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.evolution.controller.NeatControllerEvolution
import cz.cuni.mff.aspect.evolution.controller.NeuroControllerEvolution
import cz.cuni.mff.aspect.evolution.fitnessDistanceLeastActions
import cz.cuni.mff.aspect.evolution.levels.TrainingLevelsSet
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.ann.NetworkSettings
import cz.cuni.mff.aspect.mario.controllers.ann.networks.UpdatedAgentNetwork
import cz.cuni.mff.aspect.mario.level.MarioLevel
import cz.cuni.mff.aspect.mario.level.custom.OnlyPathLevel
import cz.cuni.mff.aspect.mario.level.custom.PathWithHolesLevel
import cz.cuni.mff.aspect.mario.level.original.*
import kotlin.system.exitProcess


fun main() {
    evolveAI()
    exitProcess(0)
}


fun evolveAI() {
    val controllerANN = UpdatedAgentNetwork(5, 5, 0, 2, 20)
    val controllerEvolution: ControllerEvolution = NeuroControllerEvolution(controllerANN, 250  , 50, chartLabel = "Neuroevolution Stage 4 Level 1 split")
    val levels = emptyArray<MarioLevel>() + Stage4Level1Split.levels + PathWithHolesLevel
    val resultController = controllerEvolution.evolve(levels, ::fitnessDistanceLeastActions)

    val marioSimulator = GameSimulator()

    marioSimulator.playMario(resultController, OnlyPathLevel, true)
    marioSimulator.playMario(resultController, Stage4Level1Split.levels[0], true)
    marioSimulator.playMario(resultController, Stage4Level1Split.levels[1], true)
    marioSimulator.playMario(resultController, Stage4Level1Split.levels[2], true)
    marioSimulator.playMario(resultController, Stage4Level1Split.levels[3], true)
    marioSimulator.playMario(resultController, Stage4Level1Split.levels[4], true)
    marioSimulator.playMario(resultController, Stage4Level1Split.levels[5], true)
    marioSimulator.playMario(resultController, Stage4Level1, true)
}
