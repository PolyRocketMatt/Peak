package com.github.polyrocketmatt.peak.buffer.simulation

import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer3

interface AsyncSimulator {

    fun simulate(buffer: AsyncNoiseBuffer2): AsyncNoiseBuffer2

    fun simulate(buffer: AsyncNoiseBuffer3): AsyncNoiseBuffer3

}