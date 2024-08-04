package com.github.polyrocketmatt.peak.impl.buffer;

import com.github.polyrocketmatt.peak.api.buffer.DataBuffer;
import com.github.polyrocketmatt.peak.api.buffer.DataContext;
import org.jetbrains.annotations.NotNull;

public class DataBufferFactory {

    public static @NotNull DataBuffer<Float> getFloatBuffer(int size) {
        return new DataBufferFloat1D(size);
    }

    public static @NotNull DataBuffer<Float> getFloatBuffer(int size, DataContext context) {
        return new DataBufferFloat1D(size, context);
    }

    public static @NotNull DataBuffer<Float> getFloatBuffer(int width, int height) {
        return new DataBufferFloat2D(width, height);
    }

    public static @NotNull DataBuffer<Float> getFloatBuffer(int width, int height, DataContext context) {
        return new DataBufferFloat2D(width, height);
    }

}
