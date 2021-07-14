package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class addAdminActivity extends AppCompatActivity {
    TextInputEditText build_number;
    Button add;
    EditText passcode;
    EditText mail;
    EditText name;
    EditText password;
    EditText Area;
    ProgressDialog loadingBar;
    CheckBox checkBox;
    EditText cityA,phoneA;
    Dialog dialog;
    private TextView errorText,errorHeading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        passcode = findViewById(R.id.bulid_number_admin);
        name = findViewById(R.id.Name_admin);
        mail = findViewById(R.id.Email_admin);
        password = findViewById(R.id.password_admin);
        checkBox = findViewById(R.id.checkbox_admin);
        loadingBar = new ProgressDialog(this);
        cityA = findViewById(R.id.CityA);
        phoneA = findViewById(R.id.phoneA);
        Area = findViewById(R.id.areaA);


        dialog=new Dialog(addAdminActivity.this);
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


        add = findViewById(R.id.add_admin_btn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String StrLoginEmail = mail.getText().toString();
                String StrLoginPassword = password.getText().toString();
                if(validateEmail(StrLoginEmail) && validatePass(StrLoginPassword)){
                    if(checkBox.isChecked()){
                        createAccount();
                    }else{
                        Toast.makeText(getApplicationContext(), "please check the checkbox", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "PLease try again ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createAccount() {

        String Passcode = passcode.getText().toString();
        String Fullname = name.getText().toString();
        String email = mail.getText().toString();
        String password_st = password.getText().toString();
        String PhoneA = phoneA.getText().toString();
        String CityA = cityA.getText().toString();
        String AreaA = Area.getText().toString();



        // get text


        if(Passcode.isEmpty()){
            passcode.setError("passcode can't be empty");
        }
        else if(Passcode.length()!=4){
            passcode.setError("passcode should be of 4 digit");
        }

        else if(Fullname.isEmpty()){
            name.setError("Please Enter Your Name");
            //Toast.makeText(this, "Please Enter Your Name...", Toast.LENGTH_SHORT).show();
        }
        else if(Fullname.length()<3){
            name.setError("Name should be of 3 character");
            //Toast.makeText(this, "Please Enter Your Name...", Toast.LENGTH_SHORT).show();
        }
        else if(email.isEmpty()){
            mail.setError("Field can't be Empty");
        }
        else if(password_st.isEmpty()){
            //Toast.makeText(this, "Please Enter a password...", Toast.LENGTH_SHORT).show();
            password.setError("Please Enter a password");
        }
        else if(CityA.isEmpty()){
            //Toast.makeText(this, "Please Enter City Name...", Toast.LENGTH_SHORT).show();
            cityA.setError("please Enter City Name");
        }
        else if(CityA.length()<3){
            cityA.setError("City Name should be of 3 character");
            //Toast.makeText(this, "Please Enter Your Name...", Toast.LENGTH_SHORT).show();
        }
        else if(AreaA.isEmpty()){
            Toast.makeText(this, "Please Enter Area...", Toast.LENGTH_SHORT).show();
        }
        else if(PhoneA.isEmpty()){
            phoneA.setError("please Enter Phone number");
            Toast.makeText(this, " please Enter Phone number...", Toast.LENGTH_SHORT).show();
        }
        else if(PhoneA.length()!=10){
            phoneA.setError("phone number should be of 10 digit");
            //Toast.makeText(this, " please Enter valid Phone number...", Toast.LENGTH_SHORT).show();
        }
        else{

            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("please Wait while checking Credentials..");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            Validate(email,Passcode,Fullname,password_st,CityA,PhoneA,AreaA);



        }

    }

    private void Validate(final String email,final String passcode,final String fullname,final String password,final String cityA,final String phoneA,final String areaA) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Admin").child(passcode).exists())){
                    HashMap<String,Object> UserDataMap = new HashMap<>();
                    UserDataMap.put("Email",email);
                    UserDataMap.put("Name",fullname);
                    UserDataMap.put("Passcode",passcode);
                    UserDataMap.put("Password",password);
                    UserDataMap.put("City",cityA);
                    UserDataMap.put("Phone",phoneA);
                    UserDataMap.put("Area",areaA);


                    RootRef.child("Admin").child(passcode).updateChildren(UserDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Congratulations..Your Account Has been Created Sucessfully.. ", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent = new Intent(getApplicationContext(),MasterAdminDasboardActivity.class);
                                        startActivity(intent);

                                    }else{
                                        loadingBar.dismiss();
                                        Toast.makeText(getApplicationContext(), "Somthing Went Wrong.. Please Try Again After Some time..", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });



                }else{
                    Toast.makeText(getApplicationContext(), "This "+passcode+" id already Exists..", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    errorHeading.setText("Invalid Credentials");
                    errorText.setText("PLease Try again with another Email id");
                    dialog.show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


    private boolean validatePass(String Password) {

        String[] regex = {".*\\d+.*"
                , ".*[a-z].*", ".*[A-Z].*"
                , ".*[@#$%^&+=].*"
                , ".*/\\s/.*", ".{8,20}.*"};
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(regex[0]);
        matcher = pattern.matcher(Password);
        if (!matcher.matches()) {
            password.setError("Password should contain at least one digit");
            return false;
        }
        pattern  = Pattern.compile(regex[1]);
        matcher = pattern.matcher(Password);
        if (!matcher.matches()) {
            password.setError("Password should contain at least one lower case alphabet");
            return false;
        }
        pattern = Pattern.compile(regex[2]);
        matcher = pattern.matcher(Password);
        if (!matcher.matches()) {
            password.setError("Password should contain at least one upper case alphabet");
            return false;
        }
        pattern = Pattern.compile(regex[3]);
        matcher = pattern.matcher(Password);
        if (!matcher.matches()) {
            password.setError("Password should contain at least one special character");
            return false;
        }
        pattern = Pattern.compile(regex[4]);
        matcher = pattern.matcher(Password);
        if (matcher.matches()) {
            password.setError("White spaces are not allowed");
            return false;
        }
        pattern = Pattern.compile(regex[5]);
        matcher = pattern.matcher(Password);
        if (!matcher.matches()) {
            password.setError("Password should contain 8 to 20 characters only");
            return false;
        }
        return true;
    }

    private boolean validateEmail(String Email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Email);
        if (!matcher.matches()) {
            mail.setError("Please Provide a valid E-mail");
            return false;
        }
        return true;
    }

}