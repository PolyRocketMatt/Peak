package com.github.polyrocketmatt.peak.api.window;

import com.github.polyrocketmatt.peak.api.buffer.DataBuffer;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a window function that can be applied to a buffer.
 *
 * @author Matthias Kovacic
 * @since 0.0.1
 * @version 0.0.1
 * @see WindowContext
 */
public interface WindowFunction {

    /**
     * Applies the window function to the given floating-point buffer.
     *
     * @param buffer the buffer to apply the window function to
     * @param ctx the context of the window function
     */
    void applyFloat(@NotNull DataBuffer<Float> buffer, @NotNull WindowContext<Float> ctx);

    /**
     * Applies the window function to the given double buffer.
     *
     * @param buffer the buffer to apply the window function to
     * @param ctx the context of the window function
     */
    void applyDouble(@NotNull DataBuffer<Double> buffer, @NotNull WindowContext<Double> ctx);

    /**
     * Applies the window function to the given integer buffer.
     *
     * @param buffer the buffer to apply the window function to
     * @param ctx the context of the window function
     */
    void applyInt(@NotNull DataBuffer<Integer> buffer, @NotNull WindowContext<Integer> ctx);

}
