package cz.cuni.mff.aspect.mario.level

import cz.cuni.mff.aspect.mario.Tiles

// TODO: change enemies to IntArray
class DirectMarioLevel(override val tiles: Array<ByteArray>, override val enemies: Array<Array<Int>>) : MarioLevel {

    companion object {
        fun createFromTilesArray(width: Int, height: Int, tilesArray: IntArray): DirectMarioLevel {
            val tiles = Array(height) { y ->
                ByteArray(width) { x ->
                    val index = x * height + y
                    when (tilesArray[index]) {
                        0 -> Tiles.NOTHING
                        1 -> Tiles.DIRT
                        else -> Tiles.NOTHING
                    }
                }
            }

            return DirectMarioLevel(tiles, emptyArray())
        }
    }
}