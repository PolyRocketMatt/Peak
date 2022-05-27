package com.github.polyrocketmatt.peak.math

import com.github.polyrocketmatt.game.math.smoothStep
import kotlin.math.max
import kotlin.math.min

/**
 * Smooth-step function between a provided min and max, evaluated on the
 * provided value.
 *
 * @param value: the value to evaluate
 * @param min: the lower bound for the smooth-step
 * @param max: the upper-bound for the smooth-step
 * @return the evaluated value in the smooth-step function
 */
fun Float.smoothStepLerp(min: Float, max: Float): Float {
    val lambda = min(1.0f, max(0.0f, (this - min) / (max - min)))
    return lambda.smoothStep()
}

/**
 * Inverted smooth-step function between a provided min and max, evaluated on the
 * provided value.
 *
 * @param value: the value to evaluate
 * @param min: the lower bound for the smooth-step
 * @param max: the upper-bound for the smooth-step
 * @return the inverted evaluated value in the smooth-step function
 */
fun Float.inverseSmoothStepLerp(min: Float, max: Float): Float {
    val lambda = min(1.0f, max(0.0f, (this - min) / (max - min)))
    return 1.0f - lambda.smoothStep()
}

class SmoothUtils {

    companion object {

        /**
         * Smooth-step function between a provided min and max, evaluated on the
         * provided value.
         *
         * @param value: the value to evaluate
         * @param min: the lower bound for the smooth-step
         * @param max: the upper-bound for the smooth-step
         * @return the evaluated value in the smooth-step function
         */
        fun smoothStepLerp(value: Float, min: Float, max: Float): Float {
            val lambda = min(1.0f, max(0.0f, (value - min) / (max - min)))
            return lambda.smoothStep()
        }

        /**
         * Inverted smooth-step function between a provided min and max, evaluated on the
         * provided value.
         *
         * @param value: the value to evaluate
         * @param min: the lower bound for the smooth-step
         * @param max: the upper-bound for the smooth-step
         * @return the inverted evaluated value in the smooth-step function
         */
        fun inverseSmoothStepLerp(value: Float, min: Float, max: Float): Float {
            val lambda = min(1.0f, max(0.0f, (value - min) / (max - min)))
            return 1.0f - lambda.smoothStep()
        }

    }

}