package com.crm.pvt.hapinicrm;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.sun.mail.imap.protocol.INTERNALDATE;

/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class AdminDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private NavigationView navigationView;
    boolean doubleBackToExitPressedOnce = false;
    View hView;
    ImageView profileImage;
    private static final int PICK_IMAGE=1,RESULT_OK=-1;
    Uri imageUri;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            profileImage.setImageURI(imageUri);
        }
    }



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



        //to add image picker in header part
        NavigationView navigationView=(NavigationView) findViewById(R.id.nav_view);
        hView=navigationView.getHeaderView(0);
        profileImage=(ImageView)hView.findViewById(R.id.admin_profile_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);





        if(savedInstanceState==null){
            navigationView.setNavigationItemSelectedListener(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new fragment_admin_dashboard())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        if (doubleBackToExitPressedOnce) {
            this.finishAffinity();
            super.onBackPressed();

            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
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
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                finish();
                break;

            case R.id.nav_copyright:
                //add Home fragment
                Toast.makeText(this, "Hapini.in", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),WebViewActivity.class));
                break;



        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

}