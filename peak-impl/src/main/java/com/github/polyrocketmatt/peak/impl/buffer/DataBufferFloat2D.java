package com.github.polyrocketmatt.peak.impl.buffer;

import com.github.polyrocketmatt.peak.api.buffer.DataBuffer2D;
import com.github.polyrocketmatt.peak.api.buffer.DataContext;
import org.jetbrains.annotations.NotNull;

public class DataBufferFloat2D extends AbstractDataBufferFloat implements DataBuffer2D<Float> {

    private final int width;
    private final int height;

    protected DataBufferFloat2D(int width, int height) {
        this(width, height, DataContext.DEFAULT);
    }

    protected DataBufferFloat2D(int width, int height, DataContext context) {
        super(width * height, context);

        this.width = width;
        this.height = height;
    }

    protected DataBufferFloat2D(int width, int height, int chunkSize, Float[] data, boolean executeParallel) {
        super(width * height, chunkSize, data, executeParallel);

        this.width = width;
        this.height = height;
    }

    @Override
    public String prettyPrint() {
        StringBuilder builder = new StringBuilder();

        int threshold = 4;
        boolean skipWidth = width > threshold;
        boolean skipHeight = height > threshold;
        for (int i = 0; i < height; i++) {
            if (skipHeight && (i > threshold && i < height - threshold)) {
                if (i == threshold + 1)
                    builder.append("... (Skipped ").append(height - 2 * threshold).append(" rows)\n");
                continue;
            }

            builder.append("| ");
            for (int j = 0; j < width; j++) {
                if (skipWidth && (j > threshold && j < width - threshold)) {
                    if (j == threshold + 1)
                        builder.append("... ");
                    continue;
                }

                builder.append(data[i * width + j]);
                builder.append(" ");
            }
            builder.append("|\n");
        }
        return builder.toString();
    }

    @Override
    public @NotNull Float get(int i, int j) {
        if (i < 0 || i >= height || j < 0 || j >= width)
            throw new IndexOutOfBoundsException("Index out of bounds: (" + i + ", " + j + ")");
        return data[i * width + j];
    }

    @Override
    public void set(int i, int j, @NotNull Float value) {
        if (i < 0 || i >= height || j < 0 || j >= width)
            throw new IndexOutOfBoundsException("Index out of bounds: (" + i + ", " + j + ")");
        data[i * width + j] = value;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public DataBuffer2D<Float> transpose() {
        Float[] newData = new Float[data.length];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                newData[j * height + i] = data[i * width + j];
        return new DataBufferFloat2D(height, width, chunkSize, newData, executeParallel);
    }
}