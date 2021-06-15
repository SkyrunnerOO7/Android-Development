package com.crm.pvt.hapinicrm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crm.pvt.hapinicrm.models.DBhelper;

import java.util.ArrayList;

/**
 * Created by Kuldeep Sahu on 05/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class RegisterdActivity extends AppCompatActivity {
    TextView alreadyAcSignIn;
    Button SignUpBtn;
    DBhelper db;
    EditText pass;
    EditText name;
    EditText mail;
    EditText password;
    EditText repassword;

    ArrayList list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerd);
        getSupportActionBar().hide();

        list.add("1234");
        list.add("4567");
        list.add("7894");

        // CALL getInternetStatus() function to check for internet and display error dialog
        if(new InternetDialog(getApplicationContext()).getInternetStatus()){
            //   Toast.makeText(getContext(), "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();
        }

        // get id
        pass = findViewById(R.id.passcodeinputadmin);
        name = findViewById(R.id.editTextName);
        mail = findViewById(R.id.editTextMobile);
        password = findViewById(R.id.editTextEmail_login);
        repassword = findViewById(R.id.editTextPassword_login);







        alreadyAcSignIn = (TextView)findViewById(R.id.already_have_ac_signIn);
        SignUpBtn = (Button)findViewById(R.id.sign_up_btn);
        db = new DBhelper(this);



        alreadyAcSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterdActivity.this, "Please SignIn", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterdActivity.this, LoginActivity.class));
                finish();
            }
        });

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();

            }
        });

    }

    public void validate(){

        // get text
        String passcode = pass.getText().toString();
        //    int passcode_int = Integer.parseInt(pass.getText().toString());
        String Fullname = name.getText().toString();
        String email = mail.getText().toString();
        String password_st = password.getText().toString();
        String repassword_st = repassword.getText().toString();
        if(passcode.isEmpty()) {
            pass.setError("please enter your passcode");
        }
        else if(Fullname.isEmpty()) {
            name.setError("please enter your name");
        }else if(email.isEmpty()) {
            mail.setError("please enter email");
        }else if(password_st.isEmpty()) {
            password.setError("please enter your password");
        }else if(repassword_st.isEmpty()){
            repassword.setError("please retype your password");
        }else{
            if(list.contains(passcode)){
                if(password_st.equals(repassword_st)){
                    Boolean checkadmin = db.checkAdminEmail(email);
                    if(checkadmin==false){
                        Boolean insert = db.insertdata(passcode,Fullname,email,password_st);
                        if(insert==true){
                            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        }else{
                            Toast.makeText(this, "Registration Failed please try again", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this, "Admin already exists", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "passcode is invalid", Toast.LENGTH_SHORT).show();
            }
        }
    }
}