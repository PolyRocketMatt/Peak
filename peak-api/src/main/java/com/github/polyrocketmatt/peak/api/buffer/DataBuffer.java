package com.github.polyrocketmatt.peak.api.buffer;

import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
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
public interface DataBuffer<T> extends DataBufferArithmetic<T> {

    /**
     * Get the {@link DataBufferType} of this buffer.
     *
     * @return the buffer type
     * @see DataBufferType
     */
    @NotNull DataBufferType getBufferType();

    /**
     * Get the element at the given index.
     *
     * @param index the index
     * @return the element
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @NotNull T get(int index) throws IndexOutOfBoundsException;

    /**
     * Set the element at the given index.
     *
     * @param index the index
     * @param value the value
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    void set(int index, @NotNull T value) throws IndexOutOfBoundsException;

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
    @NotNull DataBuffer<T> rand();

    /**
     * Fill the buffer with the given value.
     *
     * @param value the value to fill the buffer with
     */
    @NotNull DataBuffer<T> fill(@NotNull T value);

    /**
     * Get a stream of the buffer values.
     *
     * @return the stream
     */
    Stream<T> stream();

    /**
     * For each index in the buffer, execute the given action.
     *
     * @param action the action to execute
     */
    void forEachIndexed(@NotNull BiConsumer<Integer, T> action);

    /**
     * For each index in the buffer, map the value to a new value using the given function.
     *
     * @param function the function to map the values
     */
    void mapIndexed(@NotNull BiFunction<Integer, T, T> function);

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
    <R> @NotNull Stream<R> mapTo(@NotNull Function<T, R> function);

    /**
     * Check if this buffer is similar in both type and dimensions to the given buffer.
     *
     * @param buffer the buffer to compare
     * @return true if the buffers are similar, false otherwise
     */
    boolean isSimilar(@NotNull DataBuffer<T> buffer);

    /**
     * Create a copy of this buffer.
     *
     * @return the copy
     */
    DataBuffer<T> copy();

    String prettyPrint();

}
