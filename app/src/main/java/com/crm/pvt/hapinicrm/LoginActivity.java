package com.crm.pvt.hapinicrm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.crm.pvt.hapinicrm.models.Admin;
import com.crm.pvt.hapinicrm.models.DBhelper;
import com.crm.pvt.hapinicrm.models.Developer;
import com.crm.pvt.hapinicrm.models.Employee;
import com.crm.pvt.hapinicrm.prevalent.prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private String parentDBname = "Admin";
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        LoginSpinner = findViewById(R.id.spinner_login);
        LoginEmail = findViewById(R.id.editText_login);
        LoginPassword = findViewById(R.id.editTextPassword_login);
        LoginCheckBox = findViewById(R.id.checkBox_loginAC);
        LoginButton = findViewById(R.id.cirLoginButton_login);
        CreateAdminAccount = findViewById(R.id.I_am_admin_login);
        loadingBar = new ProgressDialog(this);






        // CALL getInternetStatus() function to check for internet and display error dialog
        if (new InternetDialog(getApplicationContext()).getInternetStatus()) {
            //   Toast.makeText(getContext(), "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();
        }

        final String[] choose_category = new String[1];
        LoginSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choose_category[0] = LoginSpinner.getSelectedItem().toString();
                if(choose_category[0].contentEquals("Admin")){
                    LoginEmail.setHint("Enter passcode");
                    parentDBname = "Admin";
                }else if(choose_category[0].contentEquals("Employee")){
                    LoginEmail.setHint("Enter IMEI");
                    parentDBname = "Employee";
                }else if(choose_category[0].contentEquals("Developers")){
                    LoginEmail.setHint("Enter Number");
                    parentDBname = "Developer";
                }else{
                    Toast.makeText(LoginActivity.this, "please select on feild", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String Email = LoginEmail.getText().toString();
                String Password = LoginPassword.getText().toString();
                // For Admin
                if (choose_category[0].contentEquals("Admin")){
                    if(TextUtils.isEmpty(Email)){
                        Toast.makeText(getApplicationContext(), "Please Enter Your passcode...", Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Password)){
                        Toast.makeText(getApplicationContext(), "Please Enter a Password...", Toast.LENGTH_SHORT).show();
                    }else{
                        loadingBar.setTitle("Login Account");
                        loadingBar.setMessage("please Wait while checking Credentials..");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();
                        AllowAccessToAccount(Email,Password);
                    }
                }else{
                    LoginCheckBox.setError("Please check the box");
                }

                // For Employee
               if (choose_category[0].contentEquals("Employee")) {
                     if(TextUtils.isEmpty(Email)){
                       Toast.makeText(getApplicationContext(), "Please Enter Your IMEI number...", Toast.LENGTH_SHORT).show();
                   }
                   else if(TextUtils.isEmpty(Password)){
                       Toast.makeText(getApplicationContext(), "Please Enter a Password...", Toast.LENGTH_SHORT).show();
                   }else{
                       loadingBar.setTitle("Login Account");
                       loadingBar.setMessage("please Wait while checking Credentials..");
                       loadingBar.setCanceledOnTouchOutside(false);
                       loadingBar.show();
                       AllowAccessToEmployee(Email,Password);
                   }
                }else{
                    LoginCheckBox.setError("Please check the box");
                }

                // For Developers
                if (choose_category[0].contentEquals("Developers") && LoginCheckBox.isChecked()) {
                    if(TextUtils.isEmpty(Email)){
                        Toast.makeText(getApplicationContext(), "Please Enter Your Devloper number...", Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Password)){
                        Toast.makeText(getApplicationContext(), "Please Enter a Password...", Toast.LENGTH_SHORT).show();
                    }else{
                        loadingBar.setTitle("Login Account");
                        loadingBar.setMessage("please Wait while checking Credentials..");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();
                        AllowAccessToDeveloper(Email,Password);
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


    public void AllowAccessToAccount(final String Passcode,final String Password){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDBname).child(Passcode).exists()){
                    Admin Admindata = dataSnapshot.child(parentDBname).child(Passcode).getValue(Admin.class);
                    if (Admindata.getPasscode().equals(Passcode)){
                        if(Admindata.getPassword().equals(Password)){
                            if(parentDBname.equals("Admin")){
                                Toast.makeText(getApplicationContext(), "Welcome Admin You are Logged In Successfully... ", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(getApplicationContext(),AdminDashboardActivity.class);
                                startActivity(intent);
                            }
                        }else{
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(), "Incorrect Password..", Toast.LENGTH_SHORT).show();

                        }
                    }


                }else{
                    Toast.makeText(getApplicationContext(), "Invalid Credentials..PLease Try again with another Phone Number", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void AllowAccessToEmployee(final String IMEI,final String Password){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDBname).child(IMEI).exists()){
                    Employee EmployeeData = dataSnapshot.child(parentDBname).child(IMEI).getValue(Employee.class);
                    if (EmployeeData.getIMEI().equals(IMEI)){
                        if(EmployeeData.getPassword().equals(Password)){
                            if(parentDBname.equals("Employee")){
                                Toast.makeText(getApplicationContext(), "Welcome Employee You are Logged In Successfully... ", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(getApplicationContext(),Error404Activity.class);
                                startActivity(intent);
                            }
                        }else{
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(), "Incorrect Password..", Toast.LENGTH_SHORT).show();

                        }
                    }


                }else{
                    Toast.makeText(getApplicationContext(), "Invalid Credentials..PLease Try again with another Phone Number", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void AllowAccessToDeveloper(final String number,final String Password){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDBname).child(number).exists()){
                    Developer DeveloperData = dataSnapshot.child(parentDBname).child(number).getValue(Developer.class);
                    if (DeveloperData.getNumber().equals(number)){
                        if(DeveloperData.getPassword().equals(Password)){
                            if(parentDBname.equals("Developer")){
                                Toast.makeText(getApplicationContext(), "Welcome Developer You are Logged In Successfully... ", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(getApplicationContext(),DeveloperActivity.class);
                                startActivity(intent);
                            }
                        }else{
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(), "Incorrect Password..", Toast.LENGTH_SHORT).show();

                        }
                    }


                }else{
                    Toast.makeText(getApplicationContext(), "Invalid Credentials..PLease Try again with another Phone Number", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private boolean validatePass(String password) {
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