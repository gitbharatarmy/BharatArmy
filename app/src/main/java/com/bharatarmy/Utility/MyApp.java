package com.bharatarmy.Utility;

import android.app.Application;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "header_font", "TheSans-Plain.ttf");
        //  This FontsOverride comes from the example I posted above
    }
}
