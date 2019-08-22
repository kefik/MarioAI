package cz.cuni.mff.aspect.mario

import ch.idsia.benchmark.mario.environments.MarioEnvironment
import ch.idsia.benchmark.mario.options.FastOpts
import ch.idsia.benchmark.mario.options.MarioOptions
import cz.cuni.mff.aspect.coevolution.agent.EvolutionaryAgent
import cz.cuni.mff.aspect.coevolution.generator.EvolutionaryGenerator

open class MarioSimulator() {

    private var currentTick: Int = 0
    // TODO: make this configurable
    private val maxTicks: Int = 2000
    var finalDistance: Float = 0.0f

    fun playMario(evoAgent: EvolutionaryAgent, evoGenerator: EvolutionaryGenerator, visualize: Boolean = true) {
        val marioAgent = MarioAgent(evoAgent)
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

}
