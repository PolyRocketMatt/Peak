package com.github.polyrocketmatt.peak.api.buffer;

import com.github.polyrocketmatt.peak.api.data.DataChunk;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Represents a buffer of values.
 *
 * @param <T> the type of value in the buffer
 *
 * @author Matthias Kovacic
 * @since 0.0.1
 * @version 0.0.1
 */
public interface DataBuffer<T> {

    /**
     * Get the {@link DataBufferType} of this buffer.
     *
     * @return the buffer type
     * @see DataBufferType
     */
    @NotNull DataBufferType getBufferType();

    /**
     * Get the minimum value in the buffer.
     *
     * @return the minimum value
     */
    @NotNull T min();

    /**
     * Get the maximum value in the buffer.
     *
     * @return the maximum value
     */
    @NotNull T max();

    /**
     * Get the sum of all values in the buffer.
     *
     * @return the sum of all values
     */
    @NotNull T sum();

    /**
     * Get the average of all values in the buffer.
     *
     * @return the average of all values
     */
    @NotNull T average();

    /**
     * Fill the buffer with random values between 0 and 1.
     */
    void rand();

    /**
     * Map the buffer values to a new value using the given function.
     *
     * @param function the function to map the values
     */
    void map(@NotNull Function<T, T> function);

    /**
     * Map the buffer values to a new value using the given function.
     *
     * @param function the function to map the values
     * @return a stream of the mapped values
     */
    <R> @NotNull Stream<R> mapTo(@NotNull Function<DataChunk<T>, R> function);

    /**
     * Create a copy of this buffer.
     *
     * @return the copy
     */
    DataBuffer<T> copy();

}
