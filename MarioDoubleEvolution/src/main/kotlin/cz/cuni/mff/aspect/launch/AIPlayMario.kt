package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.EvolvedControllers
import cz.cuni.mff.aspect.mario.level.original.Stage1Level1
import cz.cuni.mff.aspect.storage.LevelStorage
import kotlin.system.exitProcess


fun main() {
    aiPlayLevel()
    exitProcess(0)
}


fun aiPlayLevel() {
    val controller = EvolvedControllers.UpdatedNetwork.currentBest()
    // val level = LevelStorage.loadLevel("ge_long.lvl")
    val level = Stage1Level1.getLevel()

    GameSimulator().playMario(controller, level, true)
}