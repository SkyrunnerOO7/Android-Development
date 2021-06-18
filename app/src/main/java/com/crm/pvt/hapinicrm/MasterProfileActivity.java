package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MasterProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_profile);
        getSupportActionBar().hide();
    }
}