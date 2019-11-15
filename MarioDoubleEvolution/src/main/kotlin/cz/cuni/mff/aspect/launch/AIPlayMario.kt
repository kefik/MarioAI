package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.evolution.levels.TrainingLevelsSet
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.EvolvedControllers
import cz.cuni.mff.aspect.mario.controllers.MarioController
import cz.cuni.mff.aspect.mario.controllers.ann.NetworkSettings
import cz.cuni.mff.aspect.mario.controllers.ann.SimpleANNController
import cz.cuni.mff.aspect.mario.controllers.ann.networks.NeatAgentNetwork
import cz.cuni.mff.aspect.mario.level.original.*
import cz.cuni.mff.aspect.storage.NeatAIStorage
import cz.cuni.mff.aspect.storage.ObjectStorage
import kotlin.system.exitProcess


fun main() {
    aiPlayLevel()
    // neatAiPlayLevel()
    exitProcess(0)
}


fun aiPlayLevel() {
    // val controller = EvolvedControllers.UpdatedNetwork.stage4level1Solver()
    // val level = LevelStorage.loadLevel("ge_long.lvl")
    val controller = ObjectStorage.load("tests/Gaussian test evaluation - Stage 2 Level Split/NeuroEvolution, Mutator 0.45/ai.ai") as MarioController
    val levels = Stage2Level1Split.levels
    //val level = Stage4Level1Split.levels[2]

    val gameSimulator = GameSimulator(1000)
    levels.forEach {
        gameSimulator.playMario(controller, it, true)
    }
}


fun neatAiPlayLevel() {
    val genome = NeatAIStorage.loadAi(NeatAIStorage.STAGE4_LEVEL1)
    val network = NeatAgentNetwork(NetworkSettings(5, 5, 0, 2), genome)
    val controller = SimpleANNController(network)

    val levels = arrayOf(Stage4Level1)
    val simulator = GameSimulator(10000)

    for (level in levels) {
        val stats = simulator.playMario(controller, level, true)
        print(stats.jumps)
    }


}