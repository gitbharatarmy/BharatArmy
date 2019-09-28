package com.bharatarmy;

import android.app.Application;

public class MyApplicationpip extends Application {

    public static final int MODE_NONE = 0;
    public static final int MODE_FULL = 1;
    public static final int MODE_PIP = 2;

    public int mode = MODE_NONE;
    public int orgDensityDpi = 0;

}
