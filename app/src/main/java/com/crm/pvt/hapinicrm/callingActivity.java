package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.Data;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class callingActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    ImageView calling_fragment;
    TextView name;
    TextView city;
    TextView phone;
    HashMap<String,String> PhoneNumberList;
    ArrayList<HashMap> PhoneList;

    String CUR_EMP_ID;

    ArrayList<Data> DataList;

    /*public fragment_calling(String imei) {
        CUR_EMP_ID = imei;
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);
        //edittext = findViewById(R.id.editText);

        calling_fragment=findViewById(R.id.calling_fragment);
        name=findViewById(R.id.caller_name);
        city=findViewById(R.id.caller_city);
        phone=findViewById(R.id.caller_phone);


        calling_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(callingActivity.this, Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(callingActivity.this,Manifest.permission.READ_PHONE_STATE)){

                        ActivityCompat.requestPermissions(callingActivity.this,
                                new String[]{Manifest.permission.READ_PHONE_STATE},1);

                    }
                    else {
                        ActivityCompat.requestPermissions(callingActivity.this,
                                new String[]{Manifest.permission.READ_PHONE_STATE},1);


                    }

                }
                else {

                }
                makePhoneCall();


            }
        });


    }

    private void makePhoneCall() {
        String phoneNumber = phone.getText().toString();


        if(ContextCompat.checkSelfPermission(callingActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(callingActivity.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL );
        }
        else{
            String dial = "tel:" + phoneNumber;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));

        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                            == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(callingActivity.this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                }

            }
        }

    }



    /*@Override
    public void onStart() {
        super.onStart();

        getEMPID();

//        final DatabaseReference RootRef;
//        RootRef = FirebaseDatabase.getInstance().getReference();
//
//        RootRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                PhoneNumberList = new HashMap<>();
//
//                PhoneList = new ArrayList<>();
//                for(DataSnapshot dataSnapshot: snapshot.child("NewData").child().getChildren()){
//                    PhoneNumberList.clear();
//                    PhoneNumberList.put("City",dataSnapshot.child("City").getValue().toString());
//                    PhoneNumberList.put("Name",dataSnapshot.child("Name").getValue().toString());
//                    PhoneNumberList.put("Number",dataSnapshot.child("Number").getValue().toString());
//                    PhoneList.add(PhoneNumberList);
//
//                }
//
//                name.setText(PhoneList.get(0).get("Name").toString());
//                city.setText(PhoneList.get(0).get("City").toString());
//                phone.setText(PhoneList.get(0).get("Number").toString());
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    private void getEMPID() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String[] Emp_type = new String[1];
        if (databaseReference != null) {
            databaseReference.child("Employee").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Emp_type[0] = snapshot.child(CUR_EMP_ID).child("Type").getValue().toString();

                    //   Toast.makeText(getContext(),String.valueOf(Emp_type[0]),Toast.LENGTH_SHORT).show();
                    getSelectedList(Emp_type[0]);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }


    private void getSelectedList(String type) {
        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("NewData");
        if (df != null) {
            df.orderByChild("Type").equalTo(type).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DataList = new ArrayList<>();


                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        DataList.add(dataSnapshot.getValue(Data.class));
                    }

                    Collections.sort(DataList, new Comparator<Data>() {
                        DateFormat f = new SimpleDateFormat("MM/dd/yyyy '@'hh:mm a");
                        @Override
                        public int compare(Data data, Data t1) {
                            try {
                                return f.parse(data.getTime()).compareTo(f.parse(t1.getTime()));
                            } catch (ParseException e) {
                                throw new IllegalArgumentException(e);
                            }
                        }
                    });
                    getCounter(type,DataList);
                    //   Toast.makeText(getContext(), String.valueOf(DataList.size()), Toast.LENGTH_SHORT).show();



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    private void getCounter(String type, ArrayList<Data> dataList) {
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
        final String[] position = new String[1];
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                position[0] =  snapshot.child(type).getValue().toString();

                set_data(position[0]);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void set_data(String s) {

        int pos = Integer.parseInt(s);
        if(DataList.size() > pos) {
            name.setText(DataList.get(pos).getName());
            city.setText(DataList.get(pos).getCity());
            phone.setText(DataList.get(pos).getContact());

        }
        else
            Toast.makeText(callingActivity.this,"There is no Data left for calling",Toast.LENGTH_SHORT).show();
    }*/

}