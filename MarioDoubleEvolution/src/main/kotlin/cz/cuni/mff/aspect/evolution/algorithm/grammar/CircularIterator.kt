package cz.cuni.mff.aspect.evolution.algorithm.grammar


class CircularIterator<T>(private val data: Array<T>) {

    private var currentIndex = 0
    private var _wrapsCounter = 0

    fun next(): T {
        if (this.currentIndex >= data.size) {
            this.currentIndex = 0
            this._wrapsCounter++
        }

        return this.data[this.currentIndex++]
    }

    val wrapsCount: Int
        get() = this._wrapsCounter
}
