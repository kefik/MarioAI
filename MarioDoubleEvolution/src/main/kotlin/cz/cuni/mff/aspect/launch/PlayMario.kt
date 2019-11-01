package cz.cuni.mff.aspect.launch

import ch.idsia.agents.controllers.keyboard.CheaterKeyboardAgent
import cz.cuni.mff.aspect.evolution.levels.TrainingLevelsSet
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.level.custom.OnlyPathLevel
import cz.cuni.mff.aspect.mario.level.custom.PathWithHolesLevel
import cz.cuni.mff.aspect.mario.level.original.*
import kotlin.system.exitProcess


fun main() {
    keyboardPlay()
    exitProcess(0)
}


fun keyboardPlay() {
    // val levels = Stage2Level1Split.levels.sliceArray(1..1)
    val levels = arrayOf(*Stage4Level1Split.levels)
    val marioSimulator = GameSimulator(15000)

    for (level in levels) {
        val agent = CheaterKeyboardAgent()
        marioSimulator.playMario(agent, level, true)
    }
}
