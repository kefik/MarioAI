package cz.cuni.mff.aspect.mario.level.custom

import cz.cuni.mff.aspect.evolution.levels.grammar.ChunkHelpers
import cz.cuni.mff.aspect.mario.level.MarioLevel

object OnlyPathLevel : MarioLevel {

    private const val LENGTH = 50

    override val tiles: Array<ByteArray> = Array(LENGTH) {
        ChunkHelpers.getPathColumn(10)
    }

    override val enemies: Array<Array<Int>> = emptyArray()
}