package cz.cuni.mff.aspect.mario.level.original

import cz.cuni.mff.aspect.mario.level.MarioLevel
import cz.cuni.mff.aspect.mario.level.splitting.LevelSplit
import cz.cuni.mff.aspect.mario.level.splitting.LevelSplitter

object Stage4Level1Split {

    val levels: Array<MarioLevel>

    init {
        val splits = arrayOf(
            LevelSplit(0, 80),
            LevelSplit(50, 60),
            LevelSplit(80, 60),
            LevelSplit(115, 60),
            LevelSplit(135, 60),
            LevelSplit(162, 60)
        )

        levels = LevelSplitter.splitLevel(Stage4Level1, splits)
    }

}