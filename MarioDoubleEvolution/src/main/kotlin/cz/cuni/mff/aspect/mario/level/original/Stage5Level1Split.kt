package cz.cuni.mff.aspect.mario.level.original

import cz.cuni.mff.aspect.mario.level.MarioLevel
import cz.cuni.mff.aspect.mario.level.splitting.LevelSplit
import cz.cuni.mff.aspect.mario.level.splitting.LevelSplitter

object Stage5Level1Split {

    val levels: Array<MarioLevel>

    init {
        val splits = arrayOf(
            LevelSplit(0, 60),
            LevelSplit(55, 50),
            LevelSplit(95, 50),
            LevelSplit(138, 56)
        )

        levels = LevelSplitter.splitLevel(Stage5Level1, splits)
    }

}