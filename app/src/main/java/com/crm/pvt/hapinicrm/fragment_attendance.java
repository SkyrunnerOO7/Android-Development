package com.crm.pvt.hapinicrm;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.Admin;
import com.crm.pvt.hapinicrm.models.Attendance;
import com.crm.pvt.hapinicrm.prevalent.prevalent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class fragment_attendance extends DialogFragment {
    private Button mark_presence;
    private Calendar calendar;
    private ListView listView;
    DatabaseReference databaseReference;
    long id ;
    ArrayList<String> list;
    private String loginTime;

    public fragment_attendance(String id) {
    this.id = Long.parseLong(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);

        listView = view.findViewById(R.id.list_item);
        list = new ArrayList<>();

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                Attendance att = snapshot.child("Attendance").child("IMEI").getValue(Attendance.class);

                prevalent.CurrentUserAttendance = att;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        mark_presence = view.findViewById(R.id.mark_attendance);
        mark_presence.setOnClickListener(view1 -> {
            calendar = Calendar.getInstance();
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            //DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());




            String  curTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());



            list.add(0,currentDate+"  "+curTime);
            MarkAttendance(currentDate,curTime);



//            Intent i = new Intent(getContext(),EmployeeDashboardActivity.class);
//            i.putExtra("loginTime",curTime);
//            startActivity(i);




        });

        return view;
    }

    private void MarkAttendance(String currentDate, String curTime) {
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//
//        databaseReference.child("Attendance").child(String.valueOf(id)).child(currentDate).child("Time").setValue(curTime);
        String IMEI = prevalent.CurrentOnlineEmloyee.getIMEI();
        mark(IMEI,currentDate,curTime);





        Toast.makeText(getContext(),"Your Attendance is marked successfully",Toast.LENGTH_SHORT).show();
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);

        

    }



    public void mark(String IMEI,String Date,String Time){

        final DatabaseReference eRef;
        eRef = FirebaseDatabase.getInstance().getReference();


        eRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Attendance").child("IMEI").child(Date).exists())){
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("IMEI",IMEI);
                    hashMap.put("Date",Date);
                    hashMap.put("Time",Time);

                   

                    hashMap.put("RestTime",(long)0);  //// Added after Rohan Said

                    eRef.child("Attendance").child(IMEI).child(Date).updateChildren(hashMap);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}