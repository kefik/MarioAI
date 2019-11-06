package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.EvolvedControllers
import cz.cuni.mff.aspect.mario.controllers.ann.NetworkSettings
import cz.cuni.mff.aspect.mario.controllers.ann.SimpleANNController
import cz.cuni.mff.aspect.mario.controllers.ann.networks.NeatAgentNetwork
import cz.cuni.mff.aspect.mario.level.original.*
import cz.cuni.mff.aspect.storage.NeatAIStorage
import kotlin.system.exitProcess


fun main() {
    // aiPlayLevel()
    neatAiPlayLevel()
    exitProcess(0)
}


fun aiPlayLevel() {
    val controller = EvolvedControllers.UpdatedNetwork.test()
    // val level = LevelStorage.loadLevel("ge_long.lvl")
    val level = Stage2Level1
    //val level = Stage4Level1Split.levels[2]

    GameSimulator(10000).playMario(controller, level, true)
}


fun neatAiPlayLevel() {
    val genome = NeatAIStorage.loadAi(NeatAIStorage.STAGE4_LEVEL1_SPLIT)
    val network = NeatAgentNetwork(NetworkSettings(5, 5, 0, 2), genome)
    val controller = SimpleANNController(network)

    val levels = arrayOf(Stage4Level1)
    val simulator = GameSimulator(10000)

    for (level in levels) {
        simulator.playMario(controller, level, true)
    }


}