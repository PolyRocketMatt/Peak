package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator
import com.github.polyrocketmatt.peak.exception.BufferOperationException
import kotlin.math.abs

/**
 * Operator that applies a cross-like blur to the buffer.
 */
class CrossBlur : UnaryBufferOperator {

    /**
     * Cross-blurs a buffer with a provided radius.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * radius - the radius to use for blurring.
     * @throws BufferOperationException if there is no radius value provided
     * @return a new NoiseBuffer that contains the cross-blurred elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer2, vararg data: Any): NoiseBuffer2 {
        if (data.size != 1)
            throw BufferOperationException("Incorrect number of arguments provided! Expected radius arguments!")
        if (data[0] !is Int)
            throw BufferOperationException("Expected min argument to be of type Int!")
        val radius = data[0] as Int

        val radiusSq = radius * radius
        val kernel = Array(radius * 2 + 1) { FloatArray(radius * 2 + 1) }
        for (x in -radius..radius) for (y in -radius..radius) {
            val distSq = x * x + y * y
            if (distSq < radiusSq) {
                val weight = 1.0f - (distSq / radiusSq.f())

                //weightSum += weight
                kernel[x + radius][y + radius] = weight
            }
        }

        val kernelSum = kernel.map { fl -> fl.toList() }.flatten().filter { fl -> fl != 0.0f }.sum()
        val width = buffer.width()
        val height = buffer.height()
        val halfWidth = buffer.width() / 2f
        val halfHeight = buffer.height() / 2f

        return buffer.mapIndexed { x, y, fl -> kotlin.run {
            val midDiffX = abs(x - halfWidth)
            val midDiffY = abs(y - halfHeight)

            if (midDiffX < 10 || midDiffY < 10) {
                //  Compute local kernel influence
                var average = 0.0f
                for (kX in -radius .. radius) for (kY in -radius .. radius) {
                    val sampleX = x + kX
                    val sampleY = y + kY
                    val influence =  kernel[kX + radius][kY + radius]
                    average += if (sampleX in 0 until width && sampleY in 0 until height && influence != 0.0f)
                        buffer[sampleX][sampleY] * influence
                    else
                        0.0f
                }

                average / kernelSum
            } else
                fl
        } }
    }

    /**
     * Cross-blurs a buffer to a provided min and max.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * radius - the radius to use for blurring.
     * @throws BufferOperationException if there is no radius value provided
     * @return a new NoiseBuffer that contains the cross-blurred elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer3, vararg data: Any): NoiseBuffer3 {
        TODO("Not yet implemented")
    }
}