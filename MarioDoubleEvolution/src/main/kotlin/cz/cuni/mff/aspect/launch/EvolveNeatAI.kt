package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.evolution.controller.NeatControllerEvolution
import cz.cuni.mff.aspect.evolution.fitnessDistanceLeastActions
import cz.cuni.mff.aspect.evolution.levels.TrainingLevelsSet
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.ann.NetworkSettings
import cz.cuni.mff.aspect.mario.level.MarioLevel
import cz.cuni.mff.aspect.mario.level.custom.OnlyPathLevel
import cz.cuni.mff.aspect.mario.level.custom.PathWithHolesLevel
import cz.cuni.mff.aspect.mario.level.original.*
import kotlin.system.exitProcess


fun main() {
    evolveNeatAI()
    exitProcess(0)
}

fun evolveNeatAI() {
    val controllerEvolution: ControllerEvolution = NeatControllerEvolution(NetworkSettings(5, 5, 0, 2),
        1000, "NEAT Evolution Stage 5 Level 1")
    // val levels = emptyArray<MarioLevel>() + Stage5Level1
    val levels = arrayOf<MarioLevel>(Stage5Level1) + PathWithHolesLevel
    val resultController = controllerEvolution.evolve(levels, ::fitnessDistanceLeastActions)

    val marioSimulator = GameSimulator()

    marioSimulator.playMario(resultController, OnlyPathLevel, true)
    marioSimulator.playMario(resultController, PathWithHolesLevel, true)
    marioSimulator.playMario(resultController, Stage5Level1, true)

}
