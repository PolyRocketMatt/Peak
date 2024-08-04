package com.github.polyrocketmatt.peak.impl.buffer;

import com.github.polyrocketmatt.peak.api.buffer.DataBuffer1D;
import com.github.polyrocketmatt.peak.api.buffer.DataContext;

public class DataBufferFloat1D extends AbstractDataBufferFloat implements DataBuffer1D<Float> {

    protected DataBufferFloat1D(int size) {
        this(size, DataContext.DEFAULT);
    }

    protected DataBufferFloat1D(int size, DataContext context) {
        super(size, context);
    }

    protected DataBufferFloat1D(int size, int chunkSize, Float[] data, boolean executeParallel) {
        super(size, chunkSize, data, executeParallel);
    }

}
