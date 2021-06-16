package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;
/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class AddDataActivity extends AppCompatActivity {

    private TextInputEditText full_name;
    private TextInputEditText phone_number;
    CountryCodePicker countryCodePicker;
    private Button add;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        countryCodePicker = findViewById(R.id.ccp);
        full_name = findViewById(R.id.full_name);
        phone_number = findViewById(R.id.phone_number);
        checkBox = findViewById(R.id.checkbox);

        add = findViewById(R.id.add_data);
        add.setOnClickListener(view -> {
            String phoneNumber = phone_number.getText().toString();
            String fullName = full_name.getText().toString();
            countryCodePicker.registerPhoneNumberTextView(phone_number);
            if(validateFullName(fullName) && validatePhoneNumber(phoneNumber)) {
                if (checkBox.isChecked()) {
                        if(countryCodePicker.isValid())
                        Toast.makeText(getApplicationContext(), "Successfully Submitted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Provide valid code/phone number", Toast.LENGTH_SHORT).show();

                }else
                    checkBox.setError("Accepts Conditions");
            }

        });

    }

    boolean validatePhoneNumber(String phone){

        if(phone.isEmpty()){
            phone_number.setError("Field can't be Empty");
            return false;
        }

        if(phone.length() != 10){
            phone_number.setError("Provide a valid Phone Number");
            return false;
        }
        return true;
    }
    boolean validateFullName(String name){

        if(name.isEmpty()){
            full_name.setError("Field can't be Empty");
            return false;
        }
        return true;
    }
}