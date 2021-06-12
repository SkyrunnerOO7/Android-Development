package com.crm.pvt.hapinicrm;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class WelcomeActivity extends AppCompatActivity {
    Vibrator vibrator;
    Button WellcomACbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        WellcomACbtn = (Button)findViewById(R.id.welcomeAcBtn);

        WellcomACbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(WelcomeActivity.this, "WelCome", Toast.LENGTH_SHORT).show();

                if (Build.VERSION.SDK_INT >= 26 ) {
                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                else {
                    vibrator.vibrate(50);
                }

                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}