package com.github.polyrocketmatt.peak.api.buffer;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a one-dimensional data buffer.
 *
 * @param <T> the type of data stored in the buffer
 *
 * @author Matthias Kovacic
 * @since 0.0.1
 * @version 0.0.1
 * @see DataBuffer
 */
public interface DataBuffer1D<T> extends DataBuffer<T> {

    /**
     * Gets the value at the specified index.
     *
     * @param i the index
     * @return the value at the index
     */
    @NotNull T get(int i);

    /**
     * Sets the value at the specified index.
     *
     * @param i the index
     * @param value the value to set
     */
    void set(int i, @NotNull T value);

}
