package cz.cuni.mff.aspect.evolution.levels.grammar

import cz.cuni.mff.aspect.mario.Tiles


object ChunkHelpers {

    private val spaceColumn: ByteArray = ByteArray(15) { Tiles.NOTHING }

    fun getSpaceColumn(): ByteArray = this.spaceColumn

    fun getPathColumn(level: Int): ByteArray = ByteArray(15) {
        when {
            it < level -> Tiles.NOTHING
            it > level -> Tiles.DIRT
            else -> Tiles.GRASS_TOP
        }
    }

    fun getBoxesColumn(level: Int, boxesLevel: Int): ByteArray = ByteArray(15) {
        when {
            it == boxesLevel -> Tiles.BLOCK
            it == level -> Tiles.GRASS_TOP
            it > level -> Tiles.DIRT
            else -> Tiles.NOTHING
        }
    }

    fun getSecretBoxesColumn(level: Int, boxesLevel: Int): ByteArray = ByteArray(15) {
        when {
            it == boxesLevel -> Tiles.QUESTION_MARK_BLOCK
            it == level -> Tiles.GRASS_TOP
            it > level -> Tiles.DIRT
            else -> Tiles.NOTHING
        }
    }

}