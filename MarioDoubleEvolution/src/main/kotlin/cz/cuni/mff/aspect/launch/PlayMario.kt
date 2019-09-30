package cz.cuni.mff.aspect.launch

import ch.idsia.agents.controllers.keyboard.CheaterKeyboardAgent
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.storage.LevelStorage


fun main() {
    keyboardPlay()
}


fun keyboardPlay() {
    val level = LevelStorage.loadLevel("current.lvl")
    val agent = CheaterKeyboardAgent()

    val marioSimulator = GameSimulator()
    marioSimulator.playMario(agent, level, true)
}
