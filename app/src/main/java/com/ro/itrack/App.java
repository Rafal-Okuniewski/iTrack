package com.ro.itrack;

import android.app.Application;


import com.kontakt.sdk.android.common.KontaktSDK;
import com.ro.itrack.util.Constants;

public class App extends Application {

    private static final String API_KEY = Constants.API_KEY;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDependencies();
    }

    private void initializeDependencies() {
        KontaktSDK.initialize(API_KEY);
    }

}