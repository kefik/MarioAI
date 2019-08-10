package cz.cuni.mff.aspect.doubleev.game

interface MarioMap {

    fun getTiles(): Array<ByteArray>

    fun getEnemies(): Array<Array<Int>>

}

object MockMap : MarioMap {
    private const val WIDTH = 64
    private const val HEIGHT = 15

    private const val FLOOR = 13

    override fun getTiles(): Array<ByteArray> {
        val flat = Array(WIDTH) { x ->
            ByteArray(HEIGHT) { y ->
                if (y >= FLOOR) Tiles.DIRT else Tiles.NOTHING
            }
        }

        flat[13][FLOOR - 1] = Tiles.PEACH
        flat[10][FLOOR - 5] = Tiles.QUESTION_MARK_BLOCK
        flat[11][FLOOR - 5] = Tiles.QUESTION_MARK_BLOCK_USED

        return flat;
    }

    override fun getEnemies(): Array<Array<Int>> {
        val enemies = Array(WIDTH) { Array(HEIGHT) { Enemies.NOTHING } }

        enemies[6][FLOOR - 6] = Enemies.Goomba.NORMAL
        enemies[7][FLOOR - 6] = Enemies.Goomba.WINGED
        enemies[7][FLOOR - 3] = Enemies.Goomba.WAVE
        enemies[15][FLOOR - 3] = Enemies.Goomba.WAVE
        enemies[10][FLOOR - 3] = Enemies.Koopa.GREEN_WINGED
        enemies[11][FLOOR - 3] = Enemies.Spiky.NORMAL

        return enemies
    }

}