package cz.cuni.mff.aspect.mario.level

import cz.cuni.mff.aspect.mario.Tiles

class DirectMarioLevel(private val tiles: Array<ByteArray>, private val enemies: Array<Array<Int>>) : MarioLevel {

    override fun getTiles(): Array<ByteArray> {
        return this.tiles
    }

    override fun getEnemies(): Array<Array<Int>> {
        return this.enemies
    }

    companion object {
        fun createFromTilesArray(width: Int, height: Int, tilesArray: List<Int>): DirectMarioLevel {
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