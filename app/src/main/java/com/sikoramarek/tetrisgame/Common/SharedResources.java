package com.sikoramarek.tetrisgame.Common;

import android.content.SharedPreferences;

public class SharedResources {

    private static SharedPreferences sharedPreferences;

    public SharedResources(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
