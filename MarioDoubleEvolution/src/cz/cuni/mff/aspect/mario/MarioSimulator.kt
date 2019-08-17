package cz.cuni.mff.aspect.mario

import ch.idsia.benchmark.mario.environments.MarioEnvironment
import ch.idsia.benchmark.mario.options.FastOpts
import ch.idsia.benchmark.mario.options.MarioOptions
import cz.cuni.mff.aspect.coevolution.agent.EvolutionaryAgent
import cz.cuni.mff.aspect.coevolution.generator.EvolutionaryGenerator

open class MarioSimulator {

    fun playMario(evoAgent: EvolutionaryAgent, evoGenerator: EvolutionaryGenerator) {
        val marioAgent = MarioAgent(evoAgent)
        val marioLevelGenerator = LevelGenerator(evoGenerator)

        this.playMario(marioAgent, marioLevelGenerator)
    }

    fun playMario(marioAgent: MarioAgent, levelGenerator: LevelGenerator) {
        MarioOptions.reset(FastOpts.VIS_ON_2X)
        val environment = MarioEnvironment.getInstance()
        environment.levelScene.levelGenerator = levelGenerator
        environment.reset(marioAgent)

        while (!environment.isLevelFinished) {
            environment.tick()
            marioAgent.observe(environment)
            val actions = marioAgent.actionSelection()
            environment.performAction(actions)
            marioAgent.receiveReward(environment.intermediateReward.toFloat())
        }
    }

}
