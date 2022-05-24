package com.github.polyrocketmatt.peak.noise.primitive

import com.github.polyrocketmatt.peak.noise.buffer.NoiseBuffer
import java.awt.image.BufferedImage

abstract class NoisePrimitive(val buffer: NoiseBuffer) {

    abstract fun recompute()

    abstract fun buffer(): NoiseBuffer

    abstract fun image(): BufferedImage

}