package com.github.polyrocketmatt.peak.provider.builder

import com.github.polyrocketmatt.peak.provider.data.ProviderData

interface ProviderDataBuilder {

    /**
     * Build the ProviderData.
     */
    fun build(): ProviderData

}