package com.github.polyrocketmatt.peak.impl.buffer;

import com.github.polyrocketmatt.peak.api.buffer.DataBuffer;
import com.github.polyrocketmatt.peak.api.data.DataChunk;
import com.github.polyrocketmatt.peak.api.data.DataChunkContext;
import com.github.polyrocketmatt.peak.impl.data.SimpleDataChunkFloat;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class AbstractDataBufferFloat implements DataBuffer<Float> {

    protected final int size;
    protected final int chunkSize;
    protected final List<DataChunk<Float>> data;

    protected final boolean executeParallel;

    public AbstractDataBufferFloat(int size, DataChunkContext context) {
        this.size = size;
        this.chunkSize = context.chunkSize();
        this.data = new ArrayList<>();

        int chunks = size / chunkSize + 1;
        this.executeParallel = context.autoParallel() ? (chunks >= 8192) : context.parallel();

        for (int i = 0; i < chunks; i++) {
            int chunkSizeActual = Math.min(chunkSize, size - i * chunkSize);
            if (chunkSizeActual <= 0)
                break;
            data.add(new SimpleDataChunkFloat(i, chunkSizeActual));
        }
    }

    protected AbstractDataBufferFloat(int size, int chunkSize, List<DataChunk<Float>> data, boolean executeParallel) {
        this.size = size;
        this.chunkSize = chunkSize;
        this.data = data;
        this.executeParallel = executeParallel;
    }

    protected void forEachChunk(@NotNull DataBuffer<Float> buffer, @NotNull BiConsumer<DataChunk<Float>, DataChunk<Float>> consumer) {
        if (executeParallel)
            data.parallelStream().forEach(chunk -> consumer.accept(chunk, buffer.getChunk(chunk.getIndex())));
        else
            data.forEach(chunk -> consumer.accept(chunk, buffer.getChunk(chunk.getIndex())));
    }

    protected void forEachChunk(@NotNull Float value, @NotNull BiConsumer<DataChunk<Float>, Float> consumer) {
        if (executeParallel)
            data.parallelStream().forEach(chunk -> consumer.accept(chunk, value));
        else
            data.forEach(chunk -> consumer.accept(chunk, value));
    }

}
