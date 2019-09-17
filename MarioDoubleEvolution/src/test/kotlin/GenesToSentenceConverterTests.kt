import cz.cuni.mff.aspect.evolution.algorithm.grammar.*
import org.junit.Test
import org.junit.Assert.*


class GenesToSentenceConverterTests {

    @Test
    fun `should expand to block type 1`() {
        this.assertExpansionTo(arrayOf(0, 0, 0), arrayOf(TestGrammar.blockType1))
    }

    @Test
    fun `should expand to block type 1 using mod`() {
        this.assertExpansionTo(arrayOf(10, 0, 0), arrayOf(TestGrammar.blockType1))
    }

    @Test
    fun `should expand to block type 1 using multiple mods`() {
        this.assertExpansionTo(arrayOf(10, 4, 0), arrayOf(TestGrammar.blockType1))
    }

    @Test
    fun `should expand to block type 3`() {
        this.assertExpansionTo(arrayOf(0, 0, 2), arrayOf(TestGrammar.blockType3))
    }

    @Test
    fun `should expand to block type 3 with big numbers`() {
        this.assertExpansionTo(arrayOf(250, 250, 2), arrayOf(TestGrammar.blockType3))
    }

    @Test
    fun `should expand to block type 3 using multiple mods`() {
        this.assertExpansionTo(arrayOf(20, 6, 2), arrayOf(TestGrammar.blockType3))
    }

    @Test
    fun `should expand to 5 multiple type 1 blocks`() {
        this.assertExpansionTo(arrayOf(0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0),
            arrayOf(TestGrammar.blockType1, TestGrammar.blockType1, TestGrammar.blockType1, TestGrammar.blockType1, TestGrammar.blockType1))
    }

    @Test
    fun `should expand to 5 multiple type 1 blocks using mods`() {
        this.assertExpansionTo(arrayOf(101, 5, 0, 51, 5, 63, 10, 49, 15, 20, 55),
            arrayOf(TestGrammar.blockType1, TestGrammar.blockType1, TestGrammar.blockType1, TestGrammar.blockType1, TestGrammar.blockType1))
    }

    @Test
    fun `should expand to 5 multiple types`() {
        this.assertExpansionTo(arrayOf(101, 5, 7, 51, 21, 63, 10, 49, 123, 20, 59),
            arrayOf(TestGrammar.blockType3, TestGrammar.blockType2, TestGrammar.blockType1, TestGrammar.blockType4, TestGrammar.blockType5))
    }

    @Test
    fun `infinite expansion should return empty sentence`() {
        val result = getConverterForTestGrammar().convertUnsigned(arrayOf(1, 1, 1))
        assertTrue(result.isEmpty())
    }

    private fun assertExpansionTo(genes: Array<Int>, expectedResult: GrammarSentence) {
        val converter = getConverterForTestGrammar()
        val result = converter.convertUnsigned(genes)

        assertArrayEquals("Wrong conversion", expectedResult, result)
    }

    private fun getConverterForTestGrammar() = GenesToSentenceConverter(TestGrammar.get())

    object TestGrammar {
        val start = NonTerminal("START")
        private val blockSequence = NonTerminal("BLOCK SEQUENCE")
        private  val block = NonTerminal("BLOCK")
        val blockType1 = Terminal("block 1")
        val blockType2 = Terminal("block 2")
        val blockType3 = Terminal("block 3")
        val blockType4 = Terminal("block 4")
        val blockType5 = Terminal("block 5")

        private val grammar = Grammar(arrayOf(
            ProductionRule(start, arrayOf(blockSequence)),

            ProductionRule(blockSequence, arrayOf(block)),
            ProductionRule(blockSequence, arrayOf(block, blockSequence)),

            ProductionRule(block, arrayOf(blockType1)),
            ProductionRule(block, arrayOf(blockType2)),
            ProductionRule(block, arrayOf(blockType3)),
            ProductionRule(block, arrayOf(blockType4)),
            ProductionRule(block, arrayOf(blockType5))
        ), start)

        fun get(): Grammar = grammar
    }

}