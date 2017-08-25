package com.example.faiz.vividways.Utils;

import android.app.Application;
import android.content.Context;
import android.os.SystemClock;
import android.support.multidex.MultiDex;

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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }
}
