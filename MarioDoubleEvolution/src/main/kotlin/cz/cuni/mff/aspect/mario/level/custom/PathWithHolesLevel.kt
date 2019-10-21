package cz.cuni.mff.aspect.mario.level.custom

import cz.cuni.mff.aspect.evolution.levels.grammar.ChunkHelpers
import cz.cuni.mff.aspect.mario.level.MarioLevel

object PathWithHolesLevel : MarioLevel {

    private const val LENGTH = 50
    private val HOLES_AT = arrayOf(
        6, 7,
        12, 13, 14,
        20, 21, 22, 23,
        28, 29,
        32, 33,
        35, 36
    )

    override val tiles: Array<ByteArray> = Array(LENGTH) {
        if (it in HOLES_AT) {
            ChunkHelpers.getSpaceColumn()
        } else {
            ChunkHelpers.getPathColumn(10)
        }
    }

    override val enemies: Array<Array<Int>> = emptyArray()
}