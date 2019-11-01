package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.evolution.controller.NeatControllerEvolution
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
        400)
    val levels = emptyArray<MarioLevel>() + Stage4Level1Split.levels + PathWithHolesLevel + OnlyPathLevel + Stage4Level1
    val resultController = controllerEvolution.evolve(levels)

    val marioSimulator = GameSimulator()

    marioSimulator.playMario(resultController, OnlyPathLevel, true)
    marioSimulator.playMario(resultController, PathWithHolesLevel, true)

    marioSimulator.playMario(resultController, Stage4Level1Split.levels[0], true)
    marioSimulator.playMario(resultController, Stage4Level1Split.levels[1], true)
    marioSimulator.playMario(resultController, Stage4Level1Split.levels[2], true)
    marioSimulator.playMario(resultController, Stage4Level1Split.levels[3], true)
    marioSimulator.playMario(resultController, Stage4Level1Split.levels[4], true)
    marioSimulator.playMario(resultController, Stage4Level1Split.levels[5], true)

    marioSimulator.playMario(resultController, Stage4Level1, true)

}
