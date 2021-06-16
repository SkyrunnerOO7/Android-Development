package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class PrivacyPolicyActivity extends AppCompatActivity {
    ImageView backBtnPrivacyAc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        getSupportActionBar().hide();

        backBtnPrivacyAc = (ImageView)findViewById(R.id.back_arrow_btn_privacyPolicyAc);

        backBtnPrivacyAc.setOnClickListener(view -> {
            startActivity(new Intent(PrivacyPolicyActivity.this,WelcomeActivity.class));
            finish();
        });
    }
}