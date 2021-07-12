package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.Candidate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.crm.pvt.hapinicrm.R.drawable.active_user_icon;
import static com.crm.pvt.hapinicrm.R.drawable.add_data_icon;
import static com.crm.pvt.hapinicrm.R.drawable.user_icon_blue;

/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class AddDataActivity extends AppCompatActivity {

    private TextInputEditText full_name;
    private TextInputEditText phone_number;
    private TextInputEditText city;
    private TextInputEditText area;
    private TextInputEditText organization;
    private TextInputEditText experience;
    private TextInputEditText password;
    private TextInputEditText service;
    private TextInputEditText subService,mail;
    public String type,check_spinner;

    Spinner dataSpinner;

    CountryCodePicker countryCodePicker;
    private Button add;
    private CheckBox checkBox;
    ProgressDialog loadingBar;
    //Switch Switch;
    SwitchCompat Switch;
    private String currentDate;
    private String currentTime,Time;
    Dialog dialog;
    private TextView errorText,errorHeading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        countryCodePicker = findViewById(R.id.ccp);
        checkBox = findViewById(R.id.checkbox);
        full_name = findViewById(R.id.full_name);
        phone_number = findViewById(R.id.phone_number);
        city = findViewById(R.id.city);
        area = findViewById(R.id.Area);
        organization = findViewById(R.id.Organization);
        experience = findViewById(R.id.Experience);
        password = findViewById(R.id.Password);
        service = findViewById(R.id.Service);
        subService = findViewById(R.id.SubService);
        mail = findViewById(R.id.Mail);

        dataSpinner=findViewById(R.id.spinner_Doc_data);

        check_spinner="false";

        currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm aa", Locale.getDefault()).format(new Date());

        Time=currentDate+" @"+currentTime;



        dialog=new Dialog(AddDataActivity.this);
        dialog.setContentView(R.layout.layout_error404);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        errorText=dialog.findViewById(R.id.errortextoferrorAc);
        errorHeading=dialog.findViewById(R.id.homeHeading);

        Button CloseDialog=dialog.findViewById(R.id.CloseBtnErrorAC);
        CloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                errorHeading.setText("Error 404");
                errorText.setText("Something went wrong!");


            }
        });


        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddDataActivity.this);
        builder1.setMessage("Please first select the option from- choose here, to get the form ");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();



        final String[] choose_category = new String[1];
        dataSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                choose_category[0] = dataSpinner.getSelectedItem().toString();
                if(choose_category[0].contentEquals("candidate")){


                    full_name.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    phone_number.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    city.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    area.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    password.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    mail.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    organization.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    experience.setHintTextColor(getResources().getColor(R.color.navy_blue));

                    subService.setHintTextColor(getResources().getColor(R.color.md_blue_grey_400));
                    service.setHintTextColor(getResources().getColor(R.color.md_blue_grey_400));


                    full_name.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    phone_number.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    city.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    area.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    password.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    mail.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    organization.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    experience.setHintTextColor(getResources().getColor(R.color.navy_blue));

                    full_name.setVisibility(View.VISIBLE);
                    phone_number.setVisibility(View.VISIBLE);
                    city.setVisibility(View.VISIBLE);
                    area.setVisibility(View.VISIBLE);
                    password.setVisibility(View.INVISIBLE);
                    mail.setVisibility(View.VISIBLE);
                    organization.setVisibility(View.VISIBLE);
                    experience.setVisibility(View.VISIBLE);
                    subService.setVisibility(View.INVISIBLE);
                    service.setVisibility(View.INVISIBLE);


                    full_name.setEnabled(true);
                    phone_number.setEnabled(true);
                    city.setEnabled(true);
                    area.setEnabled(true);
                    password.setEnabled(true);
                    mail.setEnabled(true);
                    organization.setEnabled(true);
                    experience.setEnabled(true);




                    full_name.setFocusable(true);
                    phone_number.setFocusable(true);
                    city.setFocusable(true);
                    area.setFocusable(true);
                    password.setFocusable(true);
                    mail.setFocusable(true);
                    organization.setFocusable(true);
                    experience.setFocusable(true);


                    full_name.setHint("Enter FullName");
                    phone_number.setHint("Enter Phone Number");
                    city.setHint("Enter City");

                    experience.setHint("Enter Experience");
                    password.setHint("Enter Password");

                    mail.setHint("Enter Mail");
                    area.setHint("Enter Area");

                    organization.setHint("Enter Qualification");

                    service.setHint("Not Required for Candidate");
                    subService.setHint("Not Required for Candidate");

                    subService.setEnabled(false);
                    subService.setFocusable(false);

                    service.setEnabled(false);
                    service.setFocusable(false);

                    type="Candidate";
                    check_spinner="true";
                }
                else if(choose_category[0].contentEquals("vendors")){
                    type="Vendors";
                    check_spinner="true";
                    full_name.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    phone_number.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    city.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    area.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    password.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    mail.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    organization.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    experience.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    subService.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    service.setHintTextColor(getResources().getColor(R.color.navy_blue));



                    full_name.setVisibility(View.VISIBLE);
                    phone_number.setVisibility(View.VISIBLE);
                    city.setVisibility(View.VISIBLE);
                    area.setVisibility(View.VISIBLE);
                    password.setVisibility(View.INVISIBLE);
                    mail.setVisibility(View.VISIBLE);
                    organization.setVisibility(View.VISIBLE);
                    experience.setVisibility(View.VISIBLE);
                    subService.setVisibility(View.VISIBLE);
                    service.setVisibility(View.VISIBLE);





                    full_name.setEnabled(true);
                    phone_number.setEnabled(true);
                    city.setEnabled(true);
                    area.setEnabled(true);
                    password.setEnabled(true);
                    mail.setEnabled(true);
                    organization.setEnabled(true);
                    experience.setEnabled(true);
                    subService.setEnabled(true);
                    service.setEnabled(true);

                    full_name.setFocusable(true);
                    phone_number.setFocusable(true);
                    city.setFocusable(true);
                    area.setFocusable(true);
                    password.setFocusable(true);
                    mail.setFocusable(true);
                    organization.setFocusable(true);
                    experience.setFocusable(true);
                    subService.setFocusable(true);
                    service.setFocusable(true);


                    full_name.setHint("Enter FullName");
                    phone_number.setHint("Enter Phone Number");
                    city.setHint("Enter City");
                    organization.setHint("Enter Organization");
                    experience.setHint("Enter Experience");
                    password.setHint("Enter Password");
                    service.setHint("Enter Service");
                    subService.setHint("Enter SubService");
                    mail.setHint("Enter Mail");
                    area.setHint("Enter Area");





                }
                else if(choose_category[0].contentEquals("customerb2b")){

                    type="CustomerB2B";
                    check_spinner="true";


                    full_name.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    phone_number.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    city.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    organization.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    password.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    mail.setHintTextColor(getResources().getColor(R.color.navy_blue));


                    area.setHintTextColor(getResources().getColor(R.color.md_blue_grey_400));
                    experience.setHintTextColor(getResources().getColor(R.color.md_blue_grey_400));
                    subService.setHintTextColor(getResources().getColor(R.color.md_blue_grey_400));
                    service.setHintTextColor(getResources().getColor(R.color.md_blue_grey_400));


                    full_name.setVisibility(View.VISIBLE);
                    phone_number.setVisibility(View.VISIBLE);
                    city.setVisibility(View.VISIBLE);
                    area.setVisibility(View.INVISIBLE);
                    password.setVisibility(View.INVISIBLE);
                    mail.setVisibility(View.VISIBLE);
                    organization.setVisibility(View.VISIBLE);
                    experience.setVisibility(View.INVISIBLE);
                    subService.setVisibility(View.INVISIBLE);
                    service.setVisibility(View.INVISIBLE);


                    full_name.setFocusable(true);
                    phone_number.setFocusable(true);
                    city.setFocusable(true);
                    password.setFocusable(true);
                    mail.setFocusable(true);
                    organization.setFocusable(true);



                    full_name.setEnabled(true);
                    phone_number.setEnabled(true);
                    city.setEnabled(true);
                    password.setEnabled(true);
                    mail.setEnabled(true);
                    organization.setEnabled(true);






                    full_name.setHint("Enter FullName");
                    phone_number.setHint("Enter Phone Number");
                    city.setHint("Enter City");
                    password.setHint("Enter Password");
                    mail.setHint("Enter Mail");
                    organization.setHint("Enter Qualification");

                    area.setHint("Not Required for CustomerB2B");
                    experience.setHint("Not Required for CustomerB2B");
                    experience.setEnabled(false);
                    experience.setFocusable(false);

                    area.setEnabled(false);
                    area.setFocusable(false);

                    service.setHint("Not Required for CustomerB2B");
                    subService.setHint("Not Required for CustomerB2B");
                    subService.setEnabled(false);
                    subService.setFocusable(false);

                    service.setEnabled(false);
                    service.setFocusable(false);

                }
                else if(choose_category[0].contentEquals("customerb2c")){
                    type="CustomerB2C";
                    check_spinner="true";


                    full_name.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    phone_number.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    city.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    area.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    password.setHintTextColor(getResources().getColor(R.color.navy_blue));
                    mail.setHintTextColor(getResources().getColor(R.color.navy_blue));

                    organization.setHintTextColor(getResources().getColor(R.color.md_blue_grey_400));
                    experience.setHintTextColor(getResources().getColor(R.color.md_blue_grey_400));
                    subService.setHintTextColor(getResources().getColor(R.color.md_blue_grey_400));
                    service.setHintTextColor(getResources().getColor(R.color.md_blue_grey_400));

                    full_name.setVisibility(View.VISIBLE);
                    phone_number.setVisibility(View.VISIBLE);
                    city.setVisibility(View.VISIBLE);
                    area.setVisibility(View.VISIBLE);
                    password.setVisibility(View.INVISIBLE);
                    mail.setVisibility(View.VISIBLE);
                    organization.setVisibility(View.INVISIBLE);
                    experience.setVisibility(View.INVISIBLE);
                    subService.setVisibility(View.INVISIBLE);
                    service.setVisibility(View.INVISIBLE);


                    full_name.setEnabled(true);
                    phone_number.setEnabled(true);
                    city.setEnabled(true);
                    area.setEnabled(true);
                    password.setEnabled(true);
                    mail.setEnabled(true);


                    full_name.setFocusable(true);
                    phone_number.setFocusable(true);
                    city.setFocusable(true);
                    area.setFocusable(true);
                    password.setFocusable(true);
                    mail.setFocusable(true);

                    full_name.setHint("Enter FullName");
                    phone_number.setHint("Enter Phone Number");
                    city.setHint("Enter City");
                    password.setHint("Enter Password");
                    mail.setHint("Enter Mail");
                    area.setHint("Enter Area");


                    service.setHint("Not Required for CustomerB2C");
                    service.setEnabled(false);
                    service.setFocusable(false);

                    subService.setHint("Not Required for CustomerB2C");
                    subService.setEnabled(false);
                    subService.setFocusable(false);

                    organization.setHint("Not Required for CustomerB2C");
                    organization.setEnabled(false);
                    organization.setFocusable(false);

                    experience.setHint("Not Required for CustomerB2C");
                    experience.setEnabled(false);
                    experience.setFocusable(false);

                }
                else if(choose_category[0].contentEquals("Choose Here"))
                {
                    check_spinner="false";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        loadingBar = new ProgressDialog(this);

        Switch=findViewById(R.id.switch_add_data);
        Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    Intent i=new Intent(AddDataActivity.this,AddDocumentActivity.class);
                    startActivity(i);
                }

            }
        });


        add = findViewById(R.id.add_data);
        add.setOnClickListener(view -> {

            String phoneNumber,fullName,area1,city_st,organization1,experience1,service1,subservice1,password1,mail1;
            phoneNumber = phone_number.getText().toString();
            fullName = full_name.getText().toString();
            city_st = city.getText().toString();

            area1 = area.getText().toString();
            organization1 = organization.getText().toString();
            experience1 = experience.getText().toString();

            service1 = service.getText().toString();
            subservice1 = subService.getText().toString();
//            password1 = password.getText().toString();
            mail1 = mail.getText().toString();
            if (!check_spinner.equals("true"))
            {
                Toast.makeText(getApplicationContext(), "Please Choose Type", Toast.LENGTH_SHORT).show();
            }
            else if(check_spinner.equals("true") && type.equals("CustomerB2C"))
            {


                countryCodePicker.registerPhoneNumberTextView(phone_number);
                if(validateFullName(fullName) && validatePhoneNumber(phoneNumber) && validatecity(city_st) && validatemail(mail1)  && validatearea(area1)) {
                    if (checkBox.isChecked()) {

                        if (countryCodePicker.isValid())
                            Toast.makeText(getApplicationContext(), "Successfully Submitted", Toast.LENGTH_SHORT).show();

                        if(countryCodePicker.isValid()){
                            CustomerB2CcreateEntry(phoneNumber,fullName,area1,city_st,mail1);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Provide valid code/phone number", Toast.LENGTH_SHORT).show();
                    }else
                        checkBox.setError("Accepts Conditions");
                }

            }
            else if(check_spinner.equals("true") && type.equals("CustomerB2B")){


                countryCodePicker.registerPhoneNumberTextView(phone_number);
                if(validateFullName(fullName) && validatePhoneNumber(phoneNumber) && validatecity(city_st) && validatemail(mail1) &&  validateorganization(organization1)) {
                    if (checkBox.isChecked()) {

                        if (countryCodePicker.isValid())
                            Toast.makeText(getApplicationContext(), "Successfully Submitted", Toast.LENGTH_SHORT).show();

                        if(countryCodePicker.isValid()){
                            CustomerB2BCreateEntry(phoneNumber,fullName,city_st,organization1,mail1);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Provide valid code/phone number", Toast.LENGTH_SHORT).show();
                    }else
                        checkBox.setError("Accepts Conditions");
                }
            }
            else if(check_spinner.equals("true") && type.equals("Vendors")){

                Toast.makeText(this, "inside", Toast.LENGTH_SHORT).show();
                countryCodePicker.registerPhoneNumberTextView(phone_number);
                if(validatearea(area1) && validateorganization(organization1) && validateexperience(experience1)  && validateservice(service1) && validatesubservice(subservice1)  && validatemail(mail1) &&validateFullName(fullName) && validatePhoneNumber(phoneNumber) && validatecity(city_st)) {
                    if (checkBox.isChecked()) {

                        if (countryCodePicker.isValid())
                            Toast.makeText(getApplicationContext(), "Successfully Submitted", Toast.LENGTH_SHORT).show();

                        if(countryCodePicker.isValid()){
                            VendorsCreateEntry(phoneNumber,fullName,area1,city_st,organization1,experience1,service1,subservice1,mail1);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Provide valid code/phone number", Toast.LENGTH_SHORT).show();
                    }else
                        checkBox.setError("Accepts Conditions");
                }
            }
            else if(check_spinner.equals("true") && type.equals("Candidate")){

                countryCodePicker.registerPhoneNumberTextView(phone_number);

                if(validatearea(area1) && validateorganization(organization1) && validateexperience(experience1)  &&  validatemail(mail1) &&validateFullName(fullName) && validatePhoneNumber(phoneNumber) && validatecity(city_st)) {
                    if (checkBox.isChecked()) {

                        if (countryCodePicker.isValid())
                            Toast.makeText(getApplicationContext(), "Successfully Submitted", Toast.LENGTH_SHORT).show();

                        if(countryCodePicker.isValid()){
                            CandidateCreateEntry(phoneNumber,fullName,area1,city_st,organization1,experience1,mail1);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Provide valid code/phone number", Toast.LENGTH_SHORT).show();
                    }else
                        checkBox.setError("Accepts Conditions");
                }
            }




        });

    }

    private void CandidateCreateEntry(String phoneNumber,String fullName,String area1,String city_st,String organization1,String experience1,String mail1){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Data").child("Candidate").child(phoneNumber).exists())){
                    HashMap<String,Object> UserDataMap = new HashMap<>();
                    UserDataMap.put("Name",fullName);
                    UserDataMap.put("Phone",phoneNumber);
                    UserDataMap.put("City",city_st);

                    UserDataMap.put("Area",area1);
                    UserDataMap.put("Experience",experience1);

                    UserDataMap.put("Mail",mail1);
                    UserDataMap.put("Organization name",organization1);
//                    UserDataMap.put("Password",password1);

                    RootRef.child("Data").child("Candidate").child(phoneNumber).updateChildren(UserDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        AddNewData(fullName,phoneNumber,city_st,"Candidate");
                                        loadingBar.dismiss();

                                        full_name.setText("");
                                        phone_number.setText("");
                                        city.setText("");
                                        area.setText("");
                                        organization.setText("");
                                        experience.setText("");
//                                        password.setText("");
                                        mail.setText("");



                                    }else{
                                        loadingBar.dismiss();
                                        //Toast.makeText(getApplicationContext(), "Somthing Went Wrong.. Please Try Again After Some time..", Toast.LENGTH_SHORT).show();
                                        dialog.show();

                                    }

                                }
                            });



                }else{
                    //Toast.makeText(getApplicationContext(), "This "+phoneNumber+" number already Exists..", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    errorHeading.setText("Phone no already Exists..");
                    errorText.setText("Please Try Again Using Another phpne number.");
                    dialog.show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void CustomerB2BCreateEntry(String phoneNumber,String fullName,String city_st,String organization1,String mail1){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Data").child("CustomerB2B").child(phoneNumber).exists())){
                    HashMap<String,Object> UserDataMap = new HashMap<>();
                    UserDataMap.put("Name",fullName);
                    UserDataMap.put("Phone",phoneNumber);
                    UserDataMap.put("City",city_st);

                    UserDataMap.put("Mail",mail1);
                    UserDataMap.put("Organization name",organization1);
//                    UserDataMap.put("Password",password1);



                    RootRef.child("Data").child("CustomerB2B").child(phoneNumber).updateChildren(UserDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        AddNewData(fullName,phoneNumber,city_st,"CustomerB2B");
                                        loadingBar.dismiss();

                                        full_name.setText("");
                                        phone_number.setText("");
                                        city.setText("");
                                        organization.setText("");
//                                        password.setText("");
                                        mail.setText("");



                                    }else{
                                        loadingBar.dismiss();
                                        Toast.makeText(getApplicationContext(), "Somthing Went Wrong.. Please Try Again After Some time..", Toast.LENGTH_SHORT).show();
                                        dialog.show();

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
    private void CustomerB2CcreateEntry(String phoneNumber,String fullName,String area1,String city_st,String mail1)
    {


        final DatabaseReference RootRef,RootRef1;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Data").child("CustomerB2C").child(phoneNumber).exists())){
                    HashMap<String,Object> UserDataMap = new HashMap<>();
                    UserDataMap.put("Name",fullName);
                    UserDataMap.put("Phone",phoneNumber);
                    UserDataMap.put("City",city_st);

                    UserDataMap.put("Area",area1);
                    UserDataMap.put("Mail",mail1);
//                    UserDataMap.put("Password",password1);
                    RootRef.child("Data").child("CustomerB2C").child(phoneNumber).updateChildren(UserDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        AddNewData(fullName,phoneNumber,city_st,"CustomerB2C");
                                        loadingBar.dismiss();
                                        full_name.setText("");
                                        phone_number.setText("");
                                        city.setText("");
                                        area.setText("");
//                                        password.setText("");
                                        mail.setText("");



                                    }else{
                                        loadingBar.dismiss();
                                        Toast.makeText(getApplicationContext(), "Somthing Went Wrong.. Please Try Again After Some time..", Toast.LENGTH_SHORT).show();
                                        dialog.show();

                                    }

                                }
                            });



                }else{
                    Toast.makeText(getApplicationContext(), "This "+phoneNumber+" number already Exists..", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    errorHeading.setText("Phone already Exists..");
                    errorText.setText("Please Try Again Using Another Phone number..");
                    dialog.show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void VendorsCreateEntry(String phoneNumber,String fullName,String area1,String city_st,String organization1,String experience1,String service1,String subservice1,String mail1){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        String time;





        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Data").child("Vendors").child(phoneNumber).exists())){
                    HashMap<String,Object> UserDataMap = new HashMap<>();
                    UserDataMap.put("Name",fullName);
                    UserDataMap.put("Phone",phoneNumber);
                    UserDataMap.put("City",city_st);

                    UserDataMap.put("Area",area1);
                    UserDataMap.put("SubService",subservice1);
                    UserDataMap.put("Experience",experience1);

                    UserDataMap.put("Mail",mail1);
                    UserDataMap.put("Organization name",organization1);
//                    UserDataMap.put("Password",password1);
                    UserDataMap.put("Service",service1);




                    RootRef.child("Data").child("Vendors").child(phoneNumber).updateChildren(UserDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        AddNewData(fullName,phoneNumber,city_st,"Vendor");
                                        loadingBar.dismiss();

                                        full_name.setText("");
                                        phone_number.setText("");
                                        city.setText("");
                                        area.setText("");
                                        organization.setText("");
                                        experience.setText("");
//                                        password.setText("");
                                        service.setText("");
                                        subService.setText("");
                                        mail.setText("");



                                    }else{
                                        loadingBar.dismiss();
                                        //Toast.makeText(getApplicationContext(), "Something Went Wrong.. Please Try Again After Some time..", Toast.LENGTH_SHORT).show();
                                        dialog.show();

                                    }

                                }
                            });








                }
                else{
                    Toast.makeText(getApplicationContext(), "This "+phoneNumber+" number already Exists..", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void AddNewData(String fullName,String phoneNumber,String city_st,String Type)
    {
        final DatabaseReference RootRef1;
        RootRef1 = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> UserNewDataMap = new HashMap<>();
        UserNewDataMap.put("Name",fullName);
        UserNewDataMap.put("Contact",phoneNumber);
        UserNewDataMap.put("City",city_st);
        UserNewDataMap.put("Type",Type);
        UserNewDataMap.put("Time",Time);

        RootRef1.child("NewData").child(phoneNumber).updateChildren(UserNewDataMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Details has been Added Sucessfully.. ", Toast.LENGTH_SHORT).show();

                        }else{
                            loadingBar.dismiss();
                            //Toast.makeText(getApplicationContext(), "Something Went Wrong.. Please Try Again After Some time..", Toast.LENGTH_SHORT).show();
                            dialog.show();


                        }

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
        else if (name.length()<3) {
            full_name.setError("Name should be greater than or equal to 3");
            full_name.requestFocus();
            return false;
        }


        if(!name.matches("[a-zA-Z ]+"))
        {
            full_name.requestFocus();
            full_name.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        }
        return true;
    }

    boolean validatecity(String cityS){

        if(cityS.isEmpty()){
            city.setError("Field can't be Empty");
            return false;
        }
        else if(!cityS.matches("[a-zA-Z ]+"))
        {
            city.requestFocus();
            city.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        }
        return true;
    }


    boolean validatearea(String area1) {
        if(area1.isEmpty()){
            area.setError("Field can't be Empty");
            area.requestFocus();
            return false;
        }
        else if(!area1.matches("[a-zA-Z ]+"))
        {
            area.requestFocus();
            area.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        }

        return true;

    }
    boolean validateorganization(String organization1) {
        if(organization1.isEmpty()){
            organization.setError("Field can't be Empty");
            return false;
        }
        return true;
    }
    boolean validateexperience(String experience1)
    {
        if(experience1.isEmpty()){
            experience.setError("Field can't be Empty");
            return false;
        }
        return true;
    }

    boolean validateservice(String service1){
        if(service1.isEmpty()){
            service.setError("Field can't be Empty");
            return false;
        }
        else if(!service1.matches("[a-zA-Z ]+"))
        {
            service.requestFocus();
            service.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        }
        return true;
    }
    boolean validatesubservice(String subservice1){
        if(subservice1.isEmpty()){
            subService.setError("Field can't be Empty");
            return false;
        }
        else if(!subservice1.matches("[a-zA-Z ]+"))
        {
            subService.requestFocus();
            subService.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        }
        return true;
    }
    boolean validatepassword(String password1) {
        if(password1.isEmpty()){
            password.setError("Field can't be Empty");
            return false;
        }else if(password1.length()<6)
        {
            password.setError("Password Should be of atleast 6 digit");
            return false;
        }
        return true;
    }
    boolean validatemail(String mail1){
        if(mail1.isEmpty()){
            mail.setError("Field can't be Empty");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(mail1).matches()) {
            mail.setError("Enter a valid email address");
            mail.requestFocus();
            return false;
        }

        return true;
    }
}