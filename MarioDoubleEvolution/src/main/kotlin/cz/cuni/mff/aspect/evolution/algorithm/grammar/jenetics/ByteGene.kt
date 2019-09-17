package cz.cuni.mff.aspect.evolution.algorithm.grammar.jenetics

import io.jenetics.NumericGene
import io.jenetics.util.ISeq
import io.jenetics.util.IntRange
import io.jenetics.util.MSeq
import org.apache.commons.lang3.RandomUtils
import java.util.*


class ByteGene(private val value: Byte) : NumericGene<Byte, ByteGene> {

    override fun getMin(): Byte {
        return Byte.MIN_VALUE
    }

    override fun newInstance(number: Number?): ByteGene {
        return ByteGene(number?.toByte() ?: Byte.MIN_VALUE)
    }

    override fun newInstance(): ByteGene {
        return ByteGene(randomByte())
    }

    override fun getMax(): Byte {
        return Byte.MAX_VALUE
    }

    override fun getAllele(): Byte {
        return this.value
    }

    companion object {
        private val random = Random()
        private val randomBytes: ByteArray = ByteArray(256)
        private var randomBytesIndex: Int = randomBytes.size

        fun seq(lengthRange: IntRange): ISeq<ByteGene> {
            val length = lengthRange.min + random.nextInt(lengthRange.size())
            val values = RandomUtils.nextBytes(length)
            var current = 0

            return MSeq.ofLength<ByteGene>(length)
                    .fill { ByteGene(values[current++]) }
                    .toISeq()
        }

        fun randomByte(): Byte {
            if (this.randomBytesIndex == this.randomBytes.size) {
                this.randomBytesIndex = 0
                this.random.nextBytes(this.randomBytes)
            }

            return this.randomBytes[this.randomBytesIndex++]
        }
    }

}

