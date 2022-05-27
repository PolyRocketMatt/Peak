package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.math.intPow
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator

/**
 * Operator that computes a polynomial on a buffer with the provided polynomial coefficients.
 */
class PolynomialOperator : UnaryBufferOperator {

    /**
     * Computes a polynomial on a buffer with the provided polynomial coefficients.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * coefficients - the coefficients of the polynomial in the form: ax^n + ... bx^2 + cx^1 + d
     * @return a new NoiseBuffer that contains the evaluated polynomial of the elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer2, vararg data: Float): NoiseBuffer2 = buffer.map { fl -> computePolynomial(fl, data) }

    /**
     * Computes a polynomial on a buffer with the provided polynomial coefficients.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * coefficients - the coefficients of the polynomial in the form: ax^n + ... bx^2 + cx^1 + d
     * @return a new NoiseBuffer that contains the evaluated polynomial of the elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer3, vararg data: Float): NoiseBuffer3 = buffer.map { fl -> computePolynomial(fl, data) }

    private fun computePolynomial(x: Float, coefficients: FloatArray): Float {
        var exponent = coefficients.size - 1
        var result = 0.0f
        var index = 0
        while (exponent != -1)
            result += coefficients[index++] * x.intPow(exponent--)
        return result
    }

}