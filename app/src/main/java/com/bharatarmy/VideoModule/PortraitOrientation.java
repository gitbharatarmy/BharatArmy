package com.bharatarmy.VideoModule;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT;

public enum PortraitOrientation {
    SENSOR(SCREEN_ORIENTATION_SENSOR_PORTRAIT),
    DEFAULT(SCREEN_ORIENTATION_PORTRAIT),
    REVERSE(SCREEN_ORIENTATION_REVERSE_PORTRAIT),
    USER(SCREEN_ORIENTATION_USER_PORTRAIT);

    private final int value;

    PortraitOrientation(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
