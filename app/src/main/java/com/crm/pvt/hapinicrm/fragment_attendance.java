package com.crm.pvt.hapinicrm;

import android.content.Intent;
import android.os.Bundle;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class fragment_attendance extends DialogFragment {
    private Button mark_presence;
    private Calendar calendar;
    private ListView listView;
    DatabaseReference databaseReference;
    long id ;
    ArrayList<String> list;

    public fragment_attendance(String id) {
    this.id = Long.parseLong(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);

        listView = view.findViewById(R.id.list_item);
        list = new ArrayList<>();



        mark_presence = view.findViewById(R.id.mark_attendance);
        mark_presence.setOnClickListener(view1 -> {
            calendar = Calendar.getInstance();
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            //DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
            String  curTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());;
            list.add(0,currentDate);

            MarkAttendance(currentDate,curTime);




        });

        return view;
    }

    private void MarkAttendance(String currentDate, String curTime) {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Attendance").child(String.valueOf(id)).child(currentDate).child("Time").setValue(curTime);

        Toast.makeText(getContext(),"Your Attendance is marked successfully",Toast.LENGTH_SHORT).show();
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);

        

    }
}