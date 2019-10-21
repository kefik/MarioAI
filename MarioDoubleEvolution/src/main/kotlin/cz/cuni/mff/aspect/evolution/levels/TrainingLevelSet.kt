package cz.cuni.mff.aspect.evolution.levels

import cz.cuni.mff.aspect.mario.level.MarioLevel
import cz.cuni.mff.aspect.mario.level.custom.OnlyPathLevel
import cz.cuni.mff.aspect.mario.level.custom.PathWithHolesLevel
import cz.cuni.mff.aspect.mario.level.original.Stage1Level1Split
import cz.cuni.mff.aspect.mario.level.original.Stage2Level1Split
import cz.cuni.mff.aspect.mario.level.original.Stage4Level1Split
import cz.cuni.mff.aspect.mario.level.original.Stage5Level1Split

val TrainingLevelsSet: Array<MarioLevel> = arrayOf(OnlyPathLevel, PathWithHolesLevel, *Stage1Level1Split.levels,
    *Stage2Level1Split.levels, *Stage4Level1Split.levels, *Stage5Level1Split.levels)
