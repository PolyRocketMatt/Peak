package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.Vec2
import com.github.polyrocketmatt.game.Vec2f
import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator
import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.types.NoiseEvaluator

class WarpOperator : UnaryBufferOperator {

    override fun operate(buffer: NoiseBuffer2, vararg data: Any): NoiseBuffer2 {
        if (data.size != 4)
            throw BufferOperationException("Incorrect number of arguments provided! Expected evaluator, primary secondary and warp argument!")
        if (data[0] !is NoiseEvaluator)
            throw BufferOperationException("Expected exponent argument to be of type NoiseEvaluator!")
        if (data[1] !is Vec2)
            throw BufferOperationException("Expected exponent argument to be of type Vector2!")
        if (data[2] !is Vec2)
            throw BufferOperationException("Expected exponent argument to be of type Vector2!")
        if (data[3] !is Float)
            throw BufferOperationException("Expected exponent argument to be of type Float!")

        val evaluator = data[0] as NoiseEvaluator
        val primaryOffset = data[1] as Vec2
        val secondaryOffset = data[2] as Vec2
        val warp = data[3] as Float

        return buffer.mapIndexed { x, y, fl -> run {
            val q = Vec2f(
                evaluator.noise(x + primaryOffset.x, y + primaryOffset.y),
                evaluator.noise(x + secondaryOffset.x, y + secondaryOffset.y)
            )

            evaluator.noise(q.x * warp + x, q.y * warp + y)
        } }
    }

    override fun operate(buffer: NoiseBuffer3, vararg data: Any): NoiseBuffer3 {
        TODO("Not yet implemented")
    }
}