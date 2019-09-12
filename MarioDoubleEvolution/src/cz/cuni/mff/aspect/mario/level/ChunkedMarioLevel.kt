package cz.cuni.mff.aspect.mario.level

import cz.cuni.mff.aspect.mario.Tiles

class ChunkedMarioLevel(private val chunks: Array<MarioLevelChunk>) : MarioLevel {

    override val tiles: Array<ByteArray>
    override val enemies: Array<Array<Int>> = emptyArray()

    init {
        val totalWidth = this.chunks.sumBy { it.width }

        var currentColumn = 0
        var currentChunkIndex = 0
        tiles = Array(totalWidth) {
            if (currentColumn == this.chunks[currentChunkIndex].width) {
                currentColumn = 0
                currentChunkIndex++
            }

            val nextColumn = this.chunks[currentChunkIndex].getColumn(currentColumn)
            currentColumn++
            nextColumn
        }
    }

}


abstract class MarioLevelChunk {

    abstract val width: Int
    abstract fun getColumn(index: Int): ByteArray

}

abstract class ArrayMarioLevelChunk(private val columns: Array<ByteArray>) : MarioLevelChunk() {

    override val width: Int = columns.size
    override fun getColumn(index: Int): ByteArray = columns[index]

}

val pathColumn = ByteArray(15) { if (it != 8) Tiles.NOTHING else Tiles.DIRT }

class PathMarioLevelChunk : ArrayMarioLevelChunk(
    arrayOf(pathColumn, pathColumn, pathColumn)
)