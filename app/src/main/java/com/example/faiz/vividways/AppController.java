package com.example.faiz.vividways;

import android.app.Application;
import android.content.Context;
import android.os.SystemClock;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.concurrent.TimeUnit;

/**
 * Created by Faiz on 7/19/2017.
 */

public class AppController extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        context = this.getApplicationContext();
    //    SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));
    }

    public static Context getContext() {
        return context;
    }
}
