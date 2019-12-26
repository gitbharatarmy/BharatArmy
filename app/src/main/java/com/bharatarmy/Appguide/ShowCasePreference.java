package com.bharatarmy.Appguide;

import android.content.Context;
import android.content.SharedPreferences;

public class ShowCasePreference {
    private static final String SHOWCASE_PREFERENCES = "show_case_pref";

    public static boolean hasShown(Context context, String tag){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHOWCASE_PREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(tag, false);
    }

    public static void setShown(Context context, String tag, boolean hasShown){
        SharedPreferences.Editor sharedPreferencesEditor = context.getSharedPreferences(SHOWCASE_PREFERENCES,
                Context.MODE_PRIVATE).edit();
        sharedPreferencesEditor.putBoolean (tag, hasShown);
        sharedPreferencesEditor.apply();
    }
}
