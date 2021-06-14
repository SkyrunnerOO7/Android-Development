package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PrivacyPolicyActivity extends AppCompatActivity {
    ImageView backBtnPrivacyAc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        backBtnPrivacyAc = (ImageView)findViewById(R.id.back_arrow_btn_privacyPolicyAc);

        backBtnPrivacyAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrivacyPolicyActivity.this,WelcomeActivity.class));
                finish();
            }
        });
    }
}