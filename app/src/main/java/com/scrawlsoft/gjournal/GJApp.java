package com.scrawlsoft.gjournal;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

public class GJApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
