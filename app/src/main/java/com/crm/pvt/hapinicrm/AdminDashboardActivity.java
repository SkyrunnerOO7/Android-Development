package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.provider.Telephony;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class AdminDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        setTitle(null);

        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.nav_view);

        Toolbar toolbar = findViewById(R.id.Menu_toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new fragment_admin_dashboard())
                .commit();

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                //add Home fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new fragment_admin_dashboard())
                        .commit();
                break;

            case R.id.nav_profile:
                //add Home fragment
                Toast.makeText(this, "Under Construction!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_Feedback:
                //add Home fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new fragment_feedback())
                        .commit();
                break;

            case R.id.nav_Rate:
                //add Home fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new fragment_rate_us())
                        .commit();
                break;

            case R.id.nav_signout:
                //add Home fragment
                Toast.makeText(this, "Sign Out|under construction", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}