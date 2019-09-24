package cz.cuni.mff.aspect.evolution.levels.grammar

import cz.cuni.mff.aspect.evolution.algorithm.grammar.Terminal
import cz.cuni.mff.aspect.mario.Enemies
import cz.cuni.mff.aspect.mario.level.MarioLevelChunk
import cz.cuni.mff.aspect.mario.level.MonsterSpawn
import cz.cuni.mff.aspect.mario.level.TerminalMarioLevelChunk


// TODO: maybe these could be Kotlin data classes?
abstract class LevelChunkTerminal(value: String) : Terminal(value) {
    abstract fun generateChunk(): MarioLevelChunk
    override fun equals(other: Any?): Boolean = other is LevelChunkTerminal && other.value == this.value
    override fun hashCode(): Int = javaClass.hashCode() * this.value.hashCode()

    abstract val width: Int
}


class NothingChunkTerminal(private var _width: Int = 3) : LevelChunkTerminal("nothing") {

    override fun takeParameters(iterator: Iterator<Int>) {
        this._width = iterator.next() % 4 + 1
    }

    override fun generateChunk(): MarioLevelChunk {
        val emptyColumn = ChunkHelpers.getSpaceColumn()
        return TerminalMarioLevelChunk(this, Array(this._width) { emptyColumn }, emptyArray())
    }

    override val width: Int get() = this._width
    override fun copy(): LevelChunkTerminal = NothingChunkTerminal(this._width)
    override fun toString(): String = "${this.value}(${this._width})"
    override fun equals(other: Any?): Boolean = other is NothingChunkTerminal && other._width == this._width
    override fun hashCode(): Int = javaClass.hashCode() * this._width.hashCode()
}


abstract class MonsterSpawningChunkTerminal(terminalName: String, protected var monsterSpawns: Array<MonsterSpawn>) :
    LevelChunkTerminal(terminalName) {

    abstract fun takeChunkParameters(iterator: Iterator<Int>)

    override fun takeParameters(iterator: Iterator<Int>) {
        this.takeChunkParameters(iterator)

        val monsterCount: Int = iterator.next() % 3
        this.monsterSpawns = Array(monsterCount) {
            val xPos = iterator.next() % this.width
            val yPos = 5
            when (iterator.next() % 2) {
                0 -> MonsterSpawn(xPos, yPos, Enemies.Goomba.NORMAL)
                1 -> MonsterSpawn(xPos, yPos, Enemies.Koopa.GREEN)
                else -> MonsterSpawn(xPos, yPos, Enemies.Goomba.WINGED)
            }
        }

    }

}


class PathChunkTerminal(monsterSpawns: Array<MonsterSpawn> = arrayOf(), private var level: Int = 5, public var _width: Int = 3) :
    MonsterSpawningChunkTerminal("path", monsterSpawns) {

    override fun takeChunkParameters(iterator: Iterator<Int>) {
        this.level = iterator.next() % 10 + 3
        this._width = iterator.next() % 4 + 4
    }

    override fun generateChunk(): MarioLevelChunk {
        val pathColumn = ChunkHelpers.getPathColumn(this.level)
        return TerminalMarioLevelChunk(this, Array(this._width) { pathColumn }, this.monsterSpawns)
    }

    override val width: Int get() = this._width
    override fun copy(): PathChunkTerminal = PathChunkTerminal(this.monsterSpawns, this.level, this._width)
    override fun toString(): String = "${this.value}(${this._width},${this.level})"
    override fun equals(other: Any?): Boolean = other is PathChunkTerminal && other._width == this._width && other.level == this.level
    override fun hashCode(): Int = javaClass.hashCode() * this._width.hashCode() * this.level.hashCode()
}


class StartChunkTerminal(monsterSpawns: Array<MonsterSpawn> = arrayOf(), private var _width: Int = 7) :
    MonsterSpawningChunkTerminal("start", monsterSpawns) {

    override fun takeChunkParameters(iterator: Iterator<Int>) {
        this._width = iterator.next() % 4 + 5
    }

    override fun generateChunk(): MarioLevelChunk {
        val pathColumn = ChunkHelpers.getPathColumn(14)
        return TerminalMarioLevelChunk(this, Array(this._width) { pathColumn }, this.monsterSpawns)
    }

    override val width: Int get() = this._width
    override fun copy(): StartChunkTerminal = StartChunkTerminal(this.monsterSpawns, this._width)
    override fun toString(): String = "${this.value}(${this._width})"
    override fun equals(other: Any?): Boolean = other is StartChunkTerminal && other._width == this._width
    override fun hashCode(): Int = javaClass.hashCode() * this._width.hashCode()
}


class BoxesChunkTerminal(monsterSpawns: Array<MonsterSpawn> = arrayOf(), private var level: Int = 5, private var _width: Int = 5, private var boxesPadding: Int = 2) :
    MonsterSpawningChunkTerminal("boxes", monsterSpawns) {

    override fun takeChunkParameters(iterator: Iterator<Int>) {
        this.level = iterator.next() % 10 + 2
        this._width = iterator.next() % 4 + 5
        this.boxesPadding = iterator.next() % 2 + 1
    }

    override fun generateChunk(): MarioLevelChunk {
        val pathColumn = ChunkHelpers.getPathColumn(this.level)
        val boxesColumn = ChunkHelpers.getBoxesColumn(this.level, this.level - 4)
        return TerminalMarioLevelChunk(this, Array(this._width) {
            if (it in (0 + this.boxesPadding until this._width - this.boxesPadding))
                boxesColumn
            else
                pathColumn
        }, this.monsterSpawns)
    }

    override val width: Int get() = this._width
    override fun copy(): BoxesChunkTerminal = BoxesChunkTerminal(this.monsterSpawns, this.level, this._width, this.boxesPadding)
    override fun toString(): String = "${this.value}(${this._width},${this.level}.${this.boxesPadding})"
    override fun equals(other: Any?): Boolean = other is BoxesChunkTerminal && other._width == this._width && other.level == this.level && this.boxesPadding == boxesPadding
    override fun hashCode(): Int = javaClass.hashCode() * this._width.hashCode() * this.level.hashCode() * this.boxesPadding.hashCode()
}


class SecretsChunkTerminal(monsterSpawns: Array<MonsterSpawn> = arrayOf(), private var level: Int = 5, private var _width: Int = 5, private var secretsPadding: Int = 2) :
    MonsterSpawningChunkTerminal("secrets", monsterSpawns) {

    override fun takeChunkParameters(iterator: Iterator<Int>) {
        this.level = iterator.next() % 10 + 2
        this._width = iterator.next() % 4 + 5
        this.secretsPadding = iterator.next() % 2 + 1
    }

    override fun generateChunk(): MarioLevelChunk {
        val pathColumn = ChunkHelpers.getPathColumn(this.level)
        val secretsColumn = ChunkHelpers.getSecretBoxesColumn(this.level, this.level - 4)
        return TerminalMarioLevelChunk(this, Array(this._width) {
            if (it in (0 + this.secretsPadding until this._width - this.secretsPadding))
                secretsColumn
            else
                pathColumn
        }, this.monsterSpawns)
    }

    override val width: Int get() = this._width
    override fun copy(): SecretsChunkTerminal = SecretsChunkTerminal(this.monsterSpawns, this.level, this._width, this.secretsPadding)
    override fun toString(): String = "${this.value}(${this._width},${this.level}.${this.secretsPadding})"
    override fun equals(other: Any?): Boolean = other is SecretsChunkTerminal && other._width == this._width && other.level == this.level && this.secretsPadding == secretsPadding
    override fun hashCode(): Int = javaClass.hashCode() * this._width.hashCode() * this.level.hashCode() * this.secretsPadding.hashCode()

}

