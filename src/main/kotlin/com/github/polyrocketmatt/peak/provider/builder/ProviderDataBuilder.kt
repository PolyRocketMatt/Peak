package com.github.polyrocketmatt.peak.provider.builder

import com.github.polyrocketmatt.peak.provider.data.NoiseData

/**
 * Builder for provider data.
 */
@FunctionalInterface
interface ProviderDataBuilder {

    /**
     * Build the ProviderData.
     */
    fun build(): NoiseData

}