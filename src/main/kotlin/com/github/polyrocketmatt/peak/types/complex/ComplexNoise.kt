package com.github.polyrocketmatt.peak.types.complex

import com.github.polyrocketmatt.peak.annotation.Desc
import com.github.polyrocketmatt.peak.annotation.Ref
import com.github.polyrocketmatt.peak.types.NoiseEvaluator

/**
 * Represents complex noise.
 */
abstract class ComplexNoise : NoiseEvaluator() {

    /**
     * The types of complex noise.
     */
    @Ref("https://developers.maxon.net/docs/Cinema4DPythonSDK/html/types/noise.html")
    enum class ComplexNoiseType {
        BLISTERED_TURBULENCE,

        @Desc("Great for creating large-scale biome-placement")
        BUYA,
        CRANAL,
        DENTS,
        ELECTRIC,
        FIRE,
        GASEOUS,
        LUKA,
        NAKI,
        NUTOUS,
        OBER,
        PEZO,
        POXO,
        SPARSE_CONVOLUTION,
        GABOR,
        STUPL,
        DISPLACEMENT,
        PERLIN_WORMS,
        PERLIN_FLOW
    }

    /**
     * Get the type of complex noise.
     *
     * @return the type of complex noise.
     */
    abstract fun type(): ComplexNoiseType

}