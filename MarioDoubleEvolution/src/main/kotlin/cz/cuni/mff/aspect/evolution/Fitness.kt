package cz.cuni.mff.aspect.evolution

import cz.cuni.mff.aspect.extensions.sumByFloat
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.GameStatistics
import cz.cuni.mff.aspect.mario.controllers.MarioController
import cz.cuni.mff.aspect.mario.level.MarioLevel


fun fitnessOnlyDistance(controller: MarioController, levels: Array<MarioLevel>): Float {
    val statistics = playAllLevels(controller, levels)
    return statistics.sumByFloat { it.finalMarioDistance }
}


fun fitnessDistanceLeastActions(controller: MarioController, levels: Array<MarioLevel>): Float {
    val statistics = playAllLevels(controller, levels)

    val sumFinalDistances: Float = statistics.sumByFloat { it.finalMarioDistance }
    val sumJumps = statistics.sumBy { it.jumps }
    val sumSpecials = statistics.sumBy { it.specials }
    val levelsFinished = statistics.sumBy { if (it.levelFinished) 1 else 0 }

    return sumFinalDistances * 2 - sumJumps * 7 - sumSpecials * 7 + levelsFinished * 2000.0f
}


fun fitnessOnlyVictories(controller: MarioController, levels: Array<MarioLevel>): Float {
    val statistics = playAllLevels(controller, levels)

    return statistics.sumByFloat { if (it.levelFinished) 1.0f else 0.0f }
}


private fun playAllLevels(controller: MarioController, levels: Array<MarioLevel>): List<GameStatistics> {
    val marioSimulator = GameSimulator()
    val statistics = mutableListOf<GameStatistics>()

    levels.forEachIndexed { index, level ->
        marioSimulator.playMario(controller, level, false)
        statistics[index] = marioSimulator.statistics
    }

    return statistics
}
