package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import static com.crm.pvt.hapinicrm.LoginActivity.SHARED_PREFS_IMEI;

public class callingFeedbackActivity extends AppCompatActivity {


    Spinner spinner_calling_feedback;
    EditText remark,problem;
    public String s;
    Button submit;
    private ProgressDialog loadingBar;
    String CurEmpIMEI;
    public String[] Emp_type = new String[1];
    ArrayList<Data> DataList;
    final String[] choose_category = new String[1];
    int flag = 0;
    String limit;
    int flag1 = 0;

  //// Completed on 7 July 2021.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling_feedback);


        String filename;
        filename="checkCall";
        StringBuffer stringBuffer = new StringBuffer();
        try {
            //Attaching BufferedReader to the FileInputStream by the help of InputStreamReader
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    callingFeedbackActivity.this.openFileInput(filename)));
            String inputString;
            //Reading data line by line and storing it into the stringbuffer
            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString + "\n");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        String fun1=stringBuffer.toString().trim();
        //fun1="true";
        if(!fun1.isEmpty())
        {
            String data;
            data="data";

            FileOutputStream fos;
            try {
                fos = callingFeedbackActivity.this.openFileOutput(filename, Context.MODE_PRIVATE);
                //default mode is PRIVATE, can be APPEND etc.
                fos.write(data.getBytes());
                fos.close();


            } catch (FileNotFoundException e) {e.printStackTrace();}
            catch (IOException e) {e.printStackTrace();}


        }















        spinner_calling_feedback=findViewById(R.id.spinner_calling_feedback);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_IMEI,MODE_PRIVATE);
        CurEmpIMEI =sharedPreferences.getString("text","Empty");
        if(CurEmpIMEI.contentEquals("Empty")){
            Toast.makeText(getApplicationContext(),"Somethings Went Wrong",Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(callingFeedbackActivity.this,CurEmpIMEI,Toast.LENGTH_SHORT).show();
        //Toast.makeText(callingFeedbackActivity.this,limit,Toast.LENGTH_SHORT).show();
        spinner_calling_feedback.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                choose_category[0] = spinner_calling_feedback.getSelectedItem().toString();



                if(choose_category[0].contentEquals("Intersted(Done)")){
                    s="false";
                    Toast.makeText(callingFeedbackActivity.this,"Intersted",Toast.LENGTH_SHORT).show();
                }else if(choose_category[0].contentEquals("Not Intersted")){
                    s="false";
                    Toast.makeText(callingFeedbackActivity.this,"Not Intersted",Toast.LENGTH_SHORT).show();
                }else if(choose_category[0].contentEquals("Do not PickUp")) {
                    s="false";
                    Toast.makeText(callingFeedbackActivity.this, "Don't PickUp", Toast.LENGTH_SHORT).show();
                }
                else if(choose_category[0].contentEquals("Network Issues")){
                    s="false";
                    Toast.makeText(callingFeedbackActivity.this,"Network Issues",Toast.LENGTH_SHORT).show();
                }
                else if(choose_category[0].contentEquals("Tell to callback")){
                    s="false";
                    Toast.makeText(callingFeedbackActivity.this,"Tell to callback",Toast.LENGTH_SHORT).show();
                }

                else if(choose_category[0].contentEquals("Intersted(Issue)")){
                    s="false";
                    Toast.makeText(callingFeedbackActivity.this,"Intersted/Issue",Toast.LENGTH_SHORT).show();
                }
                else{
                    s="true";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        submit=findViewById(R.id.Submit_calling_fragment);
        remark=findViewById(R.id.remark_calling_feedback);
        problem=findViewById(R.id.problem_calling_feedback);
        loadingBar = new ProgressDialog(callingFeedbackActivity.this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String remark1,problem1;
                remark1=remark.getText().toString().trim();
                problem1=problem.getText().toString().trim();


                if(s.equals("true"))
                {
                    Toast.makeText(callingFeedbackActivity.this,"Please Select Status",Toast.LENGTH_SHORT).show();
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



                }

            }
        });




    }

    public void SubmitData(){


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
      getLimit();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("NewData").child(contact);

        databaseReference.removeValue();
        Toast.makeText(callingFeedbackActivity.this,"Data Deleted...",Toast.LENGTH_SHORT).show();
        Intent i1= new Intent(callingFeedbackActivity.this,EmployeeDashboardActivity.class);
      //  i1.putExtra("IMEI",CurEmpIMEI);
        startActivity(i1);
        loadingBar.dismiss();
    }


    private void getDetails(ArrayList<Data> dataList, String s,String type) {

        if(flag == 1)
            return;
        flag = 1;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                flag = 0;
//            }
//        }, 6000);


        int pos = Integer.parseInt(s);

        if(dataList.size() > pos) {
            String name1 = String.valueOf(dataList.get(pos).getName());
            String contact1 = String.valueOf(dataList.get(pos).getContact());
            String city1 = String.valueOf(dataList.get(pos).getCity());
            putData(name1,contact1,city1,s,type);
        }
        else
            Toast.makeText(callingFeedbackActivity.this,"There is no customer left to call!",Toast.LENGTH_SHORT).show();



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


                                        incrementPosition(pos, type);


                                    } else {
                                        loadingBar.dismiss();
                                        Toast.makeText(callingFeedbackActivity.this, "Somthing Went Wrong.. Please Try Again After Some time..", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                else{
                    Toast.makeText(callingFeedbackActivity.this, "Data Already Exists with this contact", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Intent i=new Intent(callingFeedbackActivity.this,callingActivity.class);
                    startActivity(i);

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void incrementPosition(String pos,String type) {


        int position = Integer.parseInt(pos);
        String str = String.valueOf(position+1);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(type).setValue(str);

     getLimit();


    }


    private void getLimit() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Employee");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                limit = snapshot.child(CurEmpIMEI).child("DailyLimit").getValue().toString();

                decreaseLimit(limit);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void decreaseLimit(String limit) {
        if(flag1 == 1)
            return;
        flag1 =1;
        int i = Integer.parseInt(limit)-1;

        String str= String.valueOf(i);
        HashMap<String,Object> map = new HashMap<>();
        map.put("DailyLimit",str);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Employee");
        databaseReference.child(CurEmpIMEI).updateChildren(map);


      //  Toast.makeText(callingFeedbackActivity.this, "Sucessfully Submitted", Toast.LENGTH_SHORT).show();
        Intent i1= new Intent(callingFeedbackActivity.this,EmployeeDashboardActivity.class);
     //   i1.putExtra("IMEI",CurEmpIMEI);
        startActivity(i1);
        loadingBar.dismiss();


    }


}