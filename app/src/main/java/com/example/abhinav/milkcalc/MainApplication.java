package com.example.abhinav.milkcalc;

import android.app.Application;
import android.os.StrictMode;

import com.google.firebase.database.FirebaseDatabase;

import timber.log.Timber;


public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        if (BuildConfig.DEBUG) {

            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyFlashScreen()
                    .build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());

            Timber.plant(new Timber.DebugTree());
        }
    }
}
