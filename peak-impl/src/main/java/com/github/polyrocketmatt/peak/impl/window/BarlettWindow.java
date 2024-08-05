package com.github.polyrocketmatt.peak.impl.window;

import com.github.polyrocketmatt.peak.api.buffer.DataBuffer;
import com.github.polyrocketmatt.peak.api.window.WindowContext;
import com.github.polyrocketmatt.peak.api.window.WindowFunction;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.abs;

public class BarlettWindow implements WindowFunction {

    protected BarlettWindow() {}

    @Override
    public void applyFloat(@NotNull DataBuffer<Float> buffer, @NotNull WindowContext<Float> ctx) {
        int N = buffer.size();
        buffer.multIndexedBy((idx, val) -> 1 - abs((idx - (N - 1) * 0.5f) / ((N - 1) * 0.5f)));
    }

    @Override
    public void applyDouble(@NotNull DataBuffer<Double> buffer, @NotNull WindowContext<Double> ctx) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void applyInt(@NotNull DataBuffer<Integer> buffer, @NotNull WindowContext<Integer> ctx) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
