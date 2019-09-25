package cz.cuni.mff.aspect.mario.level

import cz.cuni.mff.aspect.evolution.algorithm.grammar.Terminal


class ChunkedMarioLevel(chunks: Array<MarioLevelChunk>) : MarioLevel {

    override val tiles: Array<ByteArray>
    override val enemies: Array<Array<Int>>

    init {
        val totalWidth = chunks.sumBy { it.width }

        var currentColumn = 0
        var currentChunkIndex = 0
        tiles = Array(totalWidth) {
            if (currentColumn == chunks[currentChunkIndex].width) {
                currentColumn = 0
                currentChunkIndex++
            }

            val nextColumn = chunks[currentChunkIndex].getColumn(currentColumn)
            currentColumn++
            nextColumn
        }
    }

    init {
        val totalWidth = chunks.sumBy { it.width }
        enemies = Array(totalWidth) { Array(15) { 0 } }

        var currentChunkStart = 0
        chunks.forEach { chunk ->
            val enemySpawns = chunk.getMonsterSpawns()
            enemySpawns.forEach {
                enemies[currentChunkStart + it.xPos][it.yPos] = it.monsterType
            }

            currentChunkStart += chunk.width
        }
    }

}


abstract class MarioLevelChunk {

    abstract val width: Int
    abstract fun getColumn(index: Int): ByteArray
    abstract fun getMonsterSpawns(): Array<MonsterSpawn>

}

open class ArrayMarioLevelChunk(private val columns: Array<ByteArray>, private val monsterSpawns: Array<MonsterSpawn>) : MarioLevelChunk() {

    override val width: Int = columns.size
    override fun getColumn(index: Int): ByteArray = columns[index]
    override fun getMonsterSpawns(): Array<MonsterSpawn> = monsterSpawns

}


class TerminalMarioLevelChunk(val terminal: Terminal, columns: Array<ByteArray>, monsterSpawns: Array<MonsterSpawn>) :
    ArrayMarioLevelChunk(columns, monsterSpawns)


data class MonsterSpawn(val xPos: Int, val yPos: Int, val monsterType: Int)

