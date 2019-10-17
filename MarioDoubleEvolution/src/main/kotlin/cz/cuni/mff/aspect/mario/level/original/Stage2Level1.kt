package cz.cuni.mff.aspect.mario.level.original

import cz.cuni.mff.aspect.evolution.levels.grammar.ChunkHelpers
import cz.cuni.mff.aspect.mario.Enemies
import cz.cuni.mff.aspect.mario.Tiles
import cz.cuni.mff.aspect.mario.level.DirectMarioLevel
import cz.cuni.mff.aspect.mario.level.MarioLevel


object Stage2Level1 {

    private const val BOTTOM_LEVEL = 15
    private const val FLOOR_LEVEL =  13
    private const val SECOND_LEVEL = 9
    private const val THIRD_LEVEL = 5
    private const val LEVEL_WIDTH = 212

    private val level: MarioLevel

    init {
        val tiles: Array<ByteArray> = Array(LEVEL_WIDTH) {
            when (it) {
                15, 17 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, SECOND_LEVEL, Tiles.BRICK)
                16 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, SECOND_LEVEL, Tiles.BRICK_WITH_POWERUP)

                20, 21, 22, 23, 24 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, it - 19)

                28 -> ChunkHelpers.getColumnWithTwoBlocks(FLOOR_LEVEL, SECOND_LEVEL, Tiles.BRICK_WITH_COIN, THIRD_LEVEL, Tiles.BRICK) // The coin block should be hidden and the brick should have 1UP
                29, 30, 31 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, THIRD_LEVEL, Tiles.BRICK)

                34 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 4)
                35 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 2)

                46 -> ChunkHelpers.getPipeStartColumn(FLOOR_LEVEL, 4)
                47 -> ChunkHelpers.getPipeEndColumn(FLOOR_LEVEL, 4)

                53 -> ChunkHelpers.getColumnWithTwoBlocks(FLOOR_LEVEL, SECOND_LEVEL, Tiles.QM_WITH_POWERUP, THIRD_LEVEL, Tiles.QM_WITH_COIN)
                54, 55, 56, 57 -> ChunkHelpers.getColumnWithTwoBlocks(FLOOR_LEVEL, SECOND_LEVEL, Tiles.QM_WITH_COIN, THIRD_LEVEL, Tiles.QM_WITH_COIN)

                68 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, SECOND_LEVEL, Tiles.BRICK)
                69 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, THIRD_LEVEL, Tiles.BRICK) // should contain star
                70, 71, 72 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, THIRD_LEVEL, Tiles.BRICK)

                74 -> ChunkHelpers.getPipeStartColumn(FLOOR_LEVEL, 4)
                75 -> ChunkHelpers.getPipeEndColumn(FLOOR_LEVEL, 4)

                79, 80 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, SECOND_LEVEL, Tiles.QM_WITH_COIN)
                81, 82 -> ChunkHelpers.getColumnWithTwoBlocks(FLOOR_LEVEL, SECOND_LEVEL, Tiles.QM_WITH_COIN, THIRD_LEVEL, Tiles.BRICK)
                83 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, THIRD_LEVEL, Tiles.BRICK) // should contain ladder to the clouds
                84 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, THIRD_LEVEL, Tiles.BRICK)
                85 -> ChunkHelpers.getColumnWithTwoBlocks(FLOOR_LEVEL, SECOND_LEVEL, Tiles.QM_WITH_COIN, THIRD_LEVEL, Tiles.BRICK)
                86, 87 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, SECOND_LEVEL, Tiles.QM_WITH_COIN)

                92, 93, 94, 95 -> ChunkHelpers.getColumnWithBlock(BOTTOM_LEVEL, THIRD_LEVEL, Tiles.BRICK)

                103 -> ChunkHelpers.getPipeStartColumn(FLOOR_LEVEL, 4) // should be pipe down
                104 -> ChunkHelpers.getPipeEndColumn(FLOOR_LEVEL, 4)

                106, 107, 108 -> ChunkHelpers.getSpaceColumn()

                115 -> ChunkHelpers.getPipeStartColumn(FLOOR_LEVEL, 2) // should be pipe up
                116 -> ChunkHelpers.getPipeEndColumn(FLOOR_LEVEL, 2)

                122 -> ChunkHelpers.getPipeStartColumn(FLOOR_LEVEL, 4)
                123 -> ChunkHelpers.getPipeEndColumn(FLOOR_LEVEL, 4)

                125 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, THIRD_LEVEL, Tiles.BRICK_WITH_POWERUP)
                126 -> ChunkHelpers.getPipeStartWithBlockColumn(FLOOR_LEVEL, 3, THIRD_LEVEL, Tiles.BRICK)
                127 -> ChunkHelpers.getPipeEndWithBlockColumn(FLOOR_LEVEL, 3, THIRD_LEVEL, Tiles.BRICK)
                128 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, THIRD_LEVEL, Tiles.BRICK)

                130 -> ChunkHelpers.getPipeStartColumn(FLOOR_LEVEL, 5)
                131 -> ChunkHelpers.getPipeEndColumn(FLOOR_LEVEL, 5)

                139, 140, 141 -> ChunkHelpers.getSpaceColumn()

                152, 153 -> ChunkHelpers.getSpaceColumn()
                154 -> ChunkHelpers.getStonesColumn(FLOOR_LEVEL, 3)

                161 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, SECOND_LEVEL, Tiles.BRICK_WITH_COIN)

                164, 165, 166, 167, 168 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, THIRD_LEVEL, Tiles.BRICK)
                170 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, SECOND_LEVEL, Tiles.QM_WITH_COIN)
                172 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, THIRD_LEVEL, Tiles.BRICK_WITH_POWERUP)

                176 -> ChunkHelpers.getPipeStartColumn(FLOOR_LEVEL, 3)
                177 -> ChunkHelpers.getPipeEndColumn(FLOOR_LEVEL, 3)

                185 -> ChunkHelpers.getColumnWithBlock(FLOOR_LEVEL, SECOND_LEVEL, Tiles.BRICK)
                186 -> ChunkHelpers.getColumnWithTwoBlocks(FLOOR_LEVEL, SECOND_LEVEL, Tiles.BRICK, THIRD_LEVEL, Tiles.BRICK_WITH_COIN) // the coin brick should be invisible

                // 188 should be some piston
                // 190 and 191 should be stone wall with height 10

                200 -> ChunkHelpers.getPrincessPeachColumn(FLOOR_LEVEL)

                else -> ChunkHelpers.getPathColumn(FLOOR_LEVEL)
            }
        }

        val enemies: Array<Array<Int>> = Array(LEVEL_WIDTH) { Array(15) { 0 } }
        enemies[24][FLOOR_LEVEL - 6] = Enemies.Goomba.NORMAL

        enemies[32][FLOOR_LEVEL - 1] = Enemies.Koopa.GREEN
        enemies[33][FLOOR_LEVEL - 1] = Enemies.Koopa.GREEN

        enemies[42][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[43][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL

        enemies[46][FLOOR_LEVEL - 4] = Enemies.Flower.NORMAL

        enemies[55][SECOND_LEVEL - 1] = Enemies.Koopa.GREEN

        enemies[59][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[61][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[66][SECOND_LEVEL - 1] = Enemies.Koopa.GREEN
        enemies[68][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[70][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[71][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL

        enemies[74][FLOOR_LEVEL - 4] = Enemies.Flower.NORMAL

        enemies[87][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[89][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[90][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL

        enemies[103][FLOOR_LEVEL - 5] = Enemies.Goomba.NORMAL
        enemies[103][FLOOR_LEVEL - 4] = Enemies.Flower.NORMAL

        enemies[115][FLOOR_LEVEL - 5] = Enemies.Goomba.NORMAL
        enemies[115][FLOOR_LEVEL - 2] = Enemies.Flower.NORMAL
        enemies[120][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[122][FLOOR_LEVEL - 4] = Enemies.Flower.NORMAL
        enemies[130][FLOOR_LEVEL - 5] = Enemies.Flower.NORMAL

        enemies[137][SECOND_LEVEL - 1] = Enemies.Koopa.GREEN

        enemies[151][SECOND_LEVEL - 3] = Enemies.Koopa.GREEN_WINGED

        enemies[162][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL
        enemies[164][FLOOR_LEVEL - 1] = Enemies.Goomba.NORMAL

        enemies[169][FLOOR_LEVEL - 1] = Enemies.Koopa.GREEN_WINGED
        enemies[171][FLOOR_LEVEL - 1] = Enemies.Koopa.GREEN_WINGED

        enemies[176][FLOOR_LEVEL - 3] = Enemies.Flower.NORMAL

        enemies[185][FLOOR_LEVEL - 1] = Enemies.Koopa.GREEN

        this.level = DirectMarioLevel(tiles, enemies)
    }

    fun getLevel(): MarioLevel = this.level

}
