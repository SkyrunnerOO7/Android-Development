package com.crm.pvt.hapinicrm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Created by Priyanshu Gupta on June 12

public class BottonSheetAddEmployeeActivity extends BottomSheetDialogFragment {

    private TextInputEditText build_number;
    private Button add;
    private CheckBox checkBox;
    private TextInputEditText email;
    private TextInputEditText pass;
    private TextView terms;

    public BottonSheetAddEmployeeActivity(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_botton_sheet_add_employee, container, false);

        // CALL getInternetStatus() function to check for internet and display error dialog
        if(new InternetDialog(getContext()).getInternetStatus()){
         //   Toast.makeText(getContext(), "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();
        }

        build_number = v.findViewById(R.id.bulid_number);
        email = v.findViewById(R.id.Email);
        pass = v.findViewById(R.id.password);
        checkBox = v.findViewById(R.id.checkbox);

        add = v.findViewById(R.id.add_emp);
        add.setOnClickListener(view -> {
            String Email = email.getText().toString();
            String password = pass.getText().toString();
            String build_num = build_number.getText().toString();

            if(validateEmail(Email) && validatePass(password) && validateBuild(build_num)){
                if(checkBox.isChecked())
                Toast.makeText(getContext(),"Successfully Added",Toast.LENGTH_LONG).show();
                else
                    checkBox.setError("Please Accepts All T&C");
            }

        });

        terms = v.findViewById(R.id.terms);
        terms.setOnClickListener(view -> {
            Toast.makeText(getContext(),"You Clicked on Terms and Conditions",Toast.LENGTH_LONG).show();
        });



        return v;
    }
    private boolean validateBuild(String build_num) {
        if(build_num.isEmpty()){
            build_number.setError("Field can't be Empty");
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