package cz.cuni.mff.aspect.extensions

inline fun <T> Iterable<T>.sumByFloat(startValue: Float = 0.0f, selector: (T) -> Float): Float {
    var sum = startValue
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
