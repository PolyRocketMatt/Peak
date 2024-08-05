package com.github.polyrocketmatt.peak.impl.window;

import com.github.polyrocketmatt.peak.api.buffer.DataBuffer;
import com.github.polyrocketmatt.peak.api.window.WindowContext;
import com.github.polyrocketmatt.peak.api.window.WindowFunction;
import org.jetbrains.annotations.NotNull;

public class DirichletWindow implements WindowFunction {

    protected DirichletWindow() {}

    @Override
    public void applyFloat(@NotNull DataBuffer<Float> buffer, @NotNull WindowContext<Float> ctx) {}

    @Override
    public void applyDouble(@NotNull DataBuffer<Double> buffer, @NotNull WindowContext<Double> ctx) {}

    @Override
    public void applyInt(@NotNull DataBuffer<Integer> buffer, @NotNull WindowContext<Integer> ctx) {}
}
