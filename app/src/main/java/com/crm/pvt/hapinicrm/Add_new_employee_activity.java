package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.DBhelper;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Add_new_employee_activity extends AppCompatActivity {

    private TextInputEditText build_number;
    private Button add;
    private CheckBox checkBox;
    private TextInputEditText email;
    private TextInputEditText pass;
    private TextInputEditText conf_pass;
    DBhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_employee);

        // CALL getInternetStatus() function to check for internet and display error dialog
        if(new InternetDialog(getApplicationContext()).getInternetStatus()){
            //   Toast.makeText(getContext(), "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();
        }

        build_number = findViewById(R.id.bulid_number);
        email = findViewById(R.id.Email);
        pass = findViewById(R.id.password);
        checkBox = findViewById(R.id.checkbox);
        conf_pass = findViewById(R.id.confirm_password);

        add = findViewById(R.id.add_emp);
      /*  add.setOnClickListener(view -> {
            String Email = email.getText().toString();
            String password = pass.getText().toString();
            String build_num = build_number.getText().toString();
            String confirm_pass = conf_pass.getText().toString();

            if(validateBuild(build_num) && validateEmail(Email) && validatePass(password)){
                if(checkBox.isChecked()) {
                    if (password.equals(confirm_pass)) {

                        Boolean checkadmin = db.checkEmployeeEmail(Email);
                        if (checkadmin == false) {
                            Boolean insert = db.insertdataEmpoyee(build_num, Email, password);
                            if (insert == true) {
                                Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            } else {
                                Toast.makeText(this, "Registration Failed please try again", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Admin already exists", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        conf_pass.setError("Password don't matches");
                    }
                }
                else
                    checkBox.setError("Please Accepts All T&C");
            }

        });*/



    }

    private boolean validateBuild(String build_num) {
        if(build_num.isEmpty()){
            build_number.setError("Field can't be Empty");
            return false;
        }

        if(build_num.length() != 15){
            build_number.setError("Wrong IMEI Number");
            return false;
        }
        return true;
    }

    private boolean validatePass(String password) {
        if(password.isEmpty()){
            pass.setError("Field can't be Empty");
            return false;
        }
        String[] regex = {".*\\d+.*"
                , ".*[a-z].*",".*[A-Z].*"
                , ".*[@#$%^&+=].*"
                , ".*/\\s/.*",".{8,20}.*"};
        Pattern pattern;
        Matcher matcher;
        pattern= Pattern.compile(regex[0]);
        matcher = pattern.matcher(password);
        if(!matcher.matches()){
            pass.setError("Password should contain at least one digit");
            return false;
        }
        pattern= Pattern.compile(regex[1]);
        matcher = pattern.matcher(password);
        if(!matcher.matches()){
            pass.setError("Password should contain at least one lower case alphabet");
            return false;
        }
        pattern= Pattern.compile(regex[2]);
        matcher = pattern.matcher(password);
        if(!matcher.matches()){
            pass.setError("Password should contain at least one upper case alphabet");
            return false;
        }
        pattern= Pattern.compile(regex[3]);
        matcher = pattern.matcher(password);
        if(!matcher.matches()){
            pass.setError("Password should contain at least one special character");
            return false;
        }
        pattern= Pattern.compile(regex[4]);
        matcher = pattern.matcher(password);
        if(matcher.matches()){
            pass.setError("White spaces are not allowed");
            return false;
        }
        pattern= Pattern.compile(regex[5]);
        matcher = pattern.matcher(password);
        if(!matcher.matches()){
            pass.setError("Password should contain 8 to 20 characters only");
            return false;
        }



        return true;
    }

    private boolean validateEmail(String Email) {
        if(Email.isEmpty()){
            email.setError("Field can't be Empty");
            return false;
        }
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Email);
        if(!matcher.matches()){
            email.setError("Please Provide a valid E-mail");
            return false;
        }

        return true;
    }
}