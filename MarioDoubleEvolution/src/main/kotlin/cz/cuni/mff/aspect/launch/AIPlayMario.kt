package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.evolution.EvolvedAgents
import cz.cuni.mff.aspect.evolution.levels.TrainingLevelsSet
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.MarioAgent
import cz.cuni.mff.aspect.mario.controllers.MarioController
import cz.cuni.mff.aspect.mario.controllers.ann.NetworkSettings
import cz.cuni.mff.aspect.mario.controllers.ann.SimpleANNController
import cz.cuni.mff.aspect.mario.controllers.ann.networks.NeatAgentNetwork
import cz.cuni.mff.aspect.mario.level.MarioLevel
import cz.cuni.mff.aspect.mario.level.custom.PathWithHolesLevel
import cz.cuni.mff.aspect.mario.level.original.Stage1Level1Split
import cz.cuni.mff.aspect.mario.level.original.Stage2Level1Split
import cz.cuni.mff.aspect.mario.level.original.Stage4Level1
import cz.cuni.mff.aspect.mario.level.original.Stage4Level1Split
import cz.cuni.mff.aspect.storage.NeatAIStorage
import cz.cuni.mff.aspect.storage.ObjectStorage
import kotlin.system.exitProcess


fun main() {
    aiPlayLevel()
    // neatAiPlayLevel()
    exitProcess(0)
}


fun aiPlayLevel() {
    // val agent = EvolvedAgents.ruleBasedAgent
    val agent = MarioAgent(ObjectStorage.load("experiments/All test - multi gaussian/NeuroEvolution, experiment 3_ai.ai") as MarioController)
    // val levels = arrayOf<MarioLevel>(PathWithHolesLevel) + Stage4Level1Split.levels
    val levels = TrainingLevelsSet

    val gameSimulator = GameSimulator(400)
    levels.forEach {
        gameSimulator.playMario(agent, it, true)
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