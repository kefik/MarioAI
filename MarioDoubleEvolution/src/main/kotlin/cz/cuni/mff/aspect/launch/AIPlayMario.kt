package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.EvolvedControllers
import cz.cuni.mff.aspect.mario.controllers.ann.NetworkSettings
import cz.cuni.mff.aspect.mario.controllers.ann.SimpleANNController
import cz.cuni.mff.aspect.mario.controllers.ann.networks.NeatAgentNetwork
import cz.cuni.mff.aspect.mario.level.original.Stage1Level1
import cz.cuni.mff.aspect.storage.NeatAIStorage
import kotlin.system.exitProcess


fun main() {
    // aiPlayLevel()
    neatAiPlayLevel()
    exitProcess(0)
}


fun aiPlayLevel() {
    val controller = EvolvedControllers.UpdatedNetwork.avoidingEnemies()
    // val level = LevelStorage.loadLevel("ge_long.lvl")
    val level = Stage1Level1.getLevel()

    GameSimulator(10000).playMario(controller, level, true)
}


fun neatAiPlayLevel() {
    val genome = NeatAIStorage.loadAi(NeatAIStorage.FIRST_NEAT_AI)
    val network = NeatAgentNetwork(NetworkSettings(5, 5, 0, 2, 5), genome)
    val controller = SimpleANNController(network)

    val level = Stage1Level1.getLevel()

    GameSimulator(10000).playMario(controller, level, true)
}