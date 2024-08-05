package com.github.polyrocketmatt.peak.impl.window;

import com.github.polyrocketmatt.peak.api.window.WindowFunction;
import com.github.polyrocketmatt.peak.api.window.WindowFunctionType;

public class WindowFunctions {

    private static final WindowFunction DIRICHLET_WINDOW    = new DirichletWindow();
    private static final WindowFunction BARLETT_WINDOW      = new BartlettWindow();
    private static final WindowFunction HANNING_WINDOW      = new HanningWindow();

    public static WindowFunction get(WindowFunctionType type) {
        return switch (type) {
            case DIRICHLET  -> DIRICHLET_WINDOW;
            case BARTLETT   -> BARLETT_WINDOW;
            case HANNING    -> HANNING_WINDOW;
        };
    }

}
