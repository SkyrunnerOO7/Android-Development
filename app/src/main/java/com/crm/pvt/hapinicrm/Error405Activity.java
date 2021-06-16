package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Error405Activity extends AppCompatActivity {
    private Button closeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error405);
        getSupportActionBar().hide();

        closeButton = findViewById(R.id.CloseBtnErrorAC);
        closeButton.setOnClickListener(view -> {
            startActivity(new Intent(Error405Activity.this,MainActivity.class));
        });
    }
}