package com.github.polyrocketmatt.peak.buffer.simulation

import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer3

interface Simulation {

    /**
     * Perform a simulation on a 2D buffer.
     *
     * @param buffer: the buffer to perform the simulation on
     * @return a new buffer that contains the effect of the simulation on the buffer
     */
    fun simulate(buffer: AsyncNoiseBuffer2): AsyncNoiseBuffer2

    /**
     * Perform a simulation on a 3D buffer.
     *
     * @param buffer: the buffer to perform the simulation on
     * @return a new buffer that contains the effect of the simulation on the buffer
     */
    fun simulate(buffer: AsyncNoiseBuffer3): AsyncNoiseBuffer3

}