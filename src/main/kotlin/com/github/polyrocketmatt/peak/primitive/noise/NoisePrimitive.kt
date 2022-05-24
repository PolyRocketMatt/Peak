package com.github.polyrocketmatt.peak.primitive.noise

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.primitive.Primitive

/**
 * Defines a noise-related primitive, based on a noise buffer.
 */
abstract class NoisePrimitive(val buffer: NoiseBuffer): Primitive