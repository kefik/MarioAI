package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.evolution.utils.MarioGameplayEvaluators
import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.evolution.controller.NeatControllerEvolution
import cz.cuni.mff.aspect.evolution.levels.TrainingLevelsSet
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.ann.NetworkSettings
import cz.cuni.mff.aspect.mario.level.MarioLevel
import kotlin.system.exitProcess


fun main() {
    evolveNeatAI()
    exitProcess(0)
}

fun evolveNeatAI() {
    val networkSettings = NetworkSettings(5, 5, 0, 2)
    val controllerEvolution: ControllerEvolution = NeatControllerEvolution(
        networkSettings,
        generationsCount = 25,
        populationSize = 100,
        chartName = "NEAT Evolution S1L1")
    val levels = emptyArray<MarioLevel>() + TrainingLevelsSet // + PathWithHolesLevel
    //val levels = arrayOf<MarioLevel>(*TrainingLevelsSet)
    val resultController = controllerEvolution.evolve(levels, MarioGameplayEvaluators::distanceLeastActions, MarioGameplayEvaluators::victoriesOnly)

    val marioSimulator = GameSimulator()

    levels.forEach {
        marioSimulator.playMario(resultController, it, true)
    }
}
