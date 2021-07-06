package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class settings_Activity extends AppCompatActivity {
    private ImageView profileImageView;
    private TextView fullNameEditText, passcodeText, emailText;

    private TextView  closeTextBtn;

    private ImageView profileChangeTextBtn;

    private TextView saveTextButton;

    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;
    private static final int PICK_IMAGE = 1, RESULT_OK = -1;
    private String checker = "";
    //abx

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        storageProfilePrictureRef = FirebaseStorage.getInstance().getReference().child("Admin pictures");

        profileImageView = (ImageView) findViewById(R.id.settings_profile_image);
        if(prevalent.CurrentOnlineAdmin.getImage()!=null){
            Picasso.get().load(prevalent.CurrentOnlineAdmin.getImage()).placeholder(R.drawable.admin_profile_icon1).into(profileImageView);
        }
        fullNameEditText =  findViewById(R.id.settings_full_name);
        fullNameEditText.setText("Name:  "+prevalent.CurrentOnlineAdmin.getName());
        passcodeText =  findViewById(R.id.settings_passcode);
        passcodeText.setText("passcode:  "+ prevalent.CurrentOnlineAdmin.getPasscode());
        emailText = findViewById(R.id.settings_Email);
        emailText.setText("Email:  " + prevalent.CurrentOnlineAdmin.getEmail());

        //profileChangeTextBtn = (TextView) findViewById(R.id.profile_image_change_btn);

        profileChangeTextBtn =  findViewById(R.id.profile_image_change_btn);

        saveTextButton =  findViewById(R.id.save_btn);


        userInfoDetails(profileImageView,fullNameEditText,emailText,passcodeText);

        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checker.equals("clicked")){
                    userInfosaved();
                }else{
//                    updateonlyUserinfo();
                }

            }
        });
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";
                CropImage.activity(imageUri).
                        setAspectRatio(1,1).
                        start(settings_Activity.this);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data!=null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            profileImageView.setImageURI(imageUri);
            
        }else{
            Toast.makeText(this, "Error try again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(settings_Activity.this,settings_Activity.class));
            finish();

        }

    }

    private void userInfosaved() {
        if(checker.equals("clicked")){
            uploadImage();
        }


    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("update profile");
        progressDialog.setMessage("Please wait while updating the profile Image");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if(imageUri!=null){
            final StorageReference fileref = storageProfilePrictureRef
                    .child(prevalent.CurrentOnlineAdmin.getPasscode() + ".jpg");
            uploadTask = fileref.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull  Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();

                    }

                    return fileref.getDownloadUrl();
                }
            })
            .addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull  Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloaduri = task.getResult();
                        myUrl  = downloaduri.toString();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Admin");
                        HashMap<String ,Object> hashMap = new HashMap<>();
                        hashMap.put("Image",myUrl);
                        ref.child(prevalent.CurrentOnlineAdmin.getPasscode()).updateChildren(hashMap);
                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),AdminDashboardActivity.class));
                        Toast.makeText(settings_Activity.this, "Profile updated Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(settings_Activity.this, "error while uploading please try again", Toast.LENGTH_SHORT).show();


                    }

                }
            });
        }else{
            Toast.makeText(this, "Image is not selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void userInfoDetails(ImageView profileImageView, TextView fullNameEditText, TextView emailText, TextView passcodeText) {
        DatabaseReference adminref = FirebaseDatabase.getInstance().getReference().child("Users").child(prevalent.CurrentOnlineAdmin.getPasscode());
        adminref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("Image").exists()){
                        String image  = snapshot.child("Image").getValue().toString();
                        String passcode  = snapshot.child("Passcode").getValue().toString();
                        String name  = snapshot.child("Name").getValue().toString();
                        String email = snapshot.child("Email").getValue().toString();

                        Picasso.get().load(image).into(profileImageView);



                    }
                }

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });


    }
}