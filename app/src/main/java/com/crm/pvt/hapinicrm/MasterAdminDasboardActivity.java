package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    LinearLayout construction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_admin_dasboard);

        addadmin = findViewById(R.id.add_admin);
        addadmin.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),addAdminActivity.class)));

        active_user = findViewById(R.id.active_user);
        active_user.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(),"You Clicked On Active User",Toast.LENGTH_SHORT).show();
        });

        construction = findViewById(R.id.construction);
        construction.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(),"You Clicked On Contruction",Toast.LENGTH_SHORT).show();
        });

        AddData = findViewById(R.id.add_data);
        AddData.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),AddDataActivity.class));
        });

    }
}