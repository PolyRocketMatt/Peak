package com.github.polyrocketmatt.peak.api.window;

import com.github.polyrocketmatt.peak.api.buffer.DataBuffer;
import org.jetbrains.annotations.NotNull;

public interface WindowFunction {

    void applyFloat(@NotNull DataBuffer<Float> buffer, @NotNull WindowContext<Float> ctx);

    void applyDouble(@NotNull DataBuffer<Double> buffer, @NotNull WindowContext<Double> ctx);

    void applyInt(@NotNull DataBuffer<Integer> buffer, @NotNull WindowContext<Integer> ctx);

}
