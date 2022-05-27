package com.github.polyrocketmatt.peak.buffer

import com.github.polyrocketmatt.peak.provider.base.SimpleNoiseProvider

interface NoiseBuffer {

    fun min(): Float

    fun max(): Float

    fun forEach(action: (Float) -> Unit)

    fun map(transform: (Float) -> Float): NoiseBuffer

    fun content(): NoiseBuffer

    fun copy(): NoiseBuffer

    fun fill(provider: SimpleNoiseProvider): NoiseBuffer

}