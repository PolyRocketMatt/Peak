package com.github.polyrocketmatt.peak.impl.window;

import com.github.polyrocketmatt.peak.api.buffer.DataBuffer;
import com.github.polyrocketmatt.peak.api.window.WindowContext;
import com.github.polyrocketmatt.peak.api.window.WindowFunction;
import com.github.polyrocketmatt.peak.impl.PeakConstants;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.cos;

public class HanningWindow implements WindowFunction {

    protected HanningWindow() {}

    @Override
    public void applyFloat(@NotNull DataBuffer<Float> buffer, @NotNull WindowContext<Float> ctx) {
        int N = buffer.size();
        buffer.multIndexedBy((idx, val) -> 0.5f * (1.0f - (float) cos(PeakConstants.TWO_PI * idx / (N - 1))));
    }

    @Override
    public void applyDouble(@NotNull DataBuffer<Double> buffer, @NotNull WindowContext<Double> ctx) {

    }

    @Override
    public void applyInt(@NotNull DataBuffer<Integer> buffer, @NotNull WindowContext<Integer> ctx) {

    }
}
