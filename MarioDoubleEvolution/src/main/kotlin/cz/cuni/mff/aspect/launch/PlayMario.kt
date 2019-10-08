package cz.cuni.mff.aspect.launch

import ch.idsia.agents.controllers.keyboard.CheaterKeyboardAgent
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.level.original.Stage2Level1
import kotlin.system.exitProcess


fun main() {
    keyboardPlay()
    exitProcess(0)
}


fun keyboardPlay() {
    val level = Stage2Level1.getLevel()
    val agent = CheaterKeyboardAgent()

    val marioSimulator = GameSimulator(15000)
    marioSimulator.playMario(agent, level, true)
}
