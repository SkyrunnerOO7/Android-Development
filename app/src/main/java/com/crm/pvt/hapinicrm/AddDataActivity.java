package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.HashMap;

/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class AddDataActivity extends AppCompatActivity {

    private TextInputEditText full_name;
    private TextInputEditText phone_number;
    private TextInputEditText city;
    CountryCodePicker countryCodePicker;
    private Button add;
    private CheckBox checkBox;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        countryCodePicker = findViewById(R.id.ccp);
        full_name = findViewById(R.id.full_name);
        phone_number = findViewById(R.id.phone_number);
        checkBox = findViewById(R.id.checkbox);
        city = findViewById(R.id.city);
        loadingBar = new ProgressDialog(this);


        add = findViewById(R.id.add_data);
        add.setOnClickListener(view -> {
            String phoneNumber = phone_number.getText().toString();
            String fullName = full_name.getText().toString();


            String city_st = city.getText().toString();
            countryCodePicker.registerPhoneNumberTextView(phone_number);
            if(validateFullName(fullName) && validatePhoneNumber(phoneNumber) && validatecity(city_st)) {
                if (checkBox.isChecked()) {

                        if (countryCodePicker.isValid())
                            Toast.makeText(getApplicationContext(), "Successfully Submitted", Toast.LENGTH_SHORT).show();

                        if(countryCodePicker.isValid()){
                            createEntry(fullName,phoneNumber,city_st);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Provide valid code/phone number", Toast.LENGTH_SHORT).show();
                }else
                    checkBox.setError("Accepts Conditions");
            }

        });

    }

    private void createEntry(String fullName, String phoneNumber,String city) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Data").child(phoneNumber).exists())){
                    HashMap<String,Object> UserDataMap = new HashMap<>();
                    UserDataMap.put("Name",fullName);
                    UserDataMap.put("Number",phoneNumber);
                    UserDataMap.put("City",city);



                    RootRef.child("Data").child(phoneNumber).updateChildren(UserDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Details has been Added Sucessfully.. ", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                    }else{
                                        loadingBar.dismiss();
                                        Toast.makeText(getApplicationContext(), "Somthing Went Wrong.. Please Try Again After Some time..", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });



                }else{
                    Toast.makeText(getApplicationContext(), "This "+phoneNumber+" number already Exists..", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

    boolean validatecity(String cityS){

        if(cityS.isEmpty()){
            city.setError("Field can't be Empty");
            return false;
        }
        return true;
    }
}