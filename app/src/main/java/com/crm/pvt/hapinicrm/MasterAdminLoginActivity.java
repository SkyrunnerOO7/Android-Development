package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.Master;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class MasterAdminLoginActivity extends AppCompatActivity {
    EditText codeED;
    Button verify;
    String parent = "Master";
    ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_admin_login);

        codeED = findViewById(R.id.Master_confirm_code);
        verify = findViewById(R.id.verify_btn_master_code);
        loadingbar = new ProgressDialog(this);




        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code_st = codeED.getText().toString();
                if(verifycode(code_st)){
                    if(TextUtils.isEmpty(code_st)){
                        Toast.makeText(getApplicationContext(), "Please Enter Your code...", Toast.LENGTH_SHORT).show();
                    }else{
                        loadingbar.setTitle("Login Account");
                        loadingbar.setMessage("please Wait while checking Credentials..");
                        loadingbar.setCanceledOnTouchOutside(false);
                        loadingbar.show();
                        AllowAccess(code_st);
                    }

                }else{
                    codeED.setError("Should be of 8 digits");
                }

            }
        });

    }


    public void AllowAccess(final String code){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parent).child(code).exists()){
                    Master MasterData = dataSnapshot.child(parent).child(code).getValue(Master.class);
                    if (MasterData.getCode().equals(code)){
                        if(parent.equals("Master")){
                            Toast.makeText(getApplicationContext(), "Welcome Master Admin You are Logged In Successfully... ", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                            Intent intent = new Intent(getApplicationContext(),MasterAdminDasboardActivity.class);
                            startActivity(intent);
                        }else{
                            loadingbar.dismiss();
                            Toast.makeText(getApplicationContext(), "Incorrect Code..", Toast.LENGTH_SHORT).show();
                        }

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public Boolean verifycode(String code){
        int count = 0;
        for(int i = 0;i<code.length();i++){
            count ++;
        }
        if(count==8){
            return true;
        }else{

            return false;
        }
    }
}
