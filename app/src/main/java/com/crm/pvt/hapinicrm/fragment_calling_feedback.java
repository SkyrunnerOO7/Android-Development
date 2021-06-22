package com.crm.pvt.hapinicrm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class fragment_calling_feedback extends Fragment {

    Spinner spinner_calling_feedback;
    EditText remark,problem;
    public String s;
    Button submit;
    private ProgressDialog loadingBar;
    ArrayList<String> DataList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_calling_feedback,container,false);
        spinner_calling_feedback=view.findViewById(R.id.spinner_calling_feedback);
        final String[] choose_category = new String[1];

        //to check spinner selected item

        //here s is to check spinner selected or not
        spinner_calling_feedback.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                choose_category[0] = spinner_calling_feedback.getSelectedItem().toString();



                if(choose_category[0].contentEquals("Intersted(Done)")){
                    s="false";
                    Toast.makeText(getContext(),"Intersted",Toast.LENGTH_SHORT).show();
                }else if(choose_category[0].contentEquals("Not Intersted")){
                    s="false";
                    Toast.makeText(getContext(),"Not Intersted",Toast.LENGTH_SHORT).show();
                }else if(choose_category[0].contentEquals("Do not PickUp")) {
                    s="false";
                    Toast.makeText(getContext(), "Don't PickUp", Toast.LENGTH_SHORT).show();
                }
                else if(choose_category[0].contentEquals("Network Issues")){
                    s="false";
                        Toast.makeText(getContext(),"Network Issues",Toast.LENGTH_SHORT).show();
                }
                else if(choose_category[0].contentEquals("Tell to callback")){
                    s="false";
                        Toast.makeText(getContext(),"Tell to callback",Toast.LENGTH_SHORT).show();
                }

                else if(choose_category[0].contentEquals("Intersted(Issue)")){
                    s="false";
                    Toast.makeText(getContext(),"Intersted/Issue",Toast.LENGTH_SHORT).show();
                }
                else{
                    s="true";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        submit=view.findViewById(R.id.Submit_calling_fragment);
        remark=view.findViewById(R.id.remark_calling_feedback);
        problem=view.findViewById(R.id.problem_calling_feedback);
        loadingBar = new ProgressDialog(getContext());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String remark1,problem1;
                remark1=remark.getText().toString().trim();
                problem1=problem.getText().toString().trim();


                if(s.equals("true"))
                {
                    Toast.makeText(getContext(),"Please Select Status",Toast.LENGTH_SHORT).show();
                    /// Added now
                    return;

                }
                else if (remark1.isEmpty())
                {
                    remark.setError("Please Remark");
                    remark.requestFocus();
                    return;
                }
                else if (problem1.isEmpty())
                {
                    problem.setError("Please problem");
                    problem.requestFocus();
                    return;
                }
                else {
                    loadingBar.setTitle("Submitting...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    SubmitData(choose_category[0],remark1,problem1);


                }

            }
        });



        return view;
    }

   public void SubmitData(String status,String remark,String problem){
       final DatabaseReference RootRef;
       RootRef = FirebaseDatabase.getInstance().getReference();

       RootRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               DataList = new ArrayList<>();
               DataList.clear();
               for(DataSnapshot dataSnapshot:snapshot.child("Data").getChildren())
                    DataList.add(dataSnapshot.getKey());

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

       RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               if(!snapshot.child("Feedback").child(DataList.get(0)).exists()){
                   HashMap<String,Object> FeedbackDataMap = new HashMap<>();
                   FeedbackDataMap.put("Status",status);
                   FeedbackDataMap.put("Remark",remark);
                   FeedbackDataMap.put("Problem",problem);

                   RootRef.child("Feedback").child(DataList.get(0)).updateChildren(FeedbackDataMap)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){
                                       Toast.makeText(getContext(), "Sucessfully Submitted", Toast.LENGTH_SHORT).show();
                                       loadingBar.dismiss();

                                   }
                                   else{
                                       loadingBar.dismiss();
                                       Toast.makeText(getContext(), "Somthing Went Wrong.. Please Try Again After Some time..", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });

               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }
}