package com.github.polyrocketmatt.peak.buffer.operator

import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.peak.buffer.*
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.ADD
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.ADDITION
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.BLEND
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.CLIP
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.COMBO_POW
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.DIVIDE
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.DIVISION
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.INT_POW
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.INVERT
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.LERP
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.MAX
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.MIN
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.MULTIPLICATION
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.MULTIPLY
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.NORMALIZE
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.POLYNOMIAL
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.POWER
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.PULL
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.PUSH
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.SCALE
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.SMOOTHER_STEP
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.SMOOTH_STEP
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.SQUARE_ROOT
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.SUBTRACT
import com.github.polyrocketmatt.peak.buffer.operator.Operators.Companion.SUBTRACTION
import com.github.polyrocketmatt.peak.buffer.operator.unary.*
import com.github.polyrocketmatt.peak.buffer.operator.binary.*
import com.github.polyrocketmatt.peak.buffer.operator.scalar.ScalarAddOperator
import com.github.polyrocketmatt.peak.buffer.operator.scalar.ScalarDivideOperator
import com.github.polyrocketmatt.peak.buffer.operator.scalar.ScalarMultiplyOperator
import com.github.polyrocketmatt.peak.buffer.operator.scalar.ScalarSubtractOperator

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
        val PULL: PullOperator = PullOperator()
        val PUSH: PushOperator = PushOperator()
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

        /**
         * Scalar Operators
         */
        val ADDITION: ScalarAddOperator = ScalarAddOperator()
        val SUBTRACTION: ScalarSubtractOperator = ScalarSubtractOperator()
        val MULTIPLICATION: ScalarMultiplyOperator = ScalarMultiplyOperator()
        val DIVISION: ScalarDivideOperator = ScalarDivideOperator()
    }

}

fun NoiseBuffer2.clip(min: Float, max: Float) = CLIP.operate(this, min, max)
fun NoiseBuffer2.intPow(pow: Int) = INT_POW.operate(this, pow.f())
fun NoiseBuffer2.invert() = INVERT.operate(this)
fun NoiseBuffer2.lerp(min: Float, max: Float) = LERP.operate(this, min, max)
fun NoiseBuffer2.normalize() = NORMALIZE.operate(this)
fun NoiseBuffer2.polynomial(vararg coefficients: Float) = POLYNOMIAL.operate(this, *coefficients)
fun NoiseBuffer2.power(pow: Float) = POWER.operate(this, pow)
fun NoiseBuffer2.pull(min: Float, max: Float, inc: Float) = PULL.operate(this, min, max, inc)
fun NoiseBuffer2.push(min: Float, max: Float, inc: Float) = PUSH.operate(this, min, max, inc)
fun NoiseBuffer2.scale(scale: Float) = SCALE.operate(this, scale)
fun NoiseBuffer2.smoothStep() = SMOOTH_STEP.operate(this)
fun NoiseBuffer2.smootherStep() = SMOOTHER_STEP.operate(this)
fun NoiseBuffer2.sqrt() = SQUARE_ROOT.operate(this)
infix fun NoiseBuffer2.add(other: NoiseBuffer2) = ADD.operate(this, other)
fun NoiseBuffer2.blend(other: NoiseBuffer2, blend: Float) = BLEND.operate(this, other, blend)
infix fun NoiseBuffer2.pow(other: NoiseBuffer2) = COMBO_POW.operate(this, other)
infix fun NoiseBuffer2.divide(other: NoiseBuffer2) = DIVIDE.operate(this, other)
infix fun NoiseBuffer2.max(other: NoiseBuffer2) = MAX.operate(this, other)
infix fun NoiseBuffer2.min(other: NoiseBuffer2) = MIN.operate(this, other)
infix fun NoiseBuffer2.multiply(other: NoiseBuffer2) = MULTIPLY.operate(this, other)
infix fun NoiseBuffer2.subtract(other: NoiseBuffer2) = SUBTRACT.operate(this, other)
fun NoiseBuffer2.plus(data: Float) = ADDITION.operate(this, data)
fun NoiseBuffer2.minus(data: Float) = SUBTRACTION.operate(this, data)
fun NoiseBuffer2.times(data: Float) = MULTIPLICATION.operate(this, data)
fun NoiseBuffer2.div(data: Float) = DIVISION.operate(this, data)

fun NoiseBuffer3.clip(min: Float, max: Float) = CLIP.operate(this, min, max)
fun NoiseBuffer3.intPow(pow: Int) = INT_POW.operate(this, pow.f())
fun NoiseBuffer3.invert() = INVERT.operate(this)
fun NoiseBuffer3.lerp(min: Float, max: Float) = LERP.operate(this, min, max)
fun NoiseBuffer3.normalize() = NORMALIZE.operate(this)
fun NoiseBuffer3.polynomial(vararg coefficients: Float) = POLYNOMIAL.operate(this, *coefficients)
fun NoiseBuffer3.power(pow: Float) = POWER.operate(this, pow)
fun NoiseBuffer3.pull(min: Float, max: Float, inc: Float) = PULL.operate(this, min, max, inc)
fun NoiseBuffer3.push(min: Float, max: Float, inc: Float) = PUSH.operate(this, min, max, inc)
fun NoiseBuffer3.scale(scale: Float) = SCALE.operate(this, scale)
fun NoiseBuffer3.smoothStep() = SMOOTH_STEP.operate(this)
fun NoiseBuffer3.smootherStep() = SMOOTHER_STEP.operate(this)
fun NoiseBuffer3.sqrt() = SQUARE_ROOT.operate(this)
infix fun NoiseBuffer3.add(other: NoiseBuffer3) = ADD.operate(this, other)
fun NoiseBuffer3.blend(other: NoiseBuffer3, blend: Float) = BLEND.operate(this, other, blend)
infix fun NoiseBuffer3.pow(other: NoiseBuffer3) = COMBO_POW.operate(this, other)
infix fun NoiseBuffer3.divide(other: NoiseBuffer3) = DIVIDE.operate(this, other)
infix fun NoiseBuffer3.max(other: NoiseBuffer3) = MAX.operate(this, other)
infix fun NoiseBuffer3.min(other: NoiseBuffer3) = MIN.operate(this, other)
infix fun NoiseBuffer3.multiply(other: NoiseBuffer3) = MULTIPLY.operate(this, other)
infix fun NoiseBuffer3.subtract(other: NoiseBuffer3) = SUBTRACT.operate(this, other)
fun NoiseBuffer3.plus(data: Float) = ADDITION.operate(this, data)
fun NoiseBuffer3.minus(data: Float) = SUBTRACTION.operate(this, data)
fun NoiseBuffer3.times(data: Float) = MULTIPLICATION.operate(this, data)
fun NoiseBuffer3.div(data: Float) = DIVISION.operate(this, data)