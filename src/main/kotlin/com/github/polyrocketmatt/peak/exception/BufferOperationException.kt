package com.github.polyrocketmatt.peak.exception

/**
 * Thrown when an exception in a buffer has occurred.
 *
 * @param cause: the cause of the exception
 */
class BufferOperationException(cause: String) : IllegalArgumentException(cause)