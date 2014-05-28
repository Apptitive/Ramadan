package com.appsomehow.ramadan.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Sharif on 5/28/2014.
 */
public class PreferenceHelper {
    private SharedPreferences sharedPreferences;

    public PreferenceHelper(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }
}
