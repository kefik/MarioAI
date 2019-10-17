package cz.cuni.mff.aspect.launch

import ch.idsia.agents.controllers.keyboard.CheaterKeyboardAgent
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.level.original.*
import kotlin.system.exitProcess


fun main() {
    keyboardPlay()
    exitProcess(0)
}


fun keyboardPlay() {
    val level = Stage4Level1Split.levels[2]
    val agent = CheaterKeyboardAgent()

    val marioSimulator = GameSimulator(15000)
    marioSimulator.playMario(agent, level, true)
}
