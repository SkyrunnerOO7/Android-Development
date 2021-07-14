package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;

import android.widget.AdapterView;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.prevalent.prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class Add_new_employee_activity extends AppCompatActivity {

    private TextInputEditText build_number;
    private Button add;
    private CheckBox checkBox;
    private TextInputEditText email;
    private TextInputEditText pass;
    //private TextInputEditText area;
    private ProgressDialog loadingBar;
    Spinner spinner;
    String type_of_emp;
    private EditText Empname,CityE,area,phoneE;

    Dialog dialog;
    private TextView errorText,errorHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_employee);


        // CALL getInternetStatus() function to check for internet and display error dialog
        if(new InternetDialog(getApplicationContext()).getInternetStatus()){
            //   Toast.makeText(getContext(), "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();
        }
        spinner = findViewById(R.id.spinner_login);
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               type_of_emp = spinner.getSelectedItem().toString();
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

        build_number = findViewById(R.id.bulid_number);
        email = findViewById(R.id.Email);
        pass = findViewById(R.id.password);
        checkBox = findViewById(R.id.checkbox);
        area = findViewById(R.id.areaE);
        loadingBar = new ProgressDialog(this);
        Empname = findViewById(R.id.Ename);
        CityE = findViewById(R.id.cityE);
        phoneE = findViewById(R.id.phoneE);
        checkBox=findViewById(R.id.checkbox);

        dialog=new Dialog(Add_new_employee_activity.this);
        dialog.setContentView(R.layout.layout_error404);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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




        add = findViewById(R.id.add_emp);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                createAccount();


            }
        });





    }


    public void createAccount(){

        String IMEI = build_number.getText().toString();
        String mail = email.getText().toString();
        String password = pass.getText().toString();
        String area1 = area.getText().toString();
        String empname = Empname.getText().toString();
        String cityE = CityE.getText().toString();
        String PhoneE = phoneE.getText().toString();


        // get text

        if(type_of_emp.contentEquals("Select Employee Type")){
            ((TextView)spinner.getSelectedView()).setError("Choose Valid Type");
        }
        else if(IMEI.isEmpty()){
            build_number.setError("Field can't be empty");
        }
        else if(IMEI.length() != 4){

            build_number.setError("Number should be of 4 digits");

        }
        else if(mail.isEmpty()){
            email.setError("Field can't be empty");
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            email.setError("Enter a valid email address");
            email.requestFocus();
        }
        else if(empname.isEmpty()){
            Empname.setError("Name can't be empty");
        }

        else if(empname.length()<3)
        {
            Empname.requestFocus();
            Empname.setError("Name should contain atleast 3 character");
        }
        else if(!empname.matches("[a-zA-Z ]+"))
        {
            Empname.requestFocus();
            Empname.setError("Enter valid name");
        }
        else if(cityE.isEmpty()){
            CityE.setError("Field can't be empty");
        }
        else if(!cityE.matches("[a-zA-Z ]+"))
        {
            CityE.requestFocus();
            CityE.setError("Enter valid city name");
        }
        else if(area1.isEmpty()){
            CityE.setError("Field can't be empty");
        }
        else if(!area1.matches("[a-zA-Z ]+"))
        {
            CityE.requestFocus();
            CityE.setError("Enter valid area name");
        }


        else if(!(PhoneE.length()==10)){
            phoneE.setError("Enter valid number");
        }

        else if(PhoneE.isEmpty()){
            phoneE.setError("Field can't be empty");
        }
        else if(password.isEmpty()){
            pass.setError("passcode can't be empty");
        }
        else if (!validatePass(password))
        {
            Toast.makeText(this, "Enter Valid password", Toast.LENGTH_SHORT).show();
        }

        /*else if(repassword.isEmpty()){
            conf_pass.setError("Field can't be Empty");
        }
        else if(!password.equals(repassword))
        {

            conf_pass.setError("password and Confrim password should be same");

        }*/

        else if(!checkBox.isChecked())
        {
            checkBox.setError("Please check the box");

        }



        else{
           
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("please Wait while checking Credentials..");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            ValidateEmp(type_of_emp,IMEI,mail,password,empname,cityE,PhoneE,area1,"null");



        }



    }

    public void ValidateEmp(String type,String IMEI,String mail,String password,String name,String city,String phone,String area1,String url){


        final String currentDate,currentTime;
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Employee").child(IMEI).exists())){
                    HashMap<String,Object> EmpDataMap = new HashMap<>();
                    EmpDataMap.put("mail",mail);
                    EmpDataMap.put("IMEI",IMEI);
                    EmpDataMap.put("Password",password);
                    EmpDataMap.put("Name",name);
                    EmpDataMap.put("City",city);
                    EmpDataMap.put("Area",area1);
                    EmpDataMap.put("Phone",phone);
                    EmpDataMap.put("Type",type);
                    EmpDataMap.put("ImgUrl",url);
                    EmpDataMap.put("DailyLimit","30");
                    EmpDataMap.put("Verified","false");
                    EmpDataMap.put("AdharBack","null");
                    EmpDataMap.put("AdharFront","null");
                    EmpDataMap.put("PanCard","null");
                    EmpDataMap.put("DOB","null");
                    EmpDataMap.put("Date",currentDate);
                    EmpDataMap.put("Time",currentTime);
                    EmpDataMap.put("AdminName",prevalent.CurrentOnlineAdmin.getName());

                    RootRef.child("Employee").child(IMEI).updateChildren(EmpDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Account Has been Created Sucessfully.. ", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        build_number.setText("");
                                        email.setText("");
                                        pass.setText("");
                                        area.setText("");
                                        Empname.setText("");
                                        CityE.setText("");
                                        phoneE.setText("");











                                    }else{
                                        loadingBar.dismiss();
                                        Toast.makeText(getApplicationContext(), "Something Went Wrong.. Please Try Again After Some time..", Toast.LENGTH_SHORT).show();


                                    }

                                }
                            });



                }else{
                    //Toast.makeText(getApplicationContext(), "This "+email+" id already Exists..", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    //Toast.makeText(getApplicationContext(), "Please Try Again Using Another IMEI..", Toast.LENGTH_SHORT).show();
                    errorHeading.setText("Invalid Credentials");
                    errorText.setText("PLease Try again with another IMEI number");
                    dialog.show();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private boolean validIMEI(String IMEI){

        if(IMEI.length() != 15){

            build_number.setError("Number should be of 15 digits");
            return false;
        }


        return true;
    }


    private boolean validatePass(String password) {
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

}
