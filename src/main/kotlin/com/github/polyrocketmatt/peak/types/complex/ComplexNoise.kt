package com.github.polyrocketmatt.peak.types.complex

import com.github.polyrocketmatt.peak.annotation.Ref
import com.github.polyrocketmatt.peak.types.NoiseEvaluator

abstract class ComplexNoise : NoiseEvaluator {

    @Ref("https://developers.maxon.net/docs/Cinema4DPythonSDK/html/types/noise.html")
    enum class ComplexNoiseType {
        BLISTERED_TURBULENCE,
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

}