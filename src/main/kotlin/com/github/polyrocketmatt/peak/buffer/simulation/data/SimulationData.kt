package com.github.polyrocketmatt.peak.buffer.simulation.data

import com.github.polyrocketmatt.peak.buffer.simulation.Simulation

abstract class SimulationData {

    /**
     * Get a new instance of a simulator for this data.
     */
    abstract fun simulator(): Simulation

}