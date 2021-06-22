package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

public class UserFeedbackShowActivity extends AppCompatActivity {

    Spinner userFeedbackSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feedback_show);

        userFeedbackSpinner = findViewById(R.id.spinner_user_feedback);
        final String[] choose_category = new String[1];
        userFeedbackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                choose_category[0] = userFeedbackSpinner.getSelectedItem().toString();

                if(choose_category[0].contentEquals("Name")){
                    Toast.makeText(UserFeedbackShowActivity.this, "Name Selected", Toast.LENGTH_SHORT).show();
                }else if(choose_category[0].contentEquals("Number")){
                    Toast.makeText(UserFeedbackShowActivity.this, "Number Selected", Toast.LENGTH_SHORT).show();
                }else if(choose_category[0].contentEquals("New")){
                    Toast.makeText(UserFeedbackShowActivity.this, "New Selected", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UserFeedbackShowActivity.this, "please select on field", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }
}