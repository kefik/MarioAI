package cz.cuni.mff.aspect.mario.level.original

import cz.cuni.mff.aspect.evolution.levels.grammar.ChunkHelpers
import cz.cuni.mff.aspect.mario.Enemies
import cz.cuni.mff.aspect.mario.Tiles
import cz.cuni.mff.aspect.mario.level.MarioLevel


object Stage5Level1 : MarioLevel {

    private const val BOTTOM_LEVEL = 15
    private const val FLOOR_LEVEL =  13
    private const val SECOND_LEVEL = 9
    private const val THIRD_LEVEL = 5
    private const val LEVEL_WIDTH = 207

    override val tiles: Array<ByteArray>
    override val enemies: Array<Array<Int>>

    init {
        this.tiles = Array(LEVEL_WIDTH) {
            when (it) {
                44 -> ChunkHelpers.getPipeStartColumn(FLOOR_LEVEL, 3)
                45 -> ChunkHelpers.getPipeEndColumn(FLOOR_LEVEL, 3)

                49, 50 -> ChunkHelpers.getSpaceColumn()
                51 -> ChunkHelpers.getPipeStartColumn(FLOOR_LEVEL, 3)
                52 -> ChunkHelpers.getPipeEndColumn(FLOOR_LEVEL, 3)

                89 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 4)
                90, 91 -> ChunkHelpers.getColumnWithTwoBlocks(FLOOR_LEVEL, SECOND_LEVEL, Tiles.STONE, THIRD_LEVEL, Tiles.BRICK) // the second brick should have star
                92 -> ChunkHelpers.getColumnWithTwoBlocks(BOTTOM_LEVEL, SECOND_LEVEL, Tiles.STONE, THIRD_LEVEL, Tiles.BRICK)
                93 -> ChunkHelpers.getColumnWithBlock(BOTTOM_LEVEL, SECOND_LEVEL, Tiles.STONE)
                94, 95 -> ChunkHelpers.getSpaceColumn()

                111 -> ChunkHelpers.getBlasterBulletBillColumn(FLOOR_LEVEL, 2)

                114, 115 -> ChunkHelpers.getSpaceColumn()
                116 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 3)

                147 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 4)

                149, 150 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, SECOND_LEVEL, Tiles.BRICK)

                152, 153, 154 -> ChunkHelpers.getSpaceColumn()

                156 -> ChunkHelpers.getPipeStartWithBlockColumn(FLOOR_LEVEL, 2, SECOND_LEVEL, Tiles.STONE, 4)
                157 -> ChunkHelpers.getPipeEndWithBlockColumn(FLOOR_LEVEL, 2, SECOND_LEVEL, Tiles.STONE, 4)

                159 -> ChunkHelpers.getBlasterBulletBillColumn(FLOOR_LEVEL, 2)

                163 -> ChunkHelpers.getPipeStartColumn(FLOOR_LEVEL, 2)
                164 -> ChunkHelpers.getPipeEndColumn(FLOOR_LEVEL, 2)

                170 -> ChunkHelpers.getBlasterBulletBillColumn(FLOOR_LEVEL, 2)

                182 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 1)
                183 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 2)
                184 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 3)
                185 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 4)
                186 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 5)

                189, 190 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 6, 2)

                199 -> ChunkHelpers.getPrincessPeachColumn(FLOOR_LEVEL)

                else -> ChunkHelpers.getPathColumn(FLOOR_LEVEL)
            }
        }

        this.enemies = Array(LEVEL_WIDTH) { Array(15) { 0 } }
        enemies[16][FLOOR_LEVEL - 1] = Enemies.Koopa.GREEN

        enemies[19][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[20][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[21][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL

        enemies[30][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[31][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[32][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL

        enemies[41][FLOOR_LEVEL - 1] = Enemies.Koopa.GREEN_WINGED
        enemies[43][FLOOR_LEVEL - 1] = Enemies.Koopa.GREEN_WINGED

        enemies[44][FLOOR_LEVEL - 3] = Enemies.Flower.NORMAL

        enemies[51][FLOOR_LEVEL - 3] = Enemies.Flower.NORMAL

        enemies[61][FLOOR_LEVEL - 4] = Enemies.Koopa.GREEN_WINGED

        enemies[65][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[66][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[67][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL

        enemies[76][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[77][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[78][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL

        enemies[87][FLOOR_LEVEL - 2] = Enemies.Koopa.GREEN_WINGED

        enemies[103][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[104][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[105][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL

        enemies[121][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[122][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[123][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL

        enemies[127][FLOOR_LEVEL - 1] = Enemies.Koopa.GREEN

        enemies[135][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[136][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[137][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL

        enemies[144][FLOOR_LEVEL - 1] = Enemies.Koopa.GREEN
        enemies[146][FLOOR_LEVEL - 1] = Enemies.Koopa.GREEN

        enemies[156][FLOOR_LEVEL - 6] = Enemies.Flower.NORMAL

        enemies[163][FLOOR_LEVEL - 2] = Enemies.Flower.NORMAL

        enemies[178][FLOOR_LEVEL - 1] = Enemies.Koopa.GREEN_WINGED
        enemies[182][FLOOR_LEVEL - 5] = Enemies.Koopa.GREEN_WINGED
    }

}
