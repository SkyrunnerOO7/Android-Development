package com.crm.pvt.hapinicrm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class SupportActivity extends AppCompatActivity {
    Button phone;
    Button mail;
    private static final int callrequest = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        getSupportActionBar().setTitle("Support");

        // CALL getInternetStatus() function to check for internet and display error dialog
        if(new InternetDialog(getApplicationContext()).getInternetStatus()){
            //   Toast.makeText(getContext(), "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        phone = findViewById(R.id.phone_btn);
        mail = findViewById(R.id.mail_btn);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makecall();

            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("rafalestudio2020@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback | Hapini CRM | Support");
                intent.putExtra(Intent.EXTRA_TEXT,"I got some technical or other issues through this app! Describe problem...");
                startActivity(intent);

            }
        });

    }

    public  void makecall(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(SupportActivity.this,new String[] {Manifest.permission.CALL_PHONE},callrequest);

        }else{
            String s = "tel: 180030000123";
            Intent pI = new Intent(Intent.ACTION_CALL);
            pI.setData(Uri.parse(s));
            startActivity(pI);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==callrequest){
            if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makecall();

            }else{
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
