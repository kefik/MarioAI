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
            it == boxesLevel -> Tiles.BRICK
            it == level -> Tiles.GRASS_TOP
            it > level -> Tiles.DIRT
            else -> Tiles.NOTHING
        }
    }

    fun getSecretBoxesColumn(level: Int, secretsLevel: Int): ByteArray = ByteArray(15) {
        when {
            it == secretsLevel -> Tiles.QM_WITH_COIN
            it == level -> Tiles.GRASS_TOP
            it > level -> Tiles.DIRT
            else -> Tiles.NOTHING
        }
    }

    fun getBoxesAndSecretsColumn(level: Int, boxesLevel: Int, secretsLevel: Int): ByteArray = ByteArray(15) {
        when {
            it == boxesLevel -> Tiles.BRICK
            it == secretsLevel -> Tiles.QM_WITH_COIN
            it == level -> Tiles.GRASS_TOP
            it > level -> Tiles.DIRT
            else -> Tiles.NOTHING
        }
    }

    fun getDoubleSecretsColumn(level: Int, firstSecretsLevel: Int, secondSecretsLevel: Int): ByteArray = ByteArray(15) {
        when {
            it == firstSecretsLevel -> Tiles.QM_WITH_COIN
            it == secondSecretsLevel -> Tiles.QM_WITH_COIN
            it == level -> Tiles.GRASS_TOP
            it > level -> Tiles.DIRT
            else -> Tiles.NOTHING
        }
    }

    fun getColumnWithBlock(floor: Int, blockLevel: Int, blockType: Byte): ByteArray = ByteArray(15) {
        when {
            it == blockLevel -> blockType
            it == floor -> Tiles.GRASS_TOP
            it > floor -> Tiles.DIRT
            else -> Tiles.NOTHING
        }
    }

    fun getColumnWithTwoBlocks(floor: Int, firstBlockLevel: Int, firstBlockType: Byte, secondBlockLevel: Int, secondBlockType: Byte): ByteArray = ByteArray(15) {
        when {
            it == secondBlockLevel -> secondBlockType
            it == firstBlockLevel -> firstBlockType
            it == floor -> Tiles.GRASS_TOP
            it > floor -> Tiles.DIRT
            else -> Tiles.NOTHING
        }
    }


    fun getPipeStartColumn(level: Int, height: Int): ByteArray = ByteArray(15) {
        when {
            it == level -> Tiles.GRASS_TOP
            it > level -> Tiles.DIRT
            it == level - height -> Tiles.PIPE_TOP_LEFT
            it < level && it > level - height -> Tiles.PIPE_MIDDLE_LEFT
            else -> Tiles.NOTHING
        }
    }

    fun getPipeStartWithBlockColumn(level: Int, height: Int, blockLevel: Int, blockType: Byte): ByteArray = ByteArray(15) {
        when {
            it == blockLevel -> blockType
            it == level -> Tiles.GRASS_TOP
            it > level -> Tiles.DIRT
            it == level - height -> Tiles.PIPE_TOP_LEFT
            it < level && it > level - height -> Tiles.PIPE_MIDDLE_LEFT
            else -> Tiles.NOTHING
        }
    }

    fun getPipeEndColumn(level: Int, height: Int): ByteArray = ByteArray(15) {
        when {
            it == level -> Tiles.GRASS_TOP
            it > level -> Tiles.DIRT
            it == level - height -> Tiles.PIPE_TOP_RIGHT
            it < level && it > level - height -> Tiles.PIPE_MIDDLE_RIGHT
            else -> Tiles.NOTHING
        }
    }

    fun getPipeEndWithBlockColumn(level: Int, height: Int, blockLevel: Int, blockType: Byte): ByteArray = ByteArray(15) {
        when {
            it == blockLevel -> blockType
            it == level -> Tiles.GRASS_TOP
            it > level -> Tiles.DIRT
            it == level - height -> Tiles.PIPE_TOP_RIGHT
            it < level && it > level - height -> Tiles.PIPE_MIDDLE_RIGHT
            else -> Tiles.NOTHING
        }
    }

    fun getStonesColumn(level: Int, height: Int): ByteArray = ByteArray(15) {
        when {
            it == level -> Tiles.GRASS_TOP
            it > level -> Tiles.DIRT
            it < level && it >= level - height -> Tiles.STONE
            else -> Tiles.NOTHING
        }
    }

    fun getPrincessPeachColumn(level: Int): ByteArray = ByteArray(15) {
        when {
            it == level - 1 -> Tiles.PEACH
            it == level -> Tiles.GRASS_TOP
            it > level -> Tiles.DIRT
            else -> Tiles.NOTHING
        }
    }

}