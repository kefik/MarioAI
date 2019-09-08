package cz.cuni.mff.aspect.mario.level


interface MarioLevel {

    fun getTiles(): Array<ByteArray>

    fun getEnemies(): Array<Array<Int>>

}
