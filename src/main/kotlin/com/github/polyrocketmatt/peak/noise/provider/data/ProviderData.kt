package com.github.polyrocketmatt.peak.noise.provider.data

import com.github.polyrocketmatt.peak.noise.provider.primitive.SimpleNoiseProvider

abstract class ProviderData {

    abstract fun provider(): SimpleNoiseProvider

}