package com.github.polyrocketmatt.peak.provider.data

import com.github.polyrocketmatt.peak.provider.base.SimpleNoiseProvider

/**
 * Class that holds data for a noise provider.
 */
abstract class ProviderData {

    /**
     * Get a new instance of a noise provider for this data.
     */
    abstract fun provider(): SimpleNoiseProvider

}