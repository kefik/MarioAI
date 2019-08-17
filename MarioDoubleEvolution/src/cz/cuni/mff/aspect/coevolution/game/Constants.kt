package cz.cuni.mff.aspect.coevolution.game

import ch.idsia.benchmark.mario.engine.sprites.Sprite

/**
 * Constants representing level tiles. These tiles can be found in /MarioAI4J/src/ch/idsia/benchmark/mario/engine/resources/mapsheet.png.
 * The tile number is then computed as column + row * 16 (column and row being 0-based)
 */
object Tiles {

    const val NOTHING: Byte = 0

    const val DIRT: Byte = (1 + 9 * 16).toByte()

    const val GROUND_LEFT: Byte = (0 + 9 * 16).toByte()
    const val GROUND_RIGHT: Byte = (2 + 9 * 16).toByte()

    const val QUESTION_MARK_BLOCK: Byte = (4 + 1 * 16).toByte()
    const val QUESTION_MARK_BLOCK_USED: Byte = (4 + 0 * 16).toByte()

    const val PEACH: Byte = (15 + 15 * 16).toByte()

}

object Enemies {

    const val NOTHING = 0

    object Goomba {
        const val NORMAL = Sprite.KIND_GOOMBA
        const val WINGED = Sprite.KIND_GOOMBA_WINGED
        const val WAVE = Sprite.KIND_WAVE_GOOMBA
    }

    object Koopa {
        const val GREEN = Sprite.KIND_GREEN_KOOPA
        const val GREEN_WINGED = Sprite.KIND_GREEN_KOOPA_WINGED
        const val RED = Sprite.KIND_RED_KOOPA
        const val RED_WINGED = Sprite.KIND_RED_KOOPA_WINGED
    }

    object Spiky {
        const val NORMAL = Sprite.KIND_SPIKY
        const val WINGED = Sprite.KIND_SPIKY_WINGED
    }

}

object LevelTypes {

    const val DEFAULT: Int = 0
    const val CAVE: Int = 1
    const val CASTLE: Int = 2

}