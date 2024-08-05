package com.github.polyrocketmatt.peak.api.buffer;

import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

/**
 * Provides operations for manipulating the values of a {@link DataBuffer}.
 *
 * @param <T> the type of value in the buffer
 *
 * @author Matthias Kovacic
 * @since 0.0.1
 * @version 0.0.1
 * @see DataBuffer
 */
public interface DataBufferOperations<T> {

    /**
     * Add the values of the given buffer to this buffer.
     *
     * @param buffer the buffer to add
     * @return the buffer with the added values
     */
    @NotNull DataBuffer<T> add(@NotNull DataBuffer<T> buffer);

    /**
     * Add the given value to all values in this buffer.
     *
     * @param value the value to add
     * @return the buffer with the added values
     */
    @NotNull DataBuffer<T> add(@NotNull T value);

    /**
     * Subtract the values of the given buffer from this buffer.
     *
     * @param buffer the buffer to subtract
     * @return the buffer with the subtracted values
     */
    @NotNull DataBuffer<T> sub(@NotNull DataBuffer<T> buffer);

    /**
     * Subtract the given value from all values in this buffer.
     *
     * @param value the value to subtract
     * @return the buffer with the subtracted values
     */
    @NotNull DataBuffer<T> sub(@NotNull T value);

    /**
     * Multiply the values of the given buffer with this buffer.
     *
     * @param buffer the buffer to multiply
     * @return the buffer with the multiplied values
     */
    @NotNull DataBuffer<T> mul(@NotNull DataBuffer<T> buffer);

    /**
     * Multiply all values in this buffer with the given value.
     *
     * @param value the value to multiply
     * @return the buffer with the multiplied values
     */
    @NotNull DataBuffer<T> mul(@NotNull T value);

    /**
     * Multiply all values in this buffer with the evaluation of the function given the index and the value.
     *
     * @param function the function to evaluate
     * @return the buffer with the multiplied values
     */
    @NotNull DataBuffer<T> multIndexedBy(@NotNull BiFunction<Integer, T, T> function);

    /**
     * Divide the values of the given buffer by this buffer.
     *
     * @param buffer the buffer to divide
     * @return the buffer with the divided values
     * @throws ArithmeticException if a division by zero occurs
     */
    @NotNull DataBuffer<T> div(@NotNull DataBuffer<T> buffer) throws ArithmeticException;

    /**
     * Divide all values in this buffer by the given value.
     *
     * @param value the value to divide
     * @return the buffer with the divided values
     * @throws ArithmeticException if the value is zero
     */
    @NotNull DataBuffer<T> div(@NotNull T value) throws ArithmeticException;

    /**
     * Scale all values in this buffer by the given value.
     *
     * @param value the value to scale by
     * @return the buffer with the scaled values
     */
    @NotNull DataBuffer<T> scale(@NotNull T value);

    /**
     * Get the absolute values of all elements in this buffer.
     *
     * @return the buffer with the absolute values
     */
    @NotNull DataBuffer<T> abs();

    /**
     * Normalize the values in this buffer.
     *
     * @return the buffer with the normalized values
     */
    @NotNull DataBuffer<T> normalize();

    //  TODO: square, sqrt, ...

}
