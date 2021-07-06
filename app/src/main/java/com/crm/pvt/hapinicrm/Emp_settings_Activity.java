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
import android.widget.ImageButton;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Emp_settings_Activity extends AppCompatActivity {

    private CircleImageView profileImageView;
    private TextView fullNameEditText, passcodeText, emailText;
    private ImageView profileChangeTextBtn;
    private TextView saveTextButton,logout;
    private Button rest;
    public TimerTask timerTask;
    public Timer timer;
    public TextView timerText;
    private Uri imageUri;
    private String myUrl = "";
    Double time = 0.0;
    int restt = 0;
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;
    private static final int PICK_IMAGE = 1, RESULT_OK = -1;
    private String checker = "";
    private String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_settings);

        storageProfilePrictureRef = FirebaseStorage.getInstance().getReference().child("Employee pictures");

        profileImageView = (CircleImageView) findViewById(R.id.SEimage);
        if(prevalent.CurrentOnlineEmloyee.getImage()!=null){
            Picasso.get().load(prevalent.CurrentOnlineEmloyee.getImage()).placeholder(R.drawable.admin_profile_icon1).into(profileImageView);
        }
        fullNameEditText =  findViewById(R.id.SEName);
        fullNameEditText.setText("Name:  "+prevalent.CurrentOnlineEmloyee.getName());
        passcodeText =  findViewById(R.id.SEIMEI);
        passcodeText.setText("passcode:  "+ prevalent.CurrentOnlineEmloyee.getIMEI());
        emailText = findViewById(R.id.SEemail);
        emailText.setText("Email:  " + prevalent.CurrentOnlineEmloyee.getMail());
        profileChangeTextBtn = findViewById(R.id.SEchange);
        saveTextButton =  findViewById(R.id.SEsave);
        logout = findViewById(R.id.SElogout);

        rest = findViewById(R.id.work_break);
        userInfoDetails(profileImageView);







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
        profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";
                CropImage.activity(imageUri).
                        setAspectRatio(1,1).
                        start(Emp_settings_Activity.this);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference att = FirebaseDatabase.getInstance().getReference().child("Attendance");
                Calendar calendar = Calendar.getInstance();
                String logtime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());



                att.child(prevalent.CurrentOnlineEmloyee.getIMEI()).child(date)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                                String time = snapshot.child("Time").getValue(String.class);
                                long Rest = (long)snapshot.child("RestTime").getValue();

                                DateFormat format = new SimpleDateFormat("hh:mm aa");
                                Date d1 = null;
                                try {
                                    d1 = format.parse(time);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Date d2 = null;
                                try {
                                    d2 = format.parse(logtime);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                long diff1 = d2.getTime()-d1.getTime();
                                long hr = diff1/(1000*60*60);
                                HashMap<String ,Object> hashMap = new HashMap<>();
                                hashMap.put("BreakTime",hr-Rest);
                                hashMap.put("Logout",logtime);
                                att.child(prevalent.CurrentOnlineEmloyee.getIMEI()).child(date).updateChildren(hashMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(Emp_settings_Activity.this, "please wait you  are logging out", Toast.LENGTH_SHORT).show();
                                                    Timer timer = new Timer();
                                                    timer.schedule(new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                                            finish();
                                                        }
                                                    }, 5000);


                                                }
                                            }
                                        });


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });




//                Calendar calendar = Calendar.getInstance();
//                String  curTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
//                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Attendance").child(prevalent.CurrentOnlineEmloyee.getIMEI());
//                HashMap<String ,Object> hashMap = new HashMap<>();
//                hashMap.put("Logout",curTime);
//                ref.child(date).updateChildren(hashMap)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()){
//                                    Toast.makeText(Emp_settings_Activity.this, "please wait you  are logging out", Toast.LENGTH_SHORT).show();
//                                    Timer timer = new Timer();
//                                    timer.schedule(new TimerTask() {
//                                        @Override
//                                        public void run() {
//                                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                                            finish();
//                                        }
//                                    }, 5000);
//
//
//                                }
//                            }
//                        });


            }
        });

        rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                String  curTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());

                Intent intent = new Intent(getApplicationContext(),Break_Activity.class);
                intent.putExtra("RestTime",curTime);
                startActivity(intent);

//                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Attendance").child(prevalent.CurrentOnlineEmloyee.getIMEI());
//                HashMap<String ,Object> hashMap = new HashMap<>();
//                hashMap.put("RestTime",curTime);
//                ref.child(date).updateChildren(hashMap)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()){
//
//
//
//
//                                }
//                            }
//                        });


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
            startActivity(new Intent(Emp_settings_Activity.this,Emp_settings_Activity.class));
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
                    .child(prevalent.CurrentOnlineEmloyee.getIMEI() + ".jpg");
            uploadTask = fileref.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
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
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Employee");
                                HashMap<String ,Object> hashMap = new HashMap<>();
                                hashMap.put("Image",myUrl);
                                ref.child(prevalent.CurrentOnlineEmloyee.getIMEI()).updateChildren(hashMap);
                                progressDialog.dismiss();
                                Toast.makeText(Emp_settings_Activity.this, "Profile updated Successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(Emp_settings_Activity.this, "error while uploading please try again", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }else{
            Toast.makeText(this, "Image is not selected", Toast.LENGTH_SHORT).show();
        }

    }
    private void userInfoDetails(CircleImageView profileImageView) {
        DatabaseReference adminref = FirebaseDatabase.getInstance().getReference().child("Users").child(prevalent.CurrentOnlineEmloyee.getIMEI());
        adminref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("Image").exists()){
                        String image  = snapshot.child("Image").getValue().toString();


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