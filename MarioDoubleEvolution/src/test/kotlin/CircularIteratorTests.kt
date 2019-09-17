import cz.cuni.mff.aspect.evolution.algorithm.grammar.CircularIterator
import org.junit.Test
import org.junit.Assert.*

class CircularIteratorTests {

    @Test
    fun `first next() call should return first value`() {
        val testArray = arrayOf(1, 2, 3)
        val circularIterator = CircularIterator(testArray)
        val firstReceivedValue = circularIterator.next()

        assertEquals("The first next() call should return first value in the underlying array", 1, firstReceivedValue)
    }

    @Test
    fun `5th next() calls should return 5th value`() {
        val testArray = arrayOf(1, 2, 3, 4, 5, 6, 7, 8)
        val circularIterator = CircularIterator(testArray)

        circularIterator.next()
        circularIterator.next()
        circularIterator.next()
        circularIterator.next()
        val fifthReceivedValue = circularIterator.next()

        assertEquals("5th next() calls should return 5th value", 5, fifthReceivedValue)
    }

    @Test
    fun `first 3 next() calls should return first 3 values`() {
        val testArray = arrayOf(1, 2, 3, 4, 5, 6, 7, 8)
        val circularIterator = CircularIterator(testArray)

        val firstReceivedValue = circularIterator.next()
        val secondReceivedValue = circularIterator.next()
        val thirdReceivedValue = circularIterator.next()

        assertEquals("1st next() calls should return 1st value", 1, firstReceivedValue)
        assertEquals("2nd next() calls should return 2nd value", 2, secondReceivedValue)
        assertEquals("3rd next() calls should return 3rd value", 3, thirdReceivedValue)
    }

    @Test
    fun `4th next() call on 3 element array should return the first value in the array`() {
        val testArray = arrayOf(1, 2, 3)
        val circularIterator = CircularIterator(testArray)

        circularIterator.next()
        circularIterator.next()
        circularIterator.next()
        val fourthReceivedValue = circularIterator.next()

        assertEquals("Next returned wrong value", 1, fourthReceivedValue)
    }

    @Test
    fun `20th next() call on 3 element array should return the second value in the array`() {
        val testArray = arrayOf(1, 2, 3)
        val circularIterator = CircularIterator(testArray)

        for (x in 1..19)
            circularIterator.next()

        val twentiethReceivedValue = circularIterator.next()

        assertEquals("Next returned wrong value", 2, twentiethReceivedValue)
    }

    @Test
    fun `zero next() calls should make wrapCount zero`() {
        val testArray = arrayOf(1, 2, 3, 4, 5, 6)
        val circularIterator = CircularIterator(testArray)

        assertEquals("Wrong wrap count", 0, circularIterator.wrapsCount)
    }

    @Test
    fun `calling next() less times than there are elements should make wrapCount zero`() {
        val testArray = arrayOf(1, 2, 3, 4, 5, 6)
        val circularIterator = CircularIterator(testArray)

        for (x in 1..4)
            circularIterator.next()

        assertEquals("Wrong wrap count", 0, circularIterator.wrapsCount)
    }

    @Test
    fun `20 next() calls on 3 element array should make wrap count 6`() {
        val testArray = arrayOf(1, 2, 3)
        val circularIterator = CircularIterator(testArray)

        for (x in 1..20)
            circularIterator.next()

        assertEquals("Next returned wrong value", 6, circularIterator.wrapsCount)
    }

    @Test
    fun `21 next() calls on 3 element array should make wrap count 6`() {
        val testArray = arrayOf(1, 2, 3)
        val circularIterator = CircularIterator(testArray)

        for (x in 1..21)
            circularIterator.next()

        assertEquals("Next returned wrong value", 6, circularIterator.wrapsCount)
    }

    @Test
    fun `22 next() calls on 3 element array should make wrap count 7`() {
        val testArray = arrayOf(1, 2, 3)
        val circularIterator = CircularIterator(testArray)

        for (x in 1..22)
            circularIterator.next()

        assertEquals("Next returned wrong value", 7, circularIterator.wrapsCount)
    }
}