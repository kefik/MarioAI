package cz.cuni.mff.aspect.evolution.levels.grammar

import cz.cuni.mff.aspect.evolution.algorithm.grammar.Terminal
import cz.cuni.mff.aspect.mario.level.ArrayMarioLevelChunk
import cz.cuni.mff.aspect.mario.level.MarioLevelChunk


// TODO: maybe these could be Kotlin data classes?
abstract class LevelChunkTerminal(value: String) : Terminal(value) {
    abstract fun generateChunk(): MarioLevelChunk
    override fun equals(other: Any?): Boolean = other is LevelChunkTerminal && other.value == this.value
    override fun hashCode(): Int = javaClass.hashCode() * this.value.hashCode()
}


class NothingChunkTerminal(private var width: Int = 3) : LevelChunkTerminal("nothing") {

    override fun takeParameters(iterator: Iterator<Int>) {
        this.width = iterator.next() % 4 + 1
    }

    override fun generateChunk(): MarioLevelChunk {
        val emptyColumn = ChunkHelpers.getSpaceColumn()
        return ArrayMarioLevelChunk(Array(this.width) { emptyColumn })
    }

    override fun copy(): LevelChunkTerminal = NothingChunkTerminal(this.width)
    override fun toString(): String = "${this.value}(${this.width})"
    override fun equals(other: Any?): Boolean = other is NothingChunkTerminal && other.width == this.width
    override fun hashCode(): Int = javaClass.hashCode() * this.width.hashCode()
}


class PathChunkTerminal(private var level: Int = 5, private var width: Int = 3) : LevelChunkTerminal("path") {

    override fun takeParameters(iterator: Iterator<Int>) {
        this.level = iterator.next() % 10 + 2
        this.width = iterator.next() % 4 + 4
    }

    override fun generateChunk(): MarioLevelChunk {
        val pathColumn = ChunkHelpers.getPathColumn(this.level)
        return ArrayMarioLevelChunk(arrayOf(pathColumn, pathColumn, pathColumn))
    }

    override fun copy(): PathChunkTerminal = PathChunkTerminal(this.level)
    override fun toString(): String = "${this.value}(${this.width},${this.level})"
    override fun equals(other: Any?): Boolean = other is PathChunkTerminal && other.width == this.width && other.level == this.level
    override fun hashCode(): Int = javaClass.hashCode() * this.width.hashCode() * this.level.hashCode()
}

class StartChunkTerminal(private var width: Int = 7) : LevelChunkTerminal("start") {

    override fun takeParameters(iterator: Iterator<Int>) {
        this.width = iterator.next() % 4 + 5
    }

    override fun generateChunk(): MarioLevelChunk {
        val pathColumn = ChunkHelpers.getPathColumn(11)
        return ArrayMarioLevelChunk(Array(this.width) { pathColumn })
    }

    override fun copy(): StartChunkTerminal = StartChunkTerminal(this.width)
    override fun toString(): String = "${this.value}(${this.width})"
    override fun equals(other: Any?): Boolean = other is StartChunkTerminal && other.width == this.width
    override fun hashCode(): Int = javaClass.hashCode() * this.width.hashCode()
}


class BoxesChunkTerminal(private var level: Int = 5, private var width: Int = 5, private var boxesPadding: Int = 2) : LevelChunkTerminal("boxes") {

    override fun takeParameters(iterator: Iterator<Int>) {
        this.level = iterator.next() % 10 + 2
        this.width = iterator.next() % 4 + 5
        this.boxesPadding = iterator.next() % 2 + 1
    }

    override fun generateChunk(): MarioLevelChunk {
        val pathColumn = ChunkHelpers.getPathColumn(this.level)
        val boxesColumn = ChunkHelpers.getBoxesColumn(this.level, this.level - 4)
        return ArrayMarioLevelChunk(Array(this.width) {
            if (it in (0 + this.boxesPadding until this.width - this.boxesPadding))
                boxesColumn
            else
                pathColumn
        })
    }

    override fun copy(): BoxesChunkTerminal = BoxesChunkTerminal(this.level, this.width, this.boxesPadding)
    override fun toString(): String = "${this.value}(${this.width},${this.level}.${this.boxesPadding})"
    override fun equals(other: Any?): Boolean = other is BoxesChunkTerminal && other.width == this.width && other.level == this.level && this.boxesPadding == boxesPadding
    override fun hashCode(): Int = javaClass.hashCode() * this.width.hashCode() * this.level.hashCode() * this.boxesPadding.hashCode()
}

class SecretsChunkTerminal(private var level: Int = 5, private var width: Int = 5, private var secretsPadding: Int = 2) : LevelChunkTerminal("secrets") {

    override fun takeParameters(iterator: Iterator<Int>) {
        this.level = iterator.next() % 10 + 2
        this.width = iterator.next() % 4 + 5
        this.secretsPadding = iterator.next() % 2 + 1
    }

    override fun generateChunk(): MarioLevelChunk {
        val pathColumn = ChunkHelpers.getPathColumn(this.level)
        val secretsColumn = ChunkHelpers.getSecretBoxesColumn(this.level, this.level - 4)
        return ArrayMarioLevelChunk(Array(this.width) {
            if (it in (0 + this.secretsPadding until this.width - this.secretsPadding))
                secretsColumn
            else
                pathColumn
        })
    }

    override fun copy(): SecretsChunkTerminal = SecretsChunkTerminal(this.level, this.width, this.secretsPadding)
    override fun toString(): String = "${this.value}(${this.width},${this.level}.${this.secretsPadding})"
    override fun equals(other: Any?): Boolean = other is SecretsChunkTerminal && other.width == this.width && other.level == this.level && this.secretsPadding == secretsPadding
    override fun hashCode(): Int = javaClass.hashCode() * this.width.hashCode() * this.level.hashCode() * this.secretsPadding.hashCode()

}
