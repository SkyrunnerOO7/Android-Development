package com.crm.pvt.hapinicrm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class DeveloperActivity extends AppCompatActivity {
    TextView One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        One = (TextView)findViewById(R.id.one);
        Two = (TextView)findViewById(R.id.two);
        Three = (TextView)findViewById(R.id.three);
        Four = (TextView)findViewById(R.id.four);
        Five = (TextView)findViewById(R.id.five);
        Six = (TextView)findViewById(R.id.six);
        Seven = (TextView)findViewById(R.id.seven);
        Eight = (TextView)findViewById(R.id.eight);
        Nine = (TextView)findViewById(R.id.nine);

        // CALL getInternetStatus() function to check for internet and display error dialog
        if(new InternetDialog(getApplicationContext()).getInternetStatus()){
            //   Toast.makeText(getContext(), "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();
        }

        One.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeveloperActivity.this, MainActivity.class));
            }
        });

        Two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeveloperActivity.this, WelcomeActivity.class));
            }
        });

        Three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeveloperActivity.this, LoginActivity.class));
            }
        });

        Four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeveloperActivity.this, RegisterdActivity.class));
            }
        });

        Five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeveloperActivity.this, Error404Activity.class));
            }
        });

        Six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeveloperActivity.this, InternetDialogActivity.class));
            }
        });

        Seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeveloperActivity.this, WebViewActivity.class));
            }
        });

        Eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeveloperActivity.this, SupportActivity.class));
            }
        });

        Nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeveloperActivity.this, fragment_feedback.class));
            }
        });

    }
}