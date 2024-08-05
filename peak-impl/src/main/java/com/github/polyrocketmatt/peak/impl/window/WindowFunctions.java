package com.github.polyrocketmatt.peak.impl.window;

import com.github.polyrocketmatt.peak.api.window.WindowFunction;

public class WindowFunctions {

    public static final WindowFunction DIRICHLET    = new DirichletWindow();
    public static final WindowFunction BARLETT      = new BarlettWindow();

}
