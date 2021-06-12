package com.crm.pvt.hapinicrm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kuldeep Sahu on 05/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class LoginActivity extends AppCompatActivity {
    Spinner LoginSpinner;
    EditText LoginEmail, LoginPassword;
    CheckBox LoginCheckBox;
    Button LoginButton;
    TextView CreateAdminAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginSpinner = (Spinner)findViewById(R.id.spinner_login);
        LoginEmail = (EditText) findViewById(R.id.editTextEmail_login);
        LoginPassword = (EditText) findViewById(R.id.editTextPassword_login);
        LoginCheckBox = (CheckBox) findViewById(R.id.checkBox_loginAC);
        LoginButton = (Button) findViewById(R.id.cirLoginButton_login);
        CreateAdminAccount = (TextView) findViewById(R.id.I_am_admin_login);

        String StrLoginEmail, StrLoginPassword;
        StrLoginEmail = (String)LoginEmail.getText().toString();
        StrLoginPassword = (String)LoginPassword.getText().toString();

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, DeveloperActivity.class));
            }
        });

    }
}