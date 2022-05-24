package com.github.polyrocketmatt.peak.buffer.operator

import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.ADD
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.BLEND
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.CLIP
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.COMBO_POW
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.DIVIDE
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.INT_POW
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.INVERT
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.LERP
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.MAX
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.MIN
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.MULTIPLY
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.NORMALIZE
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.POLYNOMIAL
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.POWER
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.SCALE
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.SMOOTHER_STEP
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.SMOOTH_STEP
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.SQUARE_ROOT
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.SUBTRACT
import com.github.polyrocketmatt.peak.buffer.operator.unary.*
import com.github.polyrocketmatt.peak.buffer.operator.binary.*

class Operators {

    companion object {
        /**
         * Unary Operators
         */
        val CLIP: ClipOperator = ClipOperator()
        val INT_POW: IntPowerOperator = IntPowerOperator()
        val INVERT: InvertOperator = InvertOperator()
        val LERP: LerpOperator = LerpOperator()
        val NORMALIZE: NormalisationOperator = NormalisationOperator()
        val POLYNOMIAL: PolynomialOperator = PolynomialOperator()
        val POWER: PowerOperator = PowerOperator()
        val SCALE: ScaleOperator = ScaleOperator()
        val SMOOTH_STEP: SmoothStepOperator = SmoothStepOperator()
        val SMOOTHER_STEP: SmootherStepOperator = SmootherStepOperator()
        val SQUARE_ROOT: SquareRootOperator = SquareRootOperator()

        /**
         * Binary Operators
         */
        val ADD: AddOperator = AddOperator()
        val BLEND: BlendOperator = BlendOperator()
        val COMBO_POW: CombinedPowerOperator = CombinedPowerOperator()
        val DIVIDE: DivideOperator = DivideOperator()
        val MAX: MaxOperator = MaxOperator()
        val MIN: MinOperator = MinOperator()
        val MULTIPLY: MultiplyOperator = MultiplyOperator()
        val SUBTRACT: SubtractOperator = SubtractOperator()
    }

}

fun NoiseBuffer.clip(min: Float, max: Float) = CLIP.operate(this, min, max)

fun NoiseBuffer.intPow(pow: Int) = INT_POW.operate(this, pow.f())

fun NoiseBuffer.invert() = INVERT.operate(this)

fun NoiseBuffer.lerp(min: Float, max: Float) = LERP.operate(this, min, max)

fun NoiseBuffer.normalize() = NORMALIZE.operate(this)

fun NoiseBuffer.polynomial(vararg coefficients: Float) = POLYNOMIAL.operate(this, *coefficients)

fun NoiseBuffer.power(pow: Float) = POWER.operate(this, pow)

fun NoiseBuffer.scale(scale: Float) = SCALE.operate(this, scale)

fun NoiseBuffer.smoothStep() = SMOOTH_STEP.operate(this)

fun NoiseBuffer.smootherStep() = SMOOTHER_STEP.operate(this)

fun NoiseBuffer.sqrt() = SQUARE_ROOT.operate(this)

infix fun NoiseBuffer.add(other: NoiseBuffer) = ADD.operate(this, other)

fun NoiseBuffer.blend(other: NoiseBuffer, blend: Float) = BLEND.operate(this, other, blend)

infix fun NoiseBuffer.pow(other: NoiseBuffer) = COMBO_POW.operate(this, other)

infix fun NoiseBuffer.divide(other: NoiseBuffer) = DIVIDE.operate(this, other)

infix fun NoiseBuffer.max(other: NoiseBuffer) = MAX.operate(this, other)

infix fun NoiseBuffer.min(other: NoiseBuffer) = MIN.operate(this, other)

infix fun NoiseBuffer.multiply(other: NoiseBuffer) = MULTIPLY.operate(this, other)

infix fun NoiseBuffer.subtract(other: NoiseBuffer) = SUBTRACT.operate(this, other)