package cz.cuni.mff.aspect.evolution

import cz.cuni.mff.aspect.extensions.sumByFloat
import cz.cuni.mff.aspect.mario.GameStatistics


// TODO: rename me
typealias Fitness<F> = (gameStatistics: Array<GameStatistics>) -> F


fun fitnessOnlyDistance(statistics: Array<GameStatistics>): Float {
    return statistics.sumByFloat { it.finalMarioDistance }
}


fun fitnessDistanceLeastActions(statistics: Array<GameStatistics>): Float {
    val sumFinalDistances: Float = statistics.sumByFloat { it.finalMarioDistance }
    val sumJumps = statistics.sumBy { it.jumps }
    val sumSpecials = statistics.sumBy { it.specials }
    val levelsFinished = statistics.sumBy { if (it.levelFinished) 1 else 0 }

    return sumFinalDistances - sumJumps * 40 - sumSpecials * 40  + levelsFinished * 200.0f
}


fun fitnessOnlyVictories(statistics: Array<GameStatistics>): Float {
    return statistics.sumByFloat { if (it.levelFinished) 1.0f else 0.0f } * 1000
}
