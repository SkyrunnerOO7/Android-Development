package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.Employee;
import com.crm.pvt.hapinicrm.prevalent.prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;

public class VerifyEmployee extends AppCompatActivity {

    ImageView front,back,pan;
    TextView dob;

    Button verified;

    private Uri imageUri,adharfront,adharback,panCard;
    private String myUrl = "";
    private StorageTask uploadTask,taskfront,taskback,taskpan;;
    private StorageReference storageProfilePrictureRef;
    private static final int PICK_IMAGE = 1, RESULT_OK = -1;
    String imei;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_employee);
        dob=findViewById(R.id.verifyDob);
        front=findViewById(R.id.Viewaadharfront);
        back=findViewById(R.id.ViewaadharBack);
        pan=findViewById(R.id.ViewPancard);
        verified=findViewById(R.id.Verified);


        Intent intent = getIntent();
        imei=intent.getStringExtra("imei");




        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference adminref = FirebaseDatabase.getInstance().getReference().child("Employee").child(imei);
                adminref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            if(snapshot.child("AdharFront").exists()){
                                String image  = snapshot.child("AdharFront").getValue().toString();


                                Intent i=new Intent(VerifyEmployee.this,VerifyEmployeeImageView.class);
                                i.putExtra("url",image);
                                startActivity(i);
                                //Picasso.get().load(image).into(profileImageView);



                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference adminref = FirebaseDatabase.getInstance().getReference().child("Employee").child(imei);
                adminref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            if(snapshot.child("AdharBack").exists()){
                                String image  = snapshot.child("AdharBack").getValue().toString();


                                Intent i=new Intent(VerifyEmployee.this,VerifyEmployeeImageView.class);
                                i.putExtra("url",image);
                                startActivity(i);


                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });



        pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference adminref = FirebaseDatabase.getInstance().getReference().child("Employee").child(imei);
                //DatabaseReference adminref = FirebaseDatabase.getInstance().getReference().child("Users").child(prevalent.CurrentOnlineEmloyee.getIMEI());
                adminref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            if(snapshot.child("PanCard").exists()){
                                String image  = snapshot.child("PanCard").getValue().toString();


                                Intent i=new Intent(VerifyEmployee.this,VerifyEmployeeImageView.class);
                                i.putExtra("url",image);
                                startActivity(i);


                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        verified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(VerifyEmployee.this, "Marked as Verfied", Toast.LENGTH_SHORT).show();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Employee");
                HashMap<String ,Object> hashMap = new HashMap<>();
                hashMap.put("Verified","true");
                //hashMap.put("DOB",dob.getText().toString());
                ref.child(imei).updateChildren(hashMap);

            }
        });




        reference=FirebaseDatabase.getInstance().getReference().child("Employee");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                if(dataSnapshot.exists())
                {
                    Employee newPost = dataSnapshot.getValue(Employee.class);

                    if (imei.equals(newPost.getIMEI()))
                    {
                        if(!newPost.getDOB().equals("null") )
                        {
                            String s=newPost.getDOB().toString();
                            dob.setText("Date of birth: "+s);
                        }
                        else
                        {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(VerifyEmployee.this);
                            builder1.setMessage("Employee has not yet submitted his/her document");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                        }


                    }
                }
                else
                {
                    Toast.makeText(VerifyEmployee.this, "Employee not found", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });






        storageProfilePrictureRef = FirebaseStorage.getInstance().getReference().child("Employee pictures");

        ViewuserInfoDetailsAdharFront(front);
        ViewuserInfoDetailsAdharBack(back);
        ViewuserInfoDetails(pan);


    }
    private void ViewuserInfoDetailsAdharFront(ImageView profileImageView) {
        DatabaseReference adminref = FirebaseDatabase.getInstance().getReference().child("Employee").child(imei);
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


    private void ViewuserInfoDetailsAdharBack(ImageView profileImageView) {
        DatabaseReference adminref = FirebaseDatabase.getInstance().getReference().child("Employee").child(imei);
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



    private void ViewuserInfoDetails(ImageView profileImageView) {
        DatabaseReference adminref = FirebaseDatabase.getInstance().getReference().child("Employee").child(imei);
        //DatabaseReference adminref = FirebaseDatabase.getInstance().getReference().child("Users").child(prevalent.CurrentOnlineEmloyee.getIMEI());
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

}