package com.example.abhinav.milkcalc.activities;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.abhinav.milkcalc.NavigationActivity;
import com.example.abhinav.milkcalc.R;
import com.example.abhinav.milkcalc.databinding.ActivitySplashBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ui.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class SplashActivity extends BaseActivity implements FirebaseAuth.AuthStateListener {
    public static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        mFirebaseAuth = FirebaseAuth.getInstance();

        binding.btnLogin.setOnClickListener(onClickBtnLogin);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mFirebaseAuth != null)
            mFirebaseAuth.removeAuthStateListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Already signed in", Toast.LENGTH_SHORT).show();
                // Do whatever you want
            } else if (resultCode == RESULT_CANCELED) {
                // Allow app to close on back press from login screen
                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show();
                finish();
            } else if (resultCode == ResultCodes.RESULT_NO_NETWORK) {
                switchToOfflineMode();
                Toast.makeText(this, "Offline", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onNetworkStateConnected(@NonNull NetworkInfo networkInfo) {
        super.onNetworkStateConnected(networkInfo);
        binding.btnLogin.setVisibility(View.VISIBLE);
        binding.linearLayoutOffline.setVisibility(View.GONE);

        if (!isOnline)
            switchToOnlineMode();
    }

    @Override
    protected void onNetworkStateDisconnected() {
        super.onNetworkStateDisconnected();
        binding.btnLogin.setVisibility(View.GONE);
        binding.linearLayoutOffline.setVisibility(View.VISIBLE);
        switchToOfflineMode();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            startActivity(new Intent(SplashActivity.this, NavigationActivity.class));
            finish();
        } else if (isOnline) {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                            .build(),
                    RC_SIGN_IN);
        }
    }

    private void switchToOnlineMode() {
        isOnline = true;
        onAuthStateChanged(mFirebaseAuth);
    }

    private void switchToOfflineMode() {
        isOnline = false;
    }

    private boolean isOnline = true;
    private ActivitySplashBinding binding;
    private FirebaseAuth mFirebaseAuth;
    private View.OnClickListener onClickBtnLogin =
            v -> onAuthStateChanged(mFirebaseAuth);
}
