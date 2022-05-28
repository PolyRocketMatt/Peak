package com.github.polyrocketmatt.peak.types.complex

import com.github.polyrocketmatt.peak.types.NoiseEvaluator

class BuyaNoise(

) : ComplexNoise() {

    override fun noise(nX: Float, nY: Float): Float {
        TODO("Not yet implemented")
    }

    override fun noise(nX: Float, nY: Float, nZ: Float): Float {
        TODO("Not yet implemented")
    }

    override fun type(): ComplexNoiseType = ComplexNoiseType.BUYA

    override fun clone(): BuyaNoise {
        TODO("Not yet implemented")
    }

}