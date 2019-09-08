package cz.cuni.mff.aspect.mario


interface MarioMap {

    fun getTiles(): Array<ByteArray>

    fun getEnemies(): Array<Array<Int>>

}
