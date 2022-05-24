package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.math.intPow
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator

class PolynomialOperator : UnaryBufferOperator {

    override fun operate(buffer: NoiseBuffer, vararg data: Float): NoiseBuffer {
        buffer.op { fl -> computePolynomial(fl, data) }

        return buffer
    }

    private fun computePolynomial(x: Float, coefficients: FloatArray): Float {
        var exponent = coefficients.size - 1
        var result = 0.0f
        var index = 0
        while (exponent != -1)
            result += coefficients[index++] * x.intPow(exponent--)
        return result
    }

}