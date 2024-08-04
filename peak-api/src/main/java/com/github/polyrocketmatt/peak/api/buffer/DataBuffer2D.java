package com.github.polyrocketmatt.peak.api.buffer;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a two-dimensional data buffer.
 *
 * @param <T> the type of data stored in the buffer
 *
 * @author Matthias Kovacic
 * @since 0.0.1
 * @version 0.0.1
 * @see DataBuffer
 */
public interface DataBuffer2D<T> {

    /**
     * Gets the value at the specified index.
     *
     * @param i the row index
     * @param j the column index
     * @return the value at the index
     */
    @NotNull T get(int i, int j);

    /**
     * Sets the value at the specified index.
     *
     * @param i the row index
     * @param j the column index
     * @param value the value to set
     */
    void set(int i, int j, @NotNull T value);

}
