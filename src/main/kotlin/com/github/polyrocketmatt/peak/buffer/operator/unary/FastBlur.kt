package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.game.math.i
import com.github.polyrocketmatt.game.math.sqrt
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator
import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.simple.ValueNoise

/**
 * Operator that applies a fast blur to the buffer.
 */
class FastBlur : UnaryBufferOperator {

    /**
     * Blurs a buffer with a provided radius.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * radius - the radius to use for blurring.
     * @throws BufferOperationException if there is no radius value provided
     * @return a new NoiseBuffer that contains the blurred elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer2, vararg data: Any): NoiseBuffer2 {
        if (data.size != 2)
            throw BufferOperationException("Incorrect number of arguments provided! Expected radius and evaluator argument!")
        val rad = data[0]
        val evaluator = data[1]

        if (rad !is Float)
            throw BufferOperationException("Expected radius argument to be of type float!")
        if (evaluator !is NoiseEvaluator)
            throw BufferOperationException("Expected evaluator argument to be of type NoiseEvaluator!")
        val radius = rad.i()

        //  Construct kernel
        val radiusSq = radius * radius
        var weightSum = 0.0f
        val kernel = Array(radius * 2 + 1) { FloatArray(radius * 2 + 1) { 0.0f } }
        for (x in -radius .. radius) for (y in -radius .. radius) {
            val distSq = x * x + y * y
            if (distSq < radiusSq) {
                val weight = 1.0f - (distSq / radiusSq).sqrt()

                weightSum += weight
                kernel[x + radius][y + radius] = weight
            }
        }

        val kernelSize = kernel.size * kernel[0].size
        for (x in -radius .. radius) for (y in -radius .. radius)
            kernel[x + radius][y + radius] /= weightSum

        return buffer.mapIndexed { x, y, _ -> kotlin.run {
            //  Compute local kernel influence
            var average = 0.0f
            for (kX in -radius .. radius) for (kY in -radius .. radius) {
                val sampleX = x + kX
                val sampleY = y + kY
                val influence = kernel[kX + radius][kY + radius]

                average += evaluator.noise(sampleX.f(), sampleY.f()) * influence
            }

            average / kernelSize
        } }
    }

    /**
     * Blurs a buffer with a provided radius.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * radius - the radius to use for blurring.
     * @throws BufferOperationException if there is no radius value provided
     * @return a new NoiseBuffer that contains the blurred elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer3, vararg data: Any): NoiseBuffer3 {
        TODO("Not yet implemented")
    }
}