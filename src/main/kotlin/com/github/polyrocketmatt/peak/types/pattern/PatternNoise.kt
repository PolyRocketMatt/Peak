package com.github.polyrocketmatt.peak.types.pattern

import com.github.polyrocketmatt.peak.annotation.Ref
import com.github.polyrocketmatt.peak.types.NoiseEvaluator

abstract class PatternNoise : NoiseEvaluator {

    @Ref("https://www.cs.utexas.edu/~theshark/courses/cs354/lectures/cs354-21.pdf")
    enum class PatternType {
        CHECKERBOARD,
        GRID,
        STRAIGHT_STRIPE,
        RADIAL_STRIPE
    }

    enum class PatternOrientation {
        HORIZONTAL,
        DIAGONAL,
        VERTICAL,
        NOT_AVAILABLE
    }

    abstract fun type(): PatternType

    abstract fun orientation(): PatternOrientation

}