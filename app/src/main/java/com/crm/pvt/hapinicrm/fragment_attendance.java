package com.crm.pvt.hapinicrm;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class fragment_attendance extends DialogFragment {
    private Button mark_presence;
    private Calendar calendar;
    private ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);

        listView = view.findViewById(R.id.list_item);
        ArrayList<String> list = new ArrayList<>();


        mark_presence = view.findViewById(R.id.mark_attendance);
        mark_presence.setOnClickListener(view1 -> {
            calendar = Calendar.getInstance();
            String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime())+", "+
                    DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());;
            list.add(0,currentDate);

            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,list);
            listView.setAdapter(arrayAdapter);


        });

        return view;
    }
}