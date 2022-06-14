package com.github.polyrocketmatt.peak.exception

/**
 * Thrown when an exception in a buffer initialization has occurred.
 *
 * @param cause: the cause of the exception
 */
class BufferInitException(cause: String) : IllegalArgumentException(cause)