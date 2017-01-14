package com.example.abhinav.milkcalc.activities;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import timber.log.Timber;

public class BaseActivity extends AppCompatActivity {
    protected boolean isNetworkConnected;

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiverConnectivityChange,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            // remove the receiver in here,
            // standard practice :)
            unregisterReceiver(receiverConnectivityChange);
        } catch (IllegalArgumentException e) {
            Timber.e(e, e.getLocalizedMessage());
        }
    }

    protected void onNetworkStateConnected(@NonNull NetworkInfo networkInfo) {
        Timber.d("onNetworkStateConnected: %s", networkInfo);
        isNetworkConnected = true;
    }

    protected void onNetworkStateDisconnected() {
        Timber.d("onNetworkStateDisconnected");
        isNetworkConnected = false;
    }


    /**
     * Receiver that monitors network connection.
     */
    private BroadcastReceiver receiverConnectivityChange = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null) {
                // no need to trigger network info hooks
                Timber.i("networkInfo not available");
                if (isNetworkConnected) onNetworkStateDisconnected();
                return;
            }

            if (networkInfo.isConnected()) {
                if (!isNetworkConnected) onNetworkStateConnected(networkInfo);
            } else {
                if (isNetworkConnected) onNetworkStateDisconnected();
            }
        }
    };
}
