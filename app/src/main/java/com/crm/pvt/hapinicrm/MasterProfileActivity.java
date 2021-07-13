package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import com.crm.pvt.hapinicrm.models.Master;
import com.crm.pvt.hapinicrm.prevalent.prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MasterProfileActivity extends AppCompatActivity {

    private CircleImageView masterProfileImage;
    private static final int PICK_IMAGE = 1, RESULT_OK = -1;
    Uri imageUri;
    TextView save;

    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;
    private String checker = "";
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_profile);
        getSupportActionBar().hide();
        masterProfileImage = (CircleImageView) findViewById(R.id.profile_image);
        storageProfilePrictureRef = FirebaseStorage.getInstance().getReference().child("Master");

        save = findViewById(R.id.save);

        if(prevalent.CurrentMaster.getImage()!=null){
            Picasso.get().load(prevalent.CurrentMaster.getImage()).placeholder(R.drawable.admin_profile_icon1).into(masterProfileImage);
        }


            /*masterProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // to select image from phone storage
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                }
            });*/

        userInfoDetails(masterProfileImage);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checker.equals("clicked")){
                    userInfosaved();
                }else{
//                    updateonlyUserinfo();
                }

            }
        });

        masterProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";
                CropImage.activity(imageUri).
                        setAspectRatio(1, 1).
                        start(MasterProfileActivity.this);



            }
        });

        logout = findViewById(R.id.logout_button);

        logout.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
            finish();
        });


    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //to set image in imageView
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            masterProfileImage.setImageURI(imageUri);
        }
    }*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            masterProfileImage.setImageURI(imageUri);

        } else {
            Toast.makeText(this, "Error try again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MasterProfileActivity.this, settings_Activity.class));
            finish();

        }

    }


    private void userInfosaved() {
        if (checker.equals("clicked")) {
            uploadImage();
        }


    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("update profile");
        progressDialog.setMessage("Please wait while updating the profile Image");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (imageUri != null) {
            final StorageReference fileref = storageProfilePrictureRef
                    .child(prevalent.CurrentMaster.getCode() + ".jpg");
            uploadTask = fileref.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();

                    }

                    return fileref.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloaduri = task.getResult();
                                myUrl = downloaduri.toString();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Master").child(prevalent.CurrentMaster.getCode());
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("Image", myUrl);
                                ref.updateChildren(hashMap);
                                progressDialog.dismiss();
                                Toast.makeText(MasterProfileActivity.this, "Profile updated Successfully", Toast.LENGTH_SHORT).show();
                                //finish();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(MasterProfileActivity.this, "error while uploading please try again", Toast.LENGTH_SHORT).show();


                            }

                        }
                    });
        } else {
            Toast.makeText(this, "Image is not selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void userInfoDetails(ImageView profileImageView) {
        DatabaseReference adminref = FirebaseDatabase.getInstance().getReference().child("Master").child(prevalent.CurrentMaster.getCode());

        adminref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    if (snapshot.child("Image").exists()) {
                        String image = snapshot.child("Image").getValue().toString();
                        Picasso.get().load(image).into(profileImageView);


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });


    }
}