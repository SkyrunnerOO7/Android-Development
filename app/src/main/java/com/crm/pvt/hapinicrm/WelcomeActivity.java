package com.crm.pvt.hapinicrm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class WelcomeActivity extends AppCompatActivity {
    Vibrator vibrator;
    Button WellcomACbtn;
    TextView privacyBtn;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();

        // CALL getInternetStatus() function to check for internet and display error dialog
        if(new InternetDialog(getApplicationContext()).getInternetStatus()){
            //   Toast.makeText(getContext(), "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();
        }

        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        WellcomACbtn = (Button)findViewById(R.id.welcomeAcBtn);
        privacyBtn = (TextView) findViewById(R.id.privacyPolicyBtn);

        WellcomACbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        progressDialog = new ProgressDialog(WelcomeActivity.this);
                        progressDialog.show();
                        progressDialog.setContentView(R.layout.progress_dialog);
                        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


                        if (Build.VERSION.SDK_INT >= 26 ) {
                            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                        }
                        else {
                            vibrator.vibrate(50);
                        }

                        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                        Toast.makeText(WelcomeActivity.this, "WelCome", Toast.LENGTH_SHORT).show();
                        finish();


            }
        });

        WellcomACbtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(WelcomeActivity.this, "Warning: master panel!", Toast.LENGTH_SHORT).show();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(new Intent(WelcomeActivity.this, SplashScreenMasterPanel.class));
                        finish();
                    }
                }, 1000);
                return true;
            }
        });

        privacyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WelcomeActivity.this, "Privacy Policy!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(WelcomeActivity.this, PrivacyPolicyActivity.class));
            }
        });
    }
}