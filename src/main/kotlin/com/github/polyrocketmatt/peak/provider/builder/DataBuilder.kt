package com.github.polyrocketmatt.peak.provider.builder

import com.github.polyrocketmatt.peak.provider.data.NoiseData

/**
 * Builder for provider data.
 */
interface DataBuilder {

    /**
     * Build the ProviderData.
     */
    fun build(): NoiseData

}