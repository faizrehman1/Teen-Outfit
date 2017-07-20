package com.example.faiz.vividways;

import android.app.Application;
import android.os.SystemClock;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.concurrent.TimeUnit;

/**
 * Created by Faiz on 7/19/2017.
 */

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    //    SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));
    }
}
