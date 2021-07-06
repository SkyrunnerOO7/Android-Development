package com.crm.pvt.hapinicrm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.Data;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class fragment_calling_feedback extends Fragment {

    /*Spinner spinner_calling_feedback;
    EditText remark,problem;
    public String s;
    Button submit;
    private ProgressDialog loadingBar;
    String CurEmpIMEI;
    public String[] Emp_type = new String[1];
    ArrayList<Data> DataList;
    final String[] choose_category = new String[1];
    int flag = 0;

    public fragment_calling_feedback(String Key) {
        CurEmpIMEI = Key;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_calling_feedback,container,false);
        /*spinner_calling_feedback=view.findViewById(R.id.spinner_calling_feedback);



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
                    SubmitData();
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startActivity(new Intent(getContext(),fragment_calling.class));

                        }
                    }, 2000);




                }

            }
        });
*/


        return view;
    }

  /* public void SubmitData(){


        getEmp_type(CurEmpIMEI);




    }

    private void getEmp_type(String IMEI) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        if (databaseReference != null) {
            databaseReference.child("Employee").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Emp_type[0] = snapshot.child(IMEI).child("Type").getValue().toString();

                    //   Toast.makeText(getContext(),String.valueOf(Emp_type[0]),Toast.LENGTH_SHORT).show();
                    getSelectedList(Emp_type[0]);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    private void getSelectedList(String type) {
        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("NewData");
        if (df != null) {
            df.orderByChild("Type").equalTo(type).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DataList = new ArrayList<>();


                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        DataList.add(dataSnapshot.getValue(Data.class));
                    }

                    Collections.sort(DataList, new Comparator<Data>() {
                        DateFormat f = new SimpleDateFormat("MM/dd/yyyy '@'hh:mm a");
                        @Override
                        public int compare(Data data, Data t1) {
                            try {
                                return f.parse(data.getTime()).compareTo(f.parse(t1.getTime()));
                            } catch (ParseException e) {
                                throw new IllegalArgumentException(e);
                            }
                        }
                    });
                   getCounter(type,DataList);
                   //   Toast.makeText(getContext(), String.valueOf(DataList.size()), Toast.LENGTH_SHORT).show();



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void getCounter(String type, ArrayList<Data> dataList) {


        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
        final String[] position = new String[1];
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                position[0] =  snapshot.child(type).getValue().toString();

                // Toast.makeText(getContext(), String.valueOf(position[0]), Toast.LENGTH_SHORT).show();
                // Toast.makeText(getContext(), String.valueOf(dataList.get(0).getName()), Toast.LENGTH_SHORT).show();

                if(choose_category[0].trim().contentEquals("Not Intersted")){
                    deleteData(dataList.get(Integer.parseInt(position[0])).getContact());
                }
                else{
                    getDetails(dataList,position[0],type);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void deleteData(String contact) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("NewData").child(contact);

        databaseReference.removeValue();
        Toast.makeText(getContext(),"Data Deleted...",Toast.LENGTH_SHORT).show();
        loadingBar.dismiss();
    }

    private void getDetails(ArrayList<Data> dataList, String s,String type) {

        if(flag == 1)
            return;
        flag = 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                flag = 0;
            }
        }, 6000);


        int pos = Integer.parseInt(s);

        if(dataList.size() > pos) {
            String name1 = String.valueOf(dataList.get(pos).getName());
            String contact1 = String.valueOf(dataList.get(pos).getContact());
            String city1 = String.valueOf(dataList.get(pos).getCity());
            putData(name1,contact1,city1,s,type);
        }
        else
            Toast.makeText(getContext(),"There is no customer left to call!",Toast.LENGTH_SHORT).show();



     }

    private void putData(String name1, String contact, String city,String pos,String type) {

        DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.child("Feedback").child(contact).exists()) {
                    HashMap<String, Object> FeedbackDataMap = new HashMap<>();
                    FeedbackDataMap.put("Status", choose_category[0]);
                    FeedbackDataMap.put("Remark", remark.getText().toString());
                    FeedbackDataMap.put("Problem", problem.getText().toString());
                    FeedbackDataMap.put("City", city);
                    FeedbackDataMap.put("Name", name1);
                    FeedbackDataMap.put("Contact", contact);


                    RootRef.child("Feedback").child(contact).updateChildren(FeedbackDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {


                                        incrementPosition(choose_category[0], contact, pos, type);


                                    } else {
                                        loadingBar.dismiss();
                                        Toast.makeText(getContext(), "Somthing Went Wrong.. Please Try Again After Some time..", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                } else
                    Toast.makeText(getContext(), "Data Already Exists with this contact", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void incrementPosition(String status, String contact,String pos,String type) {


            int position = Integer.parseInt(pos);
            String str = String.valueOf(position+1);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child(type).setValue(str);



        Toast.makeText(getContext(), "Sucessfully Submitted", Toast.LENGTH_SHORT).show();
        loadingBar.dismiss();

    }*/
}