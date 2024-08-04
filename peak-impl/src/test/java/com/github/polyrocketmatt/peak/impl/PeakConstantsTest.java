package com.github.polyrocketmatt.peak.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PeakConstantsTest {

    @Test void testBaseConstants() {
        assertEquals(8192, PeakConstants.DEFAULT_CHUNK_SIZE_1D);
    }

}
