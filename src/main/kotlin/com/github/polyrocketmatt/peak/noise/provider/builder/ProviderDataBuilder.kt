package com.github.polyrocketmatt.peak.noise.provider.builder

import com.github.polyrocketmatt.peak.noise.provider.data.ProviderData

interface ProviderDataBuilder {

    /**
     * Build the ProviderData.
     */
    fun build(): ProviderData

}