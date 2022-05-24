package com.github.polyrocketmatt.peak.primitive.geo

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.primitive.Primitive

/**
 * Defines a geo-primitive, based on a noise buffer.
 */
abstract class GeoPrimitive(val buffer: NoiseBuffer) : Primitive