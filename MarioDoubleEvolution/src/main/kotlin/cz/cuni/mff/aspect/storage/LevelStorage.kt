package cz.cuni.mff.aspect.storage

import cz.cuni.mff.aspect.mario.level.DirectMarioLevel
import cz.cuni.mff.aspect.mario.level.MarioLevel
import java.io.Serializable


object LevelStorage {

    const val FIRST_GRAMMAR_LEVEL: String = "first.lvl"

    private val storage: ObjectStorage = ObjectStorage
    private const val levelsDirectory = "levels"

    fun storeLevel(filePath: String, level: MarioLevel) {
        val levelRepresentation = LevelRepresentation(level.tiles, level.enemies)
        val fullFilePath = this.getFullFilePath(filePath)
        this.storage.store(fullFilePath, levelRepresentation)
    }

    fun loadLevel(filePath: String): MarioLevel {
        val fullFilePath = this.getFullFilePath(filePath)
        val levelRepresentation = this.storage.load(fullFilePath) as LevelRepresentation
        return DirectMarioLevel(levelRepresentation.tiles, levelRepresentation.enemies)
    }

    private fun getFullFilePath(filePath: String): String = "${this.levelsDirectory}/$filePath"

    data class LevelRepresentation(val tiles: Array<ByteArray>, val enemies: Array<Array<Int>>): Serializable {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as LevelRepresentation

            if (!tiles.contentDeepEquals(other.tiles)) return false
            if (!enemies.contentDeepEquals(other.enemies)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = tiles.contentDeepHashCode()
            result = 31 * result + enemies.contentDeepHashCode()
            return result
        }
    }

}