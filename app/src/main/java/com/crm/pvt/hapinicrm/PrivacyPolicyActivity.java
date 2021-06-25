package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class PrivacyPolicyActivity extends AppCompatActivity {
    ImageView backBtnPrivacyAc;
    TextView privacy_info1,privacy_info2,privacy_info3,contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        getSupportActionBar().hide();
        privacy_info1=findViewById(R.id.privacy_policy_info1);
        privacy_info2=findViewById(R.id.privacy_policy_info2);
        privacy_info3=findViewById(R.id.privacy_policy_info3);
        contact=findViewById(R.id.privacy_policy_contact);


        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Intent.ACTION_SEND);
                it.putExtra(Intent.EXTRA_EMAIL, new String[]{contact.getText().toString()});
                it.putExtra(Intent.EXTRA_SUBJECT,"Write your Subject");
                it.putExtra(Intent.EXTRA_TEXT,"Write your message");
                it.setType("message/rfc822");
                startActivity(Intent.createChooser(it,"Choose Mail App"));
            }
        });

        privacy_info1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://policies.google.com/privacy"));
                startActivity(i);
            }
        });
        privacy_info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://firebase.google.com/policies/analytics"));
                startActivity(i);
            }
        });

        privacy_info3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://firebase.google.com/support/privacy/"));
                startActivity(i);
            }
        });

        backBtnPrivacyAc = (ImageView)findViewById(R.id.back_arrow_btn_privacyPolicyAc);

        backBtnPrivacyAc.setOnClickListener(view -> {
            startActivity(new Intent(PrivacyPolicyActivity.this,WelcomeActivity.class));
            finish();
        });
    }
}