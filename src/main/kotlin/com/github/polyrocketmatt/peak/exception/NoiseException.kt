package com.github.polyrocketmatt.peak.exception

/**
 * Thrown when an exception concerning a noise buffer has occurred.
 *
 * @param cause: the cause of the exception
 */
class NoiseException(cause: String) : IllegalArgumentException(cause)