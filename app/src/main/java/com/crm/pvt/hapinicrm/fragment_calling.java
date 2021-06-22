package com.crm.pvt.hapinicrm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class fragment_calling extends DialogFragment {
    ImageView calling_fragment;
    TextView name;
    TextView city;
    TextView phone;
    HashMap<String,String> PhoneNumberList;
    ArrayList<HashMap> PhoneList;
    private static final int REQUEST_CALL = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_calling,container,false);
        calling_fragment=view.findViewById(R.id.calling_fragment);
        name=view.findViewById(R.id.caller_name);
        city=view.findViewById(R.id.caller_city);
        phone=view.findViewById(R.id.caller_phone);

        calling_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

               makePhoneCall();



            }
        });

        return view;
    }



    private void makePhoneCall() {
        String phoneNumber = phone.getText().toString();


            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL );
            }
            else{
                String dial = "tel:" + phoneNumber;
                startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));

            }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }
            else{
                Toast.makeText(getContext(),"Permission DENIED",Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PhoneNumberList = new HashMap<>();

                PhoneList = new ArrayList<>();
                for(DataSnapshot dataSnapshot: snapshot.child("Data").getChildren()){
                    PhoneNumberList.clear();
                    PhoneNumberList.put("City",dataSnapshot.child("City").getValue().toString());
                    PhoneNumberList.put("Name",dataSnapshot.child("Name").getValue().toString());
                    PhoneNumberList.put("Number",dataSnapshot.child("Number").getValue().toString());
                    PhoneList.add(PhoneNumberList);

                }

                name.setText(PhoneList.get(0).get("Name").toString());
                city.setText(PhoneList.get(0).get("City").toString());
                phone.setText(PhoneList.get(0).get("Number").toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}