package com.crm.pvt.hapinicrm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kuldeep Sahu on 05/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class RegisterdActivity extends AppCompatActivity {
    TextView alreadyAcSignIn;
    Button SignUpBtn;

    EditText pass;
    EditText name;
    EditText mail;
    EditText password;
    EditText repassword;
    ImageView devIconBtn;
    private ProgressDialog loadingBar;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerd);
        getSupportActionBar().hide();

        // CALL getInternetStatus() function to check for internet and display error dialog
        if(new InternetDialog(getApplicationContext()).getInternetStatus()){
            //   Toast.makeText(getContext(), "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();
        }

        // get id
        pass = findViewById(R.id.passcodeinputadmin);
        name = findViewById(R.id.editTextName);
        mail = findViewById(R.id.editTextMobile);
        password = findViewById(R.id.editTextEmail_login);
        devIconBtn = findViewById(R.id.laptop_Dev_icon_btn);
        repassword = findViewById(R.id.editTextPassword_login);
        checkBox = findViewById(R.id.checkbox_signupAC);
        loadingBar = new ProgressDialog(this);


   /*     String passcode = pass.getText().toString();
        String Fullname = name.getText().toString();
        String email = mail.getText().toString();
        String password_st = password.getText().toString();
        String repassword_st = repassword.getText().toString();*/






        alreadyAcSignIn = (TextView)findViewById(R.id.already_have_ac_signIn);
        SignUpBtn = (Button)findViewById(R.id.sign_up_btn);



        alreadyAcSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterdActivity.this, "Please SignIn", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterdActivity.this, LoginActivity.class));
                finish();
            }
        });

      /*  SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String StrLoginEmail = mail.getText().toString();
                String StrLoginPassword = password.getText().toString();
                if(validateEmail(StrLoginEmail) && validatePass(StrLoginPassword)){
                    if(checkBox.isChecked()){
                        createAccount();
                    }else{
                        Toast.makeText(RegisterdActivity.this, "please check the checkbox", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterdActivity.this, "PLease try again ", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterdActivity.this, "This site is Under Construction", Toast.LENGTH_SHORT).show();
            }
        });

        devIconBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert("Develop and Design:");
            }
        });

    }

    public void alert(String header) {
        AlertDialog alertDialog = new AlertDialog.Builder(RegisterdActivity.this)
                .setTitle(header)
                .setMessage("\bKULDEEP SAHU (Senior Developer & Team Head)\b\n\n"
                        +"PUNIT PAWAR (VCS Maintener)\n\n"
                        +"PRIYANSHU GUPTA (Developer)\n\n"
                        +"ROHAN KULKARNI (Developer)\n\n"
                        +"SAPNA KUSHWAH (Developer)\n\n\n"
                        +"sahukuldeep912001@gmail.com\n\n"
                        +"http://skywarrior09.gq\n"
                        +"-------------------------"
)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }

      public void createAccount(){

          String passcode = pass.getText().toString();
          String Fullname = name.getText().toString();
          String email = mail.getText().toString();
          String password_st = password.getText().toString();
          String repassword_st = repassword.getText().toString();


        // get text


        if(passcode.isEmpty()){
            pass.setError("passcode can't be empty");
        }
        else if(Fullname.isEmpty()){
            Toast.makeText(this, "Please Enter Your Name...", Toast.LENGTH_SHORT).show();
        }
       else if(email.isEmpty()){
            mail.setError("Field can't be Empty");
        }
        else if(password_st.isEmpty()){
            Toast.makeText(this, "Please Enter a password...", Toast.LENGTH_SHORT).show();
        }
        else if(repassword_st.isEmpty()){
            Toast.makeText(this, "Please Enter a please retype your password...", Toast.LENGTH_SHORT).show();
        }
        else if(!password_st.equals(repassword_st)){
            Toast.makeText(this, "passwords are not matching", Toast.LENGTH_SHORT).show();
        }
        else{

        loadingBar.setTitle("Create Account");
        loadingBar.setMessage("please Wait while checking Credentials..");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        Validate(email,passcode,Fullname,password_st);



        }



    }



    public void Validate(final String email,final String passcode,final String fullname,final String password){
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


                    RootRef.child("Admin").child(passcode).updateChildren(UserDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Congratulations..Your Account Has been Created Sucessfully.. ", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent = new Intent(RegisterdActivity.this,LoginActivity.class);
                                        startActivity(intent);

                                    }else{
                                        loadingBar.dismiss();
                                        Toast.makeText(getApplicationContext(), "Somthing Went Wrong.. Please Try Again After Some time..", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });



                }else{
                    Toast.makeText(getApplicationContext(), "This "+email+" id already Exists..", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(getApplicationContext(), "Please Try Again Using Another email..", Toast.LENGTH_SHORT).show();
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