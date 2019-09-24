package cz.cuni.mff.aspect.mario.level


interface MarioLevel {

    val tiles: Array<ByteArray>
    val enemies: Array<Array<Int>>
    val pixelWidth: Int
        get() = tiles.size * 16

}
