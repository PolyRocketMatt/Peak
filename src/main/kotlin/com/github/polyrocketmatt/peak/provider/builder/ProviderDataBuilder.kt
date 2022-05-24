package com.github.polyrocketmatt.peak.provider.builder

import com.github.polyrocketmatt.peak.provider.data.ProviderData

/**
 * Builder for provider data.
 */
interface ProviderDataBuilder {

    /**
     * Build the ProviderData.
     */
    fun build(): ProviderData

}