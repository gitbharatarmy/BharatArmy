package com.bharatarmy.VideoModule;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE;

public enum LandscapeOrientation {
    SENSOR(SCREEN_ORIENTATION_SENSOR_LANDSCAPE),
    DEFAULT(SCREEN_ORIENTATION_LANDSCAPE),
    REVERSE(SCREEN_ORIENTATION_REVERSE_LANDSCAPE),
    USER(SCREEN_ORIENTATION_USER_LANDSCAPE);

    private final int value;

    LandscapeOrientation(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
