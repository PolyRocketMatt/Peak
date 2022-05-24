package com.github.polyrocketmatt.peak.provider.data

import com.github.polyrocketmatt.peak.provider.primitive.SimpleNoiseProvider

abstract class ProviderData {

    abstract fun provider(): SimpleNoiseProvider

}