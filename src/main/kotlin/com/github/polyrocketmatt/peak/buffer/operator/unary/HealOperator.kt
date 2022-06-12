package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator
import com.github.polyrocketmatt.peak.buffer.operator.normalize
import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.types.NoiseEvaluator

class HealOperator : UnaryBufferOperator {

    override fun operate(buffer: NoiseBuffer2, vararg data: Any): NoiseBuffer2 {
        if (data.size < 2)
            throw BufferOperationException("Incorrect number of arguments provided! Expected heal evaluator, power (and optional heal mask) argument!")
        if (data[0] !is NoiseEvaluator)
            throw BufferOperationException("Expected heal evaluator argument to be of type NoiseEvaluator!")
        if (data[1] !is Float)
            throw BufferOperationException("Expected power argument to be of type NoiseEvaluator!")
        if (data.size == 3 && data[2] !is NoiseBuffer2)
            throw BufferOperationException("Expected heal mask argument to be of type NoiseBuffer2!")
        val evaluator = data[0] as NoiseEvaluator
        val power = data[1] as Float
        val mask = if (data.size == 3) data[2] as NoiseBuffer2 else AsyncNoiseBuffer2(buffer.width(), buffer.height(), buffer.width() / 8, 1.0f)

        val width = buffer.width()
        val height = buffer.height()
        if (width != mask.width() || height != mask.height())
            throw BufferOperationException("Healing requires heal mask to have the same dimensions as the buffer to operate on")

        return buffer.mapIndexed { x, y, fl -> evaluator.noise(x.f(), y.f()) * mask[x][y] * power + fl }.normalize()
    }

    override fun operate(buffer: NoiseBuffer3, vararg data: Any): NoiseBuffer3 {
        TODO("Not yet implemented")
    }
}