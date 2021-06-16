package com.crm.pvt.hapinicrm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class fragment_admin_dashboard extends Fragment {

    private LinearLayout AddNewEmployee;
    private LinearLayout AddData;
    private LinearLayout Construction;
    private LinearLayout ActiveUser;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false);

        // CALL getInternetStatus() function to check for internet and display error dialog
        if(new InternetDialog(getContext()).getInternetStatus()){
            //   Toast.makeText(getContext(), "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();
        }

        // Add New Emmployee
        AddNewEmployee = view.findViewById(R.id.add_user);
        AddNewEmployee.setOnClickListener(view1 -> {

            progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            Intent intent = new Intent(getActivity(),Add_new_employee_activity.class);
            startActivity(intent);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                  progressDialog.cancel();

                }
            }, 2000);


        });

        // Add Data
        AddData = view.findViewById(R.id.add_data);
        AddData.setOnClickListener(view1 -> {
           // Toast.makeText(getContext(),"You Click On Add Data ",Toast.LENGTH_SHORT).show();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            Intent intent = new Intent(getActivity(),AddDataActivity.class);
            startActivity(intent);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    progressDialog.cancel();

                }
            }, 2000);
        });

        // Construction
        Construction = view.findViewById(R.id.construction);
        Construction.setOnClickListener(view1 -> {

            progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            Toast.makeText(getContext(),"You Click On Construction",Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getActivity(),Add_new_employee_activity.class);
//            startActivity(intent);

        });

        // Active User
        ActiveUser = view.findViewById(R.id.active_user);
        ActiveUser.setOnClickListener(view1 -> {

            progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            Toast.makeText(getContext(),"You Click On Active Users ",Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getActivity(),Add_new_employee_activity.class);
//            startActivity(intent);
        });

        return view;
    }


}