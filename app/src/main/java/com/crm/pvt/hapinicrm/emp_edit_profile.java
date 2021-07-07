package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.Admin_picture_Model;
import com.crm.pvt.hapinicrm.prevalent.prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class emp_edit_profile extends AppCompatActivity {

    CircleImageView front,back,pan;
    Button Submit;
    EditText dob;


    private Uri imageUri,adharfront,adharback,panCard;
    private String myUrl = "";
    private StorageTask uploadTask,taskfront,taskback,taskpan;;
    private StorageReference storageProfilePrictureRef;
    private static final int PICK_IMAGE = 1, RESULT_OK = -1;
    private String checker1 = "",checker2 = "",checker3 = "",check;
    Calendar myCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_edit_profile);
        dob=findViewById(R.id.dob_emp);
        Submit=findViewById(R.id.Submit_emp_details);
        front=findViewById(R.id.aadhar);
        back=findViewById(R.id.aadharBack);
        pan=findViewById(R.id.PanCard);
        check="false";
        myCalendar = Calendar.getInstance();


        storageProfilePrictureRef = FirebaseStorage.getInstance().getReference().child("Employee pictures");

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(emp_edit_profile.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        userInfoDetailsAdharFront(front);
        userInfoDetailsAdharBack(back);
        userInfoDetails(pan);





        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if(checker1.equals("clicked") && checker2.equals("clicked") && checker3.equals("clicked") && !dob.getText().toString().isEmpty()){
                    //userInfosaved();

                    userInfosaved();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Employee");
                    HashMap<String ,Object> hashMap = new HashMap<>();
                    hashMap.put("DOB",dob.getText().toString());
                    //hashMap.put("DOB",dob.getText().toString());
                    ref.child(prevalent.CurrentOnlineEmloyee.getIMEI()).updateChildren(hashMap);


                }else{
                    Toast.makeText(emp_edit_profile.this, "Please select all fields", Toast.LENGTH_SHORT).show();

                }
            }
        });
        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker1 = "clicked";
                check="front";
                /*CropImage.activity(imageUri).
                        setAspectRatio(1,1).
                        start(emp_edit_profile.this);*/
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker2 = "clicked";
                check="back";
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE);
                /*CropImage.activity(imageUri).
                        setAspectRatio(1,1).
                        start(emp_edit_profile.this);*/


            }
        });

        pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker3 = "clicked";
                check="pan";
                /*CropImage.activity(imageUri).
                        setAspectRatio(1,1).
                        start(emp_edit_profile.this);*/
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE);

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            String s="true";
            //profileImg.setImageURI(imageUri);
            if (imageUri != null) {

                if (check.equalsIgnoreCase("front"))
                {
                    front.setImageURI(imageUri);
                    adharfront=imageUri;

                }
                else if(check.equalsIgnoreCase("back"))
                {
                    back.setImageURI(imageUri);
                    adharback=imageUri;
                }
                else if(check.equalsIgnoreCase("pan"))
                {
                    pan.setImageURI(imageUri);
                    panCard=imageUri;
                }

            } else {

                Toast.makeText(this, "No profile image selected", Toast.LENGTH_SHORT).show();

            }


        }
        else{
            Toast.makeText(this, "Error try again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(emp_edit_profile.this,Emp_settings_Activity.class));
            finish();

        }








        /*if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data!=null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            if (check.equalsIgnoreCase("front"))
            {
                front.setImageURI(imageUri);
                adharfront=imageUri;

            }
            else if(check.equalsIgnoreCase("back"))
            {
                back.setImageURI(imageUri);
                adharback=imageUri;
            }
            else if(check.equalsIgnoreCase("pan"))
            {
                pan.setImageURI(imageUri);
                panCard=imageUri;
            }


        }else{
            Toast.makeText(this, "Error try again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(emp_edit_profile.this,Emp_settings_Activity.class));
            finish();

        }*/
    }
    private void userInfosaved() {

        if(checker1.equals("clicked") && checker2.equals("clicked") && checker3.equals("clicked")) {
            uploadImage();
            //Toast.makeText(this, checker1, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, adharback.toString(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, adharfront.toString(), Toast.LENGTH_SHORT).show();

        }

    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(emp_edit_profile.this);
        progressDialog.setTitle("update profile");
        progressDialog.setMessage("Please wait while updating the profile Image");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();



        if( adharback!=null){
            //final StorageReference filerefback = FirebaseStorage.getInstance().getReference().child("Employee pictures").child(prevalent.CurrentOnlineEmloyee.getIMEI()).child("AadharBack"+ ".jpg");
            final StorageReference filerefback = storageProfilePrictureRef.child(prevalent.CurrentOnlineEmloyee.getIMEI()).child("AadharBack"+ ".jpg");
            taskback = filerefback.putFile(adharback);
            taskback.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();

                    }

                    return filerefback.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull  Task<Uri> task) {
                            if(task.isSuccessful()){
                                Uri downloaduri = task.getResult();
                                myUrl  = downloaduri.toString();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Employee");
                                HashMap<String ,Object> hashMap = new HashMap<>();
                                hashMap.put("AdharBack",myUrl);
                                ref.child(prevalent.CurrentOnlineEmloyee.getIMEI()).updateChildren(hashMap);
                                //progressDialog.dismiss();
                                //Toast.makeText(emp_edit_profile.this, "Profile updated Successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                //progressDialog.dismiss();
                                Toast.makeText(emp_edit_profile.this, "error while uploading please try again", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


        }
        if( panCard!=null){
            final StorageReference filerefpan = storageProfilePrictureRef.child(prevalent.CurrentOnlineEmloyee.getIMEI()).child( "PanCard"+".jpg");
            taskpan = filerefpan.putFile(panCard);
            taskpan.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();

                    }

                    return filerefpan.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull  Task<Uri> task) {
                            if(task.isSuccessful()){
                                Uri downloaduri = task.getResult();
                                myUrl  = downloaduri.toString();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Employee");
                                HashMap<String ,Object> hashMap = new HashMap<>();
                                hashMap.put("PanCard",myUrl);
                                ref.child(prevalent.CurrentOnlineEmloyee.getIMEI()).updateChildren(hashMap);
                                //progressDialog.dismiss();
                                //Toast.makeText(emp_edit_profile.this, "Profile updated Successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                //progressDialog.dismiss();
                                Toast.makeText(emp_edit_profile.this, "error while uploading please try again", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }

        if( adharfront!=null){
            //final StorageReference fileref = storageProfilePrictureRef.child(prevalent.CurrentOnlineEmloyee.getIMEI() + ".jpg");
            final StorageReference filereffront = storageProfilePrictureRef.child(prevalent.CurrentOnlineEmloyee.getIMEI()).child("AadharFront" + ".jpg");
            taskfront = filereffront.putFile(adharfront);

            //uploadTask = fileref.putFile(imageUri);
            taskfront.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();

                    }

                    return filereffront.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull  Task<Uri> task) {
                            if(task.isSuccessful()){
                                Uri downloaduri = task.getResult();
                                myUrl  = downloaduri.toString();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Employee");
                                HashMap<String ,Object> hashMap = new HashMap<>();
                                hashMap.put("AdharFront",myUrl);
                                //hashMap.put("DOB",dob.getText().toString());
                                ref.child(prevalent.CurrentOnlineEmloyee.getIMEI()).updateChildren(hashMap);
                                progressDialog.dismiss();
                                Toast.makeText(emp_edit_profile.this, "Profile updated Successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(emp_edit_profile.this, "error while uploading please try again", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });







        }
        else{

            Toast.makeText(this, "Image is not selected", Toast.LENGTH_SHORT).show();
        }

    }

    private void userInfoDetailsAdharFront(CircleImageView profileImageView) {
        DatabaseReference adminref = FirebaseDatabase.getInstance().getReference().child("Users").child(prevalent.CurrentOnlineEmloyee.getIMEI());
        adminref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("AdharFront").exists()){
                        String image  = snapshot.child("AdharFront").getValue().toString();


                        Picasso.get().load(image).into(profileImageView);



                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void userInfoDetailsAdharBack(CircleImageView profileImageView) {
        DatabaseReference adminref = FirebaseDatabase.getInstance().getReference().child("Users").child(prevalent.CurrentOnlineEmloyee.getIMEI());
        adminref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("AdharBack").exists()){
                        String image  = snapshot.child("AdharBack").getValue().toString();


                        Picasso.get().load(image).into(profileImageView);



                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



    private void userInfoDetails(CircleImageView profileImageView) {
        DatabaseReference adminref = FirebaseDatabase.getInstance().getReference().child("Users").child(prevalent.CurrentOnlineEmloyee.getIMEI());
        adminref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("PanCard").exists()){
                        String image  = snapshot.child("PanCard").getValue().toString();


                        Picasso.get().load(image).into(profileImageView);



                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }
}