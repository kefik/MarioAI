package cz.cuni.mff.aspect.evolution

import cz.cuni.mff.aspect.extensions.sumByFloat
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.GameStatistics
import cz.cuni.mff.aspect.mario.controllers.MarioController
import cz.cuni.mff.aspect.mario.level.MarioLevel


fun fitnessOnlyDistance(controller: MarioController, levels: Array<MarioLevel>): Float {
    val statistics = playLevels(controller, levels)
    return statistics.sumByFloat { it.finalMarioDistance }
}


fun fitnessDistanceLeastActions(controller: MarioController, levels: Array<MarioLevel>): Float {
    val statistics = playLevels(controller, levels)

    val sumFinalDistances: Float = statistics.sumByFloat { it.finalMarioDistance }
    val sumJumps = statistics.sumBy { it.jumps }
    val sumSpecials = statistics.sumBy { it.specials }
    val levelsFinished = statistics.sumBy { if (it.levelFinished) 1 else 0 }

    return sumFinalDistances * 2 - sumJumps * 5 - sumSpecials * 5 + levelsFinished * 2000.0f
}


fun fitnessOnlyVictories(controller: MarioController, levels: Array<MarioLevel>): Float {
    val statistics = playLevels(controller, levels)

    return statistics.sumByFloat { if (it.levelFinished) 1.0f else 0.0f }
}


// TODO: genetics does not evaluate an individual again, but here we want it
private fun playLevels(controller: MarioController, levels: Array<MarioLevel>, count: Int = 99999): List<GameStatistics> {
    val marioSimulator = GameSimulator()
    val statistics = mutableListOf<GameStatistics>()

    val lastIndex = if (levels.size < count) levels.size else count
    val levelsToPlay = if (count < 1) levels.toList() else levels.toMutableList().shuffled().subList(0, lastIndex)

    levelsToPlay.forEach { level ->
        marioSimulator.playMario(controller, level, false)
        statistics.add(marioSimulator.statistics)
    }

    return statistics
}
