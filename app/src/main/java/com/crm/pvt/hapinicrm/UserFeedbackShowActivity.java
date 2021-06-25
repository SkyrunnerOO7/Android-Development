package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.Adapters.FeedbackAdapter;
import com.crm.pvt.hapinicrm.models.Employee;
import com.crm.pvt.hapinicrm.models.Feedback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserFeedbackShowActivity extends AppCompatActivity {

    Spinner userFeedbackSpinner;
    private RecyclerView recyclerView;
    private DatabaseReference dbref;
    private int count;
    TextView text;
    private ArrayList<Feedback> list;
    Feedback feedback ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feedback_show);

        userFeedbackSpinner = findViewById(R.id.spinner_user_feedback);
        recyclerView = findViewById(R.id.rv_user_feedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedback = new Feedback();
        text  =findViewById(R.id.Count_user_feedback);
        final String[] choose_category = new String[1];
        userFeedbackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                choose_category[0] = userFeedbackSpinner.getSelectedItem().toString();

                if(choose_category[0].contentEquals("Name")){
                    FeedbackListCity();
                    Toast.makeText(UserFeedbackShowActivity.this, "Name Selected", Toast.LENGTH_SHORT).show();
                }else if(choose_category[0].contentEquals("City")){

                    Toast.makeText(UserFeedbackShowActivity.this, "City Selected", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onStart() {
        super.onStart();



    }

    void FeedbackListCity() {
        dbref = FirebaseDatabase.getInstance().getReference().child("Feedback");
        if (dbref != null) {
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list = new ArrayList<>();

                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    list.add(dataSnapshot.getValue(Feedback.class));
                    }

                    FeedbackAdapter adapter = new FeedbackAdapter(list,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else
            Toast.makeText(getApplicationContext(),"DataBase Doesn't Exists",Toast.LENGTH_SHORT).show();
    }



}