package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.Vec2
import com.github.polyrocketmatt.game.Vec2f
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator
import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.types.NoiseEvaluator

/**
 * Operator that applies a warp-effect to the buffer.
 */
class WarpOperator : UnaryBufferOperator {

    /**
     * Warps a buffer with a provided warp evaluator, offsets and force.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * evaluator - the noise evaluator used for warping.
     * primary - the offset in the x-direction.
     * secondary - the offset in the y-direction.
     * warp - the warp power.
     * @throws BufferOperationException if there are no evaluator, primary, secondary or warp values provided
     * @throws BufferOperationException if the evaluator argument is not a NoiseEvaluator
     * @throws BufferOperationException if the primary offset argument is not a Vector2f
     * @throws BufferOperationException if the secondary offset argument is not a Vector2f
     * @throws BufferOperationException if the warp argument is not a Float
     * @return a new NoiseBuffer that contains the warped elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer2, vararg data: Any): NoiseBuffer2 {
        if (data.size != 4)
            throw BufferOperationException("Incorrect number of arguments provided! Expected evaluator, primary, secondary offsets and warp argument!")
        if (data[0] !is NoiseEvaluator)
            throw BufferOperationException("Expected evaluator argument to be of type NoiseEvaluator!")
        if (data[1] !is Vec2)
            throw BufferOperationException("Expected primary offset argument to be of type Vector2!")
        if (data[2] !is Vec2)
            throw BufferOperationException("Expected secondary offset argument to be of type Vector2!")
        if (data[3] !is Float)
            throw BufferOperationException("Expected warp argument to be of type Float!")

        val evaluator = data[0] as NoiseEvaluator
        val primaryOffset = data[1] as Vec2
        val secondaryOffset = data[2] as Vec2
        val warp = data[3] as Float

        return buffer.mapIndexed { x, y, _ -> run {
            val q = Vec2f(
                evaluator.noise(x + primaryOffset.x, y + primaryOffset.y),
                evaluator.noise(x + secondaryOffset.x, y + secondaryOffset.y)
            )

            evaluator.noise(q.x * warp + x, q.y * warp + y)
        } }
    }

    /**
     * Warps a buffer with a provided warp evaluator, offsets and force.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * evaluator - the noise evaluator used for warping.
     * primary - the offset in the x-direction.
     * secondary - the offset in the y-direction.
     * warp - the warp power.
     * @throws BufferOperationException if there are no evaluator, primary, secondary or warp values provided
     * @throws BufferOperationException if the evaluator argument is not a NoiseEvaluator
     * @throws BufferOperationException if the primary offset argument is not a Vector2f
     * @throws BufferOperationException if the secondary offset argument is not a Vector2f
     * @throws BufferOperationException if the warp argument is not a Float
     * @return a new NoiseBuffer that contains the warped elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer3, vararg data: Any): NoiseBuffer3 {
        TODO("Not yet implemented")
    }
}