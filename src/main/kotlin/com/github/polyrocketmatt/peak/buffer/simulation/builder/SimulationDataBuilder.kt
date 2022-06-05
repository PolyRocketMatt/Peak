package com.github.polyrocketmatt.peak.buffer.simulation.builder

import com.github.polyrocketmatt.peak.buffer.simulation.data.SimulationData

@FunctionalInterface
interface SimulationDataBuilder {

    /**
     * Build the SimulationData.
     */
    fun build(): SimulationData

}