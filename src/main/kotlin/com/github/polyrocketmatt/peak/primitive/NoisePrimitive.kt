package com.github.polyrocketmatt.peak.primitive

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import java.awt.image.BufferedImage

abstract class NoisePrimitive(val buffer: NoiseBuffer) {

    abstract fun recompute()

    abstract fun buffer(): NoiseBuffer

    abstract fun image(): BufferedImage

}