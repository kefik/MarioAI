package cz.cuni.mff.aspect.mario


interface Map {

    fun getTiles(): Array<ByteArray>

    fun getEnemies(): Array<Array<Int>>

}
