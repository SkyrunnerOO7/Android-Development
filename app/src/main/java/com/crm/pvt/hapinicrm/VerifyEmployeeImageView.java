package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class VerifyEmployeeImageView extends AppCompatActivity {

    ImageView image;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_employee_image_view);
        Intent intent = getIntent();
        url=intent.getStringExtra("url");

        image=findViewById(R.id.viewDetails);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(VerifyEmployeeImageView.this, "It may take few seconds", Toast.LENGTH_SHORT).show();
                Picasso.get().load(url).into(image);
            }
        });
    }
}