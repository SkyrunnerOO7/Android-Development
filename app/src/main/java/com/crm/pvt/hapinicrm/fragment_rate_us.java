package com.crm.pvt.hapinicrm;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.android.material.badge.BadgeUtils;

/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */
public class fragment_rate_us extends Fragment {
    View view;
    RatingBar ratingbar;
    EditText editText;
    Button rate_btn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_rate_us, container, false);
        ratingbar = view.findViewById(R.id.ratingBar);
        editText = view.findViewById(R.id.description_box_of_rate_us);
        rate_btn = view.findViewById(R.id.RateUs_Btn);


        String desc = editText.getText().toString();

        rate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String to="abc@gmail.com";
                String subject="";
                String message= desc;
                String num_rate = String.valueOf(ratingbar.getRating());


                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);
                email.putExtra(Intent.EXTRA_TEXT, num_rate);

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });



        return view;
    }
}