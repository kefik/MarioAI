package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.EvolvedControllers
import cz.cuni.mff.aspect.storage.LevelStorage


fun main() {
    aiPlayLevel()
}


fun aiPlayLevel() {
    val controller = EvolvedControllers.currentBestANNController()
    val level = LevelStorage.loadLevel("ge_long.lvl")

    GameSimulator().playMario(controller, level, true)
}