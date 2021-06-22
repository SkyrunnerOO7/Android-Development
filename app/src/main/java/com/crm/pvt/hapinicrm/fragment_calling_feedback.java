package com.crm.pvt.hapinicrm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class fragment_calling_feedback extends Fragment {




    private String mParam1;
    private String mParam2;
    Spinner spinner_calling_feedback;
    EditText remark,problem;
    public String s;
    Button submit;



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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String remark1,problem1;
                remark1=remark.getText().toString().trim();
                problem1=problem.getText().toString().trim();


                if(s.equals("true"))
                {
                    Toast.makeText(getContext(),"Please Select Status",Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getContext(),"Successfully Submited",Toast.LENGTH_SHORT).show();

                }

            }
        });



        return view;
    }
}