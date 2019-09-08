package cz.cuni.mff.aspect.mario.level

import cz.cuni.mff.aspect.evolution.levels.DirectEncodedEvolutionaryGenerator
import cz.cuni.mff.aspect.mario.Tiles

class DirectMarioLevel(private val tiles: Array<ByteArray>, private val enemies: Array<Array<Int>>) : MarioLevel {

    override fun getTiles(): Array<ByteArray> {
        return this.tiles
    }

    override fun getEnemies(): Array<Array<Int>> {
        return this.enemies
    }

    companion object {
        fun createFromTilesArray(tilesArray: List<Int>): DirectMarioLevel {
            val tiles = Array(DirectEncodedEvolutionaryGenerator.MAP_WIDTH) { x ->
                ByteArray(DirectEncodedEvolutionaryGenerator.MAP_HEIGHT) { y ->
                    val index = x * DirectEncodedEvolutionaryGenerator.MAP_WIDTH + y
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