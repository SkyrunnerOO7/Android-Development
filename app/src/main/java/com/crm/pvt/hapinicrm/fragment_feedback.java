package com.crm.pvt.hapinicrm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */


public class fragment_feedback extends Fragment {
    View view;
    Button btn;
    EditText desc;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // CALL getInternetStatus() function to check for internet and display error dialog
        if(new InternetDialog(getContext()).getInternetStatus()){
            //   Toast.makeText(getContext(), "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();
        }

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feedback, container, false);
        btn = view.findViewById(R.id.sendFeedBtn);
        desc = view.findViewById(R.id.feedDescriptionBox);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = "abc@gmail.com";
                String message = desc.getText().toString();
                String subject = "Feedback";

                /*JavaMailAPI javaMailAPI = new JavaMailAPI(container.getContext(),mail,subject,message);
                javaMailAPI.execute();*/

                if(message.isEmpty()) {
                    desc.setError("please enter a text");
                }else{
                    Toast.makeText(container.getContext(), "Successfully send", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }



}