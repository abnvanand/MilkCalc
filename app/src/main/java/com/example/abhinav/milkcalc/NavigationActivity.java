package com.example.abhinav.milkcalc;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abhinav.milkcalc.activities.AddBillActivity;
import com.example.abhinav.milkcalc.activities.AddLogActivity;
import com.example.abhinav.milkcalc.activities.SplashActivity;
import com.example.abhinav.milkcalc.databinding.ActivityNavigationBinding;
import com.example.abhinav.milkcalc.fragments.BillsFragment;
import com.example.abhinav.milkcalc.fragments.LogBookFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FirebaseAuth.AuthStateListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_navigation);
        setSupportActionBar(binding.appBarNavigation.toolbar);
        mFirebaseAuth = FirebaseAuth.getInstance();

        currentBackStackEntry = LogBookFragment.class.getSimpleName();
        binding.appBarNavigation.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentBackStackEntry.equals(BillsFragment.class.getSimpleName())) {
                    startActivity(new Intent(NavigationActivity.this, AddBillActivity.class));
                } else if (currentBackStackEntry.equals(LogBookFragment.class.getSimpleName())) {
                    startActivity(new Intent(NavigationActivity.this, AddLogActivity.class));
                }
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.appBarNavigation.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        binding.navigationView.setNavigationItemSelectedListener(this);

        logBookFragment = new LogBookFragment();
        billsFragment = new BillsFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(binding.appBarNavigation.contentNavigation.fragmentContainer.getId(),
                        logBookFragment, LogBookFragment.class.getSimpleName())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(this);
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
            currentBackStackEntry = LogBookFragment.class.getSimpleName();
            getSupportFragmentManager().beginTransaction()
                    .replace(binding.appBarNavigation.contentNavigation.fragmentContainer.getId(),
                            logBookFragment,
                            LogBookFragment.class.getSimpleName())
                    .commit();
        } else if (id == R.id.nav_bill) {
            currentBackStackEntry = BillsFragment.class.getSimpleName();

            getSupportFragmentManager().beginTransaction()
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

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        } else {
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
        }
    }

    private ActivityNavigationBinding binding;
    private LogBookFragment logBookFragment;
    private BillsFragment billsFragment;
    private String currentBackStackEntry;
    private FirebaseAuth mFirebaseAuth;
}
