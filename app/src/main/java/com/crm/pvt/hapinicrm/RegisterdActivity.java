package com.crm.pvt.hapinicrm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Kuldeep Sahu on 05/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class RegisterdActivity extends AppCompatActivity {
    TextView alreadyAcSignIn;
    Button SignUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerd);

        // CALL getInternetStatus() function to check for internet and display error dialog
        if(new InternetDialog(getApplicationContext()).getInternetStatus()){
            //   Toast.makeText(getContext(), "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();
        }

        alreadyAcSignIn = (TextView)findViewById(R.id.already_have_ac_signIn);
        SignUpBtn = (Button)findViewById(R.id.sign_up_btn);


        alreadyAcSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterdActivity.this, "Please SignIn", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterdActivity.this, LoginActivity.class));
                finish();
            }
        });

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterdActivity.this, "Data Base are not Connect now!", Toast.LENGTH_SHORT).show();
                Toast.makeText(RegisterdActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterdActivity.this, LoginActivity.class));
                finish();
            }
        });

    }
}