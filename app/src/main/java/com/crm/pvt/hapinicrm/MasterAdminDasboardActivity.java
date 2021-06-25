package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class MasterAdminDasboardActivity extends AppCompatActivity {
    LinearLayout addadmin;
    LinearLayout active_user;
    LinearLayout AddData;
    LinearLayout profile;
    LinearLayout Feedback;
    LinearLayout ActiveData;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_admin_dasboard);
        getSupportActionBar().hide();

        addadmin = findViewById(R.id.add_admin);
        addadmin.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),addAdminActivity.class)));

        active_user = findViewById(R.id.active_user);
        active_user.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),ActiveUserActivity.class));
        });

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),MasterProfileActivity.class));
        });

        AddData = findViewById(R.id.add_data);
        AddData.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),AddDataActivity.class));
        });
        Feedback = findViewById(R.id.feedback_layout);
        Feedback.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),UserFeedbackShowActivity.class));
        });
        ActiveData = findViewById(R.id.ActiveData_layout);
        ActiveData.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),ActiveDataActivity.class));
        });

    }

    @Override
    public void onBackPressed() {
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
}