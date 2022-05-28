package com.github.polyrocketmatt.peak.buffer.operator

import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer3
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

fun SyncNoiseBuffer2.clip(min: Float, max: Float) = CLIP.operate(this, min, max)
fun SyncNoiseBuffer2.intPow(pow: Int) = INT_POW.operate(this, pow.f())
fun SyncNoiseBuffer2.invert() = INVERT.operate(this)
fun SyncNoiseBuffer2.lerp(min: Float, max: Float) = LERP.operate(this, min, max)
fun SyncNoiseBuffer2.normalize() = NORMALIZE.operate(this)
fun SyncNoiseBuffer2.polynomial(vararg coefficients: Float) = POLYNOMIAL.operate(this, *coefficients)
fun SyncNoiseBuffer2.power(pow: Float) = POWER.operate(this, pow)
fun SyncNoiseBuffer2.pull(min: Float, max: Float, inc: Float) = PULL.operate(this, min, max, inc)
fun SyncNoiseBuffer2.push(min: Float, max: Float, inc: Float) = PUSH.operate(this, min, max, inc)
fun SyncNoiseBuffer2.scale(scale: Float) = SCALE.operate(this, scale)
fun SyncNoiseBuffer2.smoothStep() = SMOOTH_STEP.operate(this)
fun SyncNoiseBuffer2.smootherStep() = SMOOTHER_STEP.operate(this)
fun SyncNoiseBuffer2.sqrt() = SQUARE_ROOT.operate(this)
infix fun SyncNoiseBuffer2.add(other: SyncNoiseBuffer2) = ADD.operate(this, other)
fun SyncNoiseBuffer2.blend(other: SyncNoiseBuffer2, blend: Float) = BLEND.operate(this, other, blend)
infix fun SyncNoiseBuffer2.pow(other: SyncNoiseBuffer2) = COMBO_POW.operate(this, other)
infix fun SyncNoiseBuffer2.divide(other: SyncNoiseBuffer2) = DIVIDE.operate(this, other)
infix fun SyncNoiseBuffer2.max(other: SyncNoiseBuffer2) = MAX.operate(this, other)
infix fun SyncNoiseBuffer2.min(other: SyncNoiseBuffer2) = MIN.operate(this, other)
infix fun SyncNoiseBuffer2.multiply(other: SyncNoiseBuffer2) = MULTIPLY.operate(this, other)
infix fun SyncNoiseBuffer2.subtract(other: SyncNoiseBuffer2) = SUBTRACT.operate(this, other)
fun SyncNoiseBuffer2.plus(data: Float) = ADDITION.operate(this, data)
fun SyncNoiseBuffer2.minus(data: Float) = SUBTRACTION.operate(this, data)
fun SyncNoiseBuffer2.times(data: Float) = MULTIPLICATION.operate(this, data)
fun SyncNoiseBuffer2.div(data: Float) = DIVISION.operate(this, data)

fun SyncNoiseBuffer3.clip(min: Float, max: Float) = CLIP.operate(this, min, max)
fun SyncNoiseBuffer3.intPow(pow: Int) = INT_POW.operate(this, pow.f())
fun SyncNoiseBuffer3.invert() = INVERT.operate(this)
fun SyncNoiseBuffer3.lerp(min: Float, max: Float) = LERP.operate(this, min, max)
fun SyncNoiseBuffer3.normalize() = NORMALIZE.operate(this)
fun SyncNoiseBuffer3.polynomial(vararg coefficients: Float) = POLYNOMIAL.operate(this, *coefficients)
fun SyncNoiseBuffer3.power(pow: Float) = POWER.operate(this, pow)
fun SyncNoiseBuffer3.pull(min: Float, max: Float, inc: Float) = PULL.operate(this, min, max, inc)
fun SyncNoiseBuffer3.push(min: Float, max: Float, inc: Float) = PUSH.operate(this, min, max, inc)
fun SyncNoiseBuffer3.scale(scale: Float) = SCALE.operate(this, scale)
fun SyncNoiseBuffer3.smoothStep() = SMOOTH_STEP.operate(this)
fun SyncNoiseBuffer3.smootherStep() = SMOOTHER_STEP.operate(this)
fun SyncNoiseBuffer3.sqrt() = SQUARE_ROOT.operate(this)
infix fun SyncNoiseBuffer3.add(other: SyncNoiseBuffer3) = ADD.operate(this, other)
fun SyncNoiseBuffer3.blend(other: SyncNoiseBuffer3, blend: Float) = BLEND.operate(this, other, blend)
infix fun SyncNoiseBuffer3.pow(other: SyncNoiseBuffer3) = COMBO_POW.operate(this, other)
infix fun SyncNoiseBuffer3.divide(other: SyncNoiseBuffer3) = DIVIDE.operate(this, other)
infix fun SyncNoiseBuffer3.max(other: SyncNoiseBuffer3) = MAX.operate(this, other)
infix fun SyncNoiseBuffer3.min(other: SyncNoiseBuffer3) = MIN.operate(this, other)
infix fun SyncNoiseBuffer3.multiply(other: SyncNoiseBuffer3) = MULTIPLY.operate(this, other)
infix fun SyncNoiseBuffer3.subtract(other: SyncNoiseBuffer3) = SUBTRACT.operate(this, other)
fun SyncNoiseBuffer3.plus(data: Float) = ADDITION.operate(this, data)
fun SyncNoiseBuffer3.minus(data: Float) = SUBTRACTION.operate(this, data)
fun SyncNoiseBuffer3.times(data: Float) = MULTIPLICATION.operate(this, data)
fun SyncNoiseBuffer3.div(data: Float) = DIVISION.operate(this, data)

fun AsyncNoiseBuffer2.clip(min: Float, max: Float) = CLIP.operate(this, min, max)
fun AsyncNoiseBuffer2.intPow(pow: Int) = INT_POW.operate(this, pow.f())
fun AsyncNoiseBuffer2.invert() = INVERT.operate(this)
fun AsyncNoiseBuffer2.lerp(min: Float, max: Float) = LERP.operate(this, min, max)
fun AsyncNoiseBuffer2.normalize() = NORMALIZE.operate(this)
fun AsyncNoiseBuffer2.polynomial(vararg coefficients: Float) = POLYNOMIAL.operate(this, *coefficients)
fun AsyncNoiseBuffer2.power(pow: Float) = POWER.operate(this, pow)
fun AsyncNoiseBuffer2.pull(min: Float, max: Float, inc: Float) = PULL.operate(this, min, max, inc)
fun AsyncNoiseBuffer2.push(min: Float, max: Float, inc: Float) = PUSH.operate(this, min, max, inc)
fun AsyncNoiseBuffer2.scale(scale: Float) = SCALE.operate(this, scale)
fun AsyncNoiseBuffer2.smoothStep() = SMOOTH_STEP.operate(this)
fun AsyncNoiseBuffer2.smootherStep() = SMOOTHER_STEP.operate(this)
fun AsyncNoiseBuffer2.sqrt() = SQUARE_ROOT.operate(this)
infix fun AsyncNoiseBuffer2.add(other: SyncNoiseBuffer2) = ADD.operate(this, other)
fun AsyncNoiseBuffer2.blend(other: SyncNoiseBuffer2, blend: Float) = BLEND.operate(this, other, blend)
infix fun AsyncNoiseBuffer2.pow(other: SyncNoiseBuffer2) = COMBO_POW.operate(this, other)
infix fun AsyncNoiseBuffer2.divide(other: SyncNoiseBuffer2) = DIVIDE.operate(this, other)
infix fun AsyncNoiseBuffer2.max(other: SyncNoiseBuffer2) = MAX.operate(this, other)
infix fun AsyncNoiseBuffer2.min(other: SyncNoiseBuffer2) = MIN.operate(this, other)
infix fun AsyncNoiseBuffer2.multiply(other: SyncNoiseBuffer2) = MULTIPLY.operate(this, other)
infix fun AsyncNoiseBuffer2.subtract(other: SyncNoiseBuffer2) = SUBTRACT.operate(this, other)
fun AsyncNoiseBuffer2.plus(data: Float) = ADDITION.operate(this, data)
fun AsyncNoiseBuffer2.minus(data: Float) = SUBTRACTION.operate(this, data)
fun AsyncNoiseBuffer2.times(data: Float) = MULTIPLICATION.operate(this, data)
fun AsyncNoiseBuffer2.div(data: Float) = DIVISION.operate(this, data)

fun AsyncNoiseBuffer3.clip(min: Float, max: Float) = CLIP.operate(this, min, max)
fun AsyncNoiseBuffer3.intPow(pow: Int) = INT_POW.operate(this, pow.f())
fun AsyncNoiseBuffer3.invert() = INVERT.operate(this)
fun AsyncNoiseBuffer3.lerp(min: Float, max: Float) = LERP.operate(this, min, max)
fun AsyncNoiseBuffer3.normalize() = NORMALIZE.operate(this)
fun AsyncNoiseBuffer3.polynomial(vararg coefficients: Float) = POLYNOMIAL.operate(this, *coefficients)
fun AsyncNoiseBuffer3.power(pow: Float) = POWER.operate(this, pow)
fun AsyncNoiseBuffer3.pull(min: Float, max: Float, inc: Float) = PULL.operate(this, min, max, inc)
fun AsyncNoiseBuffer3.push(min: Float, max: Float, inc: Float) = PUSH.operate(this, min, max, inc)
fun AsyncNoiseBuffer3.scale(scale: Float) = SCALE.operate(this, scale)
fun AsyncNoiseBuffer3.smoothStep() = SMOOTH_STEP.operate(this)
fun AsyncNoiseBuffer3.smootherStep() = SMOOTHER_STEP.operate(this)
fun AsyncNoiseBuffer3.sqrt() = SQUARE_ROOT.operate(this)
infix fun AsyncNoiseBuffer3.add(other: SyncNoiseBuffer3) = ADD.operate(this, other)
fun AsyncNoiseBuffer3.blend(other: SyncNoiseBuffer3, blend: Float) = BLEND.operate(this, other, blend)
infix fun AsyncNoiseBuffer3.pow(other: SyncNoiseBuffer3) = COMBO_POW.operate(this, other)
infix fun AsyncNoiseBuffer3.divide(other: SyncNoiseBuffer3) = DIVIDE.operate(this, other)
infix fun AsyncNoiseBuffer3.max(other: SyncNoiseBuffer3) = MAX.operate(this, other)
infix fun AsyncNoiseBuffer3.min(other: SyncNoiseBuffer3) = MIN.operate(this, other)
infix fun AsyncNoiseBuffer3.multiply(other: SyncNoiseBuffer3) = MULTIPLY.operate(this, other)
infix fun AsyncNoiseBuffer3.subtract(other: SyncNoiseBuffer3) = SUBTRACT.operate(this, other)
fun AsyncNoiseBuffer3.plus(data: Float) = ADDITION.operate(this, data)
fun AsyncNoiseBuffer3.minus(data: Float) = SUBTRACTION.operate(this, data)
fun AsyncNoiseBuffer3.times(data: Float) = MULTIPLICATION.operate(this, data)
fun AsyncNoiseBuffer3.div(data: Float) = DIVISION.operate(this, data)