package cz.cuni.mff.aspect.mario

import ch.idsia.benchmark.mario.environments.MarioEnvironment
import ch.idsia.benchmark.mario.options.FastOpts
import ch.idsia.benchmark.mario.options.MarioOptions
import cz.cuni.mff.aspect.evolution.generator.EvolutionaryGenerator
import cz.cuni.mff.aspect.mario.controllers.MarioController

open class GameSimulator(private val maxTicks: Int = DEFAULT_MAX_TICKS) {

    private var currentTick: Int = 0
    var finalDistance: Float = 0.0f

    fun playMario(marioController: MarioController, evoGenerator: EvolutionaryGenerator, visualize: Boolean = true) {
        val marioAgent = MarioAgent(marioController)
        val marioLevelGenerator = LevelGenerator(evoGenerator)

        this.playMario(marioAgent, marioLevelGenerator, visualize)
    }

    fun playMario(marioAgent: MarioAgent, levelGenerator: LevelGenerator, visualize: Boolean = true) {
        this.currentTick = 0

        if (visualize) {
            MarioOptions.reset(FastOpts.VIS_ON_2X)
        } else {
            MarioOptions.reset(FastOpts.VIS_OFF)
        }

        val environment = MarioEnvironment(levelGenerator)
        environment.reset(marioAgent)

        while (!environment.isLevelFinished && this.currentTick < this.maxTicks) {
            environment.tick()
            marioAgent.observe(environment)
            val actions = marioAgent.actionSelection()
            environment.performAction(actions)
            marioAgent.receiveReward(environment.intermediateReward.toFloat())
            this.currentTick++
        }

        this.finalDistance = environment.marioSprite.x
    }

    companion object {
        private const val DEFAULT_MAX_TICKS = 2000
    }

}
