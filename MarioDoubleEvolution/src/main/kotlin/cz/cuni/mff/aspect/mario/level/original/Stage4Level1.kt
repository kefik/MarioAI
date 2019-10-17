package cz.cuni.mff.aspect.mario.level.original

import cz.cuni.mff.aspect.evolution.levels.grammar.ChunkHelpers
import cz.cuni.mff.aspect.mario.Enemies
import cz.cuni.mff.aspect.mario.Tiles
import cz.cuni.mff.aspect.mario.level.MarioLevel


object Stage4Level1 : MarioLevel {

    private const val BOTTOM_LEVEL = 15
    private const val FLOOR_LEVEL =  13
    private const val SECOND_LEVEL = 9
    private const val THIRD_LEVEL = 5
    private const val LEVEL_WIDTH = 230

    override val tiles: Array<ByteArray>
    override val enemies: Array<Array<Int>>

    init {
        this.tiles = Array(LEVEL_WIDTH) {
            when (it) {
                21 -> ChunkHelpers.getPipeStartColumn(FLOOR_LEVEL, 3)
                22 -> ChunkHelpers.getPipeEndColumn(FLOOR_LEVEL, 3)

                25 -> ChunkHelpers.getColumnWithTwoBlocks(FLOOR_LEVEL, SECOND_LEVEL, Tiles.QM_WITH_POWERUP, THIRD_LEVEL, Tiles.QM_WITH_COIN)

                29, 30 -> ChunkHelpers.getSpaceColumn()

                38 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, SECOND_LEVEL, Tiles.COIN)
                39, 40 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, SECOND_LEVEL - 1, Tiles.COIN)
                41 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, SECOND_LEVEL, Tiles.COIN)

                61, 63 -> ChunkHelpers.getColumnWithTwoBlocks(FLOOR_LEVEL, SECOND_LEVEL, Tiles.QM_WITH_COIN, THIRD_LEVEL, Tiles.QM_WITH_COIN)

                75, 76, 77, 78 -> ChunkHelpers.getSpaceColumn()

                87, 88, 89, 90 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, SECOND_LEVEL, Tiles.QM_WITH_COIN)  // the 3rd should have hidden 1UP

                100 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 3)

                102, 103, 104, 105 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, THIRD_LEVEL + 1, Tiles.COIN)

                113 -> ChunkHelpers.getPipeStartColumn(FLOOR_LEVEL, 4)
                114 -> ChunkHelpers.getPipeEndColumn(FLOOR_LEVEL, 4)

                116, 117, 118, 119 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, THIRD_LEVEL + 1, Tiles.COIN)

                129 -> ChunkHelpers.getPipeStartColumn(FLOOR_LEVEL, 4)
                130 -> ChunkHelpers.getPipeEndColumn(FLOOR_LEVEL, 4)

                132, 133, 134, 135 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, THIRD_LEVEL + 1, Tiles.COIN)

                143, 144 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, SECOND_LEVEL, Tiles.QM_WITH_COIN)
                145 -> ChunkHelpers.getColumnWithTwoBlocks(FLOOR_LEVEL, SECOND_LEVEL, Tiles.QM_WITH_POWERUP, THIRD_LEVEL, Tiles.QM_WITH_COIN)
                146, 147 -> ChunkHelpers.getColumnWithTwoBlocks(BOTTOM_LEVEL, SECOND_LEVEL, Tiles.BRICK, THIRD_LEVEL, Tiles.QM_WITH_COIN)
                148 -> ChunkHelpers.getColumnWithTwoBlocks(FLOOR_LEVEL, SECOND_LEVEL, Tiles.QM_WITH_COIN, THIRD_LEVEL, Tiles.QM_WITH_COIN)
                149, 150 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, SECOND_LEVEL, Tiles.QM_WITH_COIN)

                160 -> ChunkHelpers.getPipeStartColumn(FLOOR_LEVEL, 2)
                161 -> ChunkHelpers.getPipeEndColumn(FLOOR_LEVEL, 2)

                171, 172, 173 -> ChunkHelpers.getSpaceColumn()
                177, 178 -> ChunkHelpers.getSpaceColumn()

                186 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 3)

                187, 188 -> ChunkHelpers.getSpaceColumn()

                205 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 1)
                206 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 2)
                207 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 3)
                208 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 4)
                209 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 5)
                210 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 6)
                211 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 7)
                212 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 8)
                213 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 8)

                217 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, SECOND_LEVEL, Tiles.BRICK_WITH_COIN)

                222 -> ChunkHelpers.getPrincessPeachColumn(FLOOR_LEVEL)

                else -> ChunkHelpers.getPathColumn(FLOOR_LEVEL)
            }
        }

        this.enemies = Array(LEVEL_WIDTH) { Array(15) { 0 } }
        enemies[21][FLOOR_LEVEL - 3] = Enemies.Flower.NORMAL
        enemies[28][FLOOR_LEVEL - 1] = Enemies.Spiky.NORMAL
        enemies[111][FLOOR_LEVEL - 1] = Enemies.Spiky.NORMAL
        enemies[113][FLOOR_LEVEL - 4] = Enemies.Flower.NORMAL
        enemies[129][FLOOR_LEVEL - 4] = Enemies.Flower.NORMAL
        enemies[160][FLOOR_LEVEL - 2] = Enemies.Flower.NORMAL
        enemies[191][FLOOR_LEVEL - 1] = Enemies.Spiky.NORMAL
        enemies[193][FLOOR_LEVEL - 1] = Enemies.Spiky.NORMAL
    }

}
