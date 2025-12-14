package com.midApp.myapp;

import android.app.Application;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

public class Cloudy extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", "dtaymi3n9");
        config.put("api_key", "349416381395239");
        config.put("api_secret", "4pqekYaTxWPEHH4yeTw6osAJu8U");

        MediaManager.init(this, config);
    }
}
