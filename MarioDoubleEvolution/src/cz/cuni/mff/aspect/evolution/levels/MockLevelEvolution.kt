package cz.cuni.mff.aspect.evolution.levels

import cz.cuni.mff.aspect.mario.Enemies
import cz.cuni.mff.aspect.mario.MarioAgent
import cz.cuni.mff.aspect.mario.level.MarioLevel
import cz.cuni.mff.aspect.mario.Tiles
import kotlin.random.Random


class MockLevelEvolution : LevelEvolution {

    override fun evolve(agent: MarioAgent): Array<MarioLevel> {
        return arrayOf(MockLevel)
    }

    object MockLevel : MarioLevel {

        private const val WIDTH = 64
        private const val HEIGHT = 15

        private const val FLOOR = 13

        override val tiles: Array<ByteArray>
            get() {
                val flat = Array(WIDTH) {
                    ByteArray(HEIGHT) { y ->
                        if (y >= FLOOR) Tiles.DIRT else Tiles.NOTHING
                    }
                }

                flat[13][FLOOR - 1] = Tiles.PEACH
                flat[2][FLOOR - 5] = Tiles.QUESTION_MARK_BLOCK
                flat[3][FLOOR - 5] = Tiles.QUESTION_MARK_BLOCK_USED

                createHoles(flat)

                return flat
            }

        override val enemies: Array<Array<Int>>
            get() {
                val enemies = Array(WIDTH) { Array(HEIGHT) { Enemies.NOTHING } }

                enemies[6][FLOOR - 6] = Enemies.Goomba.NORMAL
                enemies[7][FLOOR - 6] = Enemies.Goomba.WINGED
                enemies[7][FLOOR - 3] = Enemies.Goomba.WAVE
                enemies[15][FLOOR - 3] = Enemies.Goomba.WAVE
                enemies[10][FLOOR - 3] = Enemies.Koopa.GREEN_WINGED
                enemies[11][FLOOR - 3] = Enemies.Spiky.NORMAL

                return enemies
            }

        private fun createHoles(tiles: Array<ByteArray>) {
            val random = Random(2627)
            val holeLength = 3
            val holesCount = 5
            val holesAt = WIDTH / (holesCount + 1)

            for (i in 0 until holesCount) {
                val holeOffset = random.nextInt(5)
                val currentHoleAt = holesAt * (1 + i)
                for (x in 0 until holeLength) {
                    for (y in FLOOR until HEIGHT) {
                        tiles[currentHoleAt + holeOffset + x][y] = Tiles.NOTHING
                    }
                }
            }
        }

    }
}