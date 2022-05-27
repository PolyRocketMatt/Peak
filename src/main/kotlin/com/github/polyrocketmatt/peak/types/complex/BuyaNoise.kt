package com.github.polyrocketmatt.peak.types.complex

class BuyaNoise(

) : ComplexNoise() {

    override fun noise(nX: Float, nY: Float): Float {
        TODO("Not yet implemented")
    }

    override fun noise(nX: Float, nY: Float, nZ: Float): Float {
        TODO("Not yet implemented")
    }

    override fun type(): ComplexNoiseType = ComplexNoiseType.BUYA

}