package com.github.polyrocketmatt.peak.types.pattern

import com.github.polyrocketmatt.peak.annotation.Ref
import com.github.polyrocketmatt.peak.types.NoiseEvaluator

/**
 * Represents pattern noise.
 */
abstract class PatternNoise : NoiseEvaluator() {

    /**
     * The types of patterns.
     */
    @Ref("https://www.cs.utexas.edu/~theshark/courses/cs354/lectures/cs354-21.pdf")
    enum class PatternNoiseType {
        CHECKERBOARD,
        GRID,
        STRAIGHT_STRIPE,
        RADIAL_STRIPE
    }

    /**
     * The possible orientations of patterns.
     */
    enum class PatternOrientation {
        HORIZONTAL,
        DIAGONAL,
        VERTICAL,
        NOT_AVAILABLE
    }

    /**
     * Get the type of pattern noise.
     *
     * @return the type of pattern noise.
     */
    abstract fun type(): PatternNoiseType

    /**
     * Get the orientation of the noise.
     *
     * @return the orientation of the noise.
     */
    abstract fun orientation(): PatternOrientation

}