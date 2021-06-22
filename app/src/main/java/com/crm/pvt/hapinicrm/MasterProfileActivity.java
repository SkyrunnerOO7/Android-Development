package com.crm.pvt.hapinicrm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MasterProfileActivity extends AppCompatActivity {

    private ImageView masterProfileImage;
    private static final int PICK_IMAGE = 1, RESULT_OK = -1;
    Uri imageUri;

    private TextView Email;
    private Button resetCode;
    private Button logout;
    private CircleImageView image;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_master_profile);
            getSupportActionBar().hide();
            masterProfileImage = (ImageView) findViewById(R.id.profile_image);

            masterProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // to select image from phone storage
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                }
            });


            Email = findViewById(R.id.email);
            resetCode = findViewById(R.id.reset_button);
            logout = findViewById(R.id.logout_button);
            image = findViewById(R.id.profile_image);

            logout.setOnClickListener(view -> {
                startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                finish();
            });

            resetCode.setOnClickListener(view -> {
                Toast.makeText(getApplicationContext(), "Your Code will be RESET ASAP", Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            //to set image in imageView
            if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
                imageUri = data.getData();
                masterProfileImage.setImageURI(imageUri);
            }
        }

    }
