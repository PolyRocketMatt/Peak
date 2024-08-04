package com.github.polyrocketmatt.peak.api.exception;

/**
 * Exception thrown when an error occurs during data computation.
 *
 * @author Matthias Kovacic
 * @since 0.0.1
 * @version 0.0.1
 */
public class DataComputationException extends RuntimeException {

    /**
     * Constructs a new data computation exception with the specified nested exception.
     *
     * @param nestedException the nested exception
     */
    public DataComputationException(Exception nestedException) {
        super(nestedException);
    }

}
