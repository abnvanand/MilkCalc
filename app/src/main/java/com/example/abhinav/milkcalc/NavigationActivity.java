package com.example.abhinav.milkcalc;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhinav.milkcalc.databinding.ActivityNavigationBinding;
import com.example.abhinav.milkcalc.fragments.BillsFragment;
import com.example.abhinav.milkcalc.fragments.LogBookFragment;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ui.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import timber.log.Timber;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_navigation);
        setSupportActionBar(binding.appBarNavigation.toolbar);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    LinearLayout linearLayout = (LinearLayout)
                            binding.navigationView
                                    .getHeaderView(binding.navigationView.getHeaderCount() - 1);

                    // TODO: find a way to use data binding somehow
                    TextView textViewUserName = (TextView) linearLayout.findViewById(R.id.userName);
                    TextView textViewEmailId = (TextView) linearLayout.findViewById(R.id.emailId);
                    ImageView imageViewProfilePicture = (ImageView) linearLayout.findViewById(R.id.profilePicture);

                    Timber.d("Profile pic uri %s", user.getPhotoUrl());
                    textViewUserName.setText(user.getDisplayName());
                    textViewEmailId.setText(user.getEmail());

                    // TODO: http://stackoverflow.com/questions/23978828/how-do-i-use-disk-caching-in-picasso
                    Picasso.with(NavigationActivity.this)
                            .load(user.getPhotoUrl())
                            .into(imageViewProfilePicture);

                } else {
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
        };
        binding.appBarNavigation.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.appBarNavigation.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        binding.navigationView.setNavigationItemSelectedListener(this);

        logBookFragment = new LogBookFragment();
        billsFragment = new BillsFragment();

        getFragmentManager().beginTransaction()
                .replace(binding.appBarNavigation.contentNavigation.fragmentContainer.getId(), logBookFragment, LogBookFragment.class.getSimpleName())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
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
                Toast.makeText(this, "Offline", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_log_book) {
            getFragmentManager().beginTransaction()
                    .replace(binding.appBarNavigation.contentNavigation.fragmentContainer.getId(),
                            logBookFragment,
                            LogBookFragment.class.getSimpleName())
                    .commit();
        } else if (id == R.id.nav_bill) {
            getFragmentManager().beginTransaction()
                    .replace(binding.appBarNavigation.contentNavigation.fragmentContainer.getId(),
                            billsFragment,
                            BillsFragment.class.getSimpleName())
                    .commit();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_logout) {
            AuthUI.getInstance().signOut(this);
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private ActivityNavigationBinding binding;
    private LogBookFragment logBookFragment;
    private BillsFragment billsFragment;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
}
