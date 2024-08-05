package com.github.polyrocketmatt.peak.api.window;

import com.github.polyrocketmatt.peak.api.buffer.DataBuffer;
import org.jetbrains.annotations.NotNull;

public interface DataBufferWindowing<T> {

    @NotNull DataBuffer<T> window(WindowFunction window, WindowContext<T> ctx);

    @NotNull DataBuffer<T> window(WindowFunction window);

}
