package com.github.polyrocketmatt.peak.provider.data

import com.github.polyrocketmatt.peak.provider.base.SimpleNoiseProvider

abstract class ProviderData {

    abstract fun provider(): SimpleNoiseProvider

}