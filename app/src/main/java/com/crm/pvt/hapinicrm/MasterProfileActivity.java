package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class MasterProfileActivity extends AppCompatActivity {

    private TextView Email;
    private Button resetCode;
    private Button logout;
    private CircleImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_profile);
        getSupportActionBar().hide();

        Email = findViewById(R.id.email);
        resetCode = findViewById(R.id.reset_button);
        logout = findViewById(R.id.logout_button);
        image = findViewById(R.id.profile_image);

        logout.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),WelcomeActivity.class));
            finish();
        });

        resetCode.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(),"Your Code will be RESET ASAP",Toast.LENGTH_SHORT).show();
        });
    }
}