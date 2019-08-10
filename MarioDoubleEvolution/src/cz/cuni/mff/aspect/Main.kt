package cz.cuni.mff.aspect

import ch.idsia.benchmark.mario.environments.MarioEnvironment
import ch.idsia.benchmark.mario.options.FastOpts
import ch.idsia.benchmark.mario.options.MarioOptions
import cz.cuni.mff.aspect.doubleev.DoubleEvolution
import cz.cuni.mff.aspect.doubleev.agent.EvolutionaryAgent
import cz.cuni.mff.aspect.doubleev.agent.MockEvolutionaryAgent
import cz.cuni.mff.aspect.doubleev.generator.EvolutionaryGenerator
import cz.cuni.mff.aspect.doubleev.generator.MockEvolutionaryGenerator
import cz.cuni.mff.aspect.mario.LevelGenerator
import cz.cuni.mff.aspect.mario.MarioAgent
import kotlin.system.exitProcess

fun playMario(evoAgent: EvolutionaryAgent, evoGenerator: EvolutionaryGenerator) {
    val marioAgent = MarioAgent(evoAgent)

    MarioOptions.reset(FastOpts.VIS_ON_2X)
    val environment = MarioEnvironment.getInstance()
    environment.levelScene.levelGenerator = LevelGenerator(evoGenerator)
    environment.reset(marioAgent)

    while (!environment.isLevelFinished) {
        environment.tick()
        marioAgent.observe(environment)
        val actions = marioAgent.actionSelection()
        environment.performAction(actions)
        marioAgent.receiveReward(environment.intermediateReward.toFloat())
    }
}

fun main() {
    val evolution = DoubleEvolution()
    val evoAgent: EvolutionaryAgent = MockEvolutionaryAgent()
    val evoGenerator: EvolutionaryGenerator = MockEvolutionaryGenerator()

    evolution.evolve(evoAgent, evoGenerator)

    playMario(evoAgent, evoGenerator)

    exitProcess(0)
}
