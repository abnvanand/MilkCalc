package com.example.abhinav.milkcalc;

import android.app.FragmentManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.abhinav.milkcalc.databinding.ActivityNavigationBinding;
import com.example.abhinav.milkcalc.fragments.BillsFragment;
import com.example.abhinav.milkcalc.fragments.LogBookFragment;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityNavigationBinding binding;
    private LogBookFragment logBookFragment;
    private BillsFragment billsFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_navigation);
        setSupportActionBar(binding.appBarNavigation.toolbar);

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
        fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction()
                .replace(binding.appBarNavigation.contentNavigation.fragmentContainer.getId(), logBookFragment, "logBookFragment")
                .commit();
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
            fragmentManager.beginTransaction()
                    .replace(binding.appBarNavigation.contentNavigation.fragmentContainer.getId(),
                            logBookFragment,
                            LogBookFragment.class.getSimpleName())
                    .commit();
        } else if (id == R.id.nav_bill) {
            fragmentManager.beginTransaction()
                    .replace(binding.appBarNavigation.contentNavigation.fragmentContainer.getId(),
                            billsFragment,
                            BillsFragment.class.getSimpleName())
                    .commit();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_logout) {

        }

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
