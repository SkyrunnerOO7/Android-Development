package com.crm.pvt.hapinicrm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crm.pvt.hapinicrm.models.DBhelper;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    DBhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        LoginSpinner = findViewById(R.id.spinner_login);
        LoginEmail = findViewById(R.id.editTextEmail_login);
        LoginPassword = findViewById(R.id.editTextPassword_login);
        LoginCheckBox = findViewById(R.id.checkBox_loginAC);
        LoginButton = findViewById(R.id.cirLoginButton_login);
        CreateAdminAccount = findViewById(R.id.I_am_admin_login);

        db = new DBhelper(this);
        String StrLoginEmail, StrLoginPassword;
        StrLoginEmail = (String) LoginEmail.getText().toString();
        StrLoginPassword = (String) LoginPassword.getText().toString();





        // CALL getInternetStatus() function to check for internet and display error dialog
        if (new InternetDialog(getApplicationContext()).getInternetStatus()) {
            //   Toast.makeText(getContext(), "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();
        }

        final String[] choose_category = new String[1];
        LoginSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choose_category[0] = LoginSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String StrLoginEmail = LoginEmail.getText().toString();
                String StrLoginPassword = LoginPassword.getText().toString();

                // For Admin
                if (validateEmail(StrLoginEmail) && validatePass(StrLoginPassword) && choose_category[0].contentEquals("Admin")) {
                    if(db.checkAdminpasword(StrLoginEmail,StrLoginPassword)){
                        if (LoginCheckBox.isChecked()) {
                            startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
                        } else
                            Toast.makeText(LoginActivity.this, "Something wrong ", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    LoginCheckBox.setError("Please check the box");
                }

                // For Employee
                if (validateEmail(StrLoginEmail) && validatePass(StrLoginPassword) && choose_category[0].contentEquals("Employee")) {
                    if(db.checkEmpoyeepasword(StrLoginEmail,StrLoginPassword)){
                        if (LoginCheckBox.isChecked()) {
                            startActivity(new Intent(LoginActivity.this, Error404Activity.class));
                        } else
                            Toast.makeText(LoginActivity.this, "Something wrong ", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    LoginCheckBox.setError("Please check the box");
                }

                // For Developers
                if (validateEmail(StrLoginEmail) && validatePass(StrLoginPassword) && choose_category[0].contentEquals("Developers")) {
                    if(db.checkEmpoyeepasword(StrLoginEmail,StrLoginPassword)){
                        if (LoginCheckBox.isChecked()) {
                            startActivity(new Intent(LoginActivity.this, DeveloperActivity.class));
                        } else
                            Toast.makeText(LoginActivity.this, "Something wrong ", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    LoginCheckBox.setError("Please check the box");
                }
            }
        });

        CreateAdminAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Only Admin Create this Account!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, RegisterdActivity.class));
                finish();
            }
        });

    }

    private boolean validatePass(String password) {



        if (password.isEmpty()) {
            LoginPassword.setError("Field can't be Empty");
            return false;
        }
        String[] regex = {".*\\d+.*"
                , ".*[a-z].*", ".*[A-Z].*"
                , ".*[@#$%^&+=].*"
                , ".*/\\s/.*", ".{8,20}.*"};
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(regex[0]);
        matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            LoginPassword.setError("Password should contain at least one digit");
            return false;
        }
        pattern = Pattern.compile(regex[1]);
        matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            LoginPassword.setError("Password should contain at least one lower case alphabet");
            return false;
        }
        pattern = Pattern.compile(regex[2]);
        matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            LoginPassword.setError("Password should contain at least one upper case alphabet");
            return false;
        }
        pattern = Pattern.compile(regex[3]);
        matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            LoginPassword.setError("Password should contain at least one special character");
            return false;
        }
        pattern = Pattern.compile(regex[4]);
        matcher = pattern.matcher(password);
        if (matcher.matches()) {
            LoginPassword.setError("White spaces are not allowed");
            return false;
        }
        pattern = Pattern.compile(regex[5]);
        matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            LoginPassword.setError("Password should contain 8 to 20 characters only");
            return false;
        }
        return true;
    }

    private boolean validateEmail(String Email) {
        if (Email.isEmpty()) {
            LoginEmail.setError("Field can't be Empty");
            return false;
        }
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Email);
        if (!matcher.matches()) {
            LoginEmail.setError("Please Provide a valid E-mail");
            return false;
        }
        return true;
    }
}