package com.github.polyrocketmatt.peak.exception

/**
 * Thrown when an exception in a simulation has occurred.
 *
 * @param cause: the cause of the exception
 */
class SimulationException(cause: String) : IllegalArgumentException(cause)