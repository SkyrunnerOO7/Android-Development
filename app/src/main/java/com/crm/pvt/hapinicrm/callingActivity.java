package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
//import
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.crm.pvt.hapinicrm.models.Data;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class callingActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    ImageView calling_fragment;
    TextView name,status,date;
    TextView city;
    TextView phone;
    LinearLayout back;
    HashMap<String,String> PhoneNumberList;
    ArrayList<HashMap> PhoneList;
    String[] limit = new String[1];
    String CUR_EMP_ID;
    public static final String SHARED_PREFS1 = "sharedPrefsAttendan112";
    public static final String text = "text1";
    ArrayList<Data> DataList;

    //comment
    /*public fragment_calling(String imei) {
        CUR_EMP_ID = imei;
    }*/

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.share_menu,menu);
        MenuItem item1=menu.findItem(R.id.shareBtn);

        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i=new Intent(callingActivity.this,EmployeeDashboardActivity.class);
                startActivity(i);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);
        calling_fragment=findViewById(R.id.calling_fragment);
        name=findViewById(R.id.caller_name);
        city=findViewById(R.id.caller_city);
        phone=findViewById(R.id.caller_phone);
        date = findViewById(R.id.date);
        status = findViewById(R.id.status);

        String filename;
        filename="checkCall";
        StringBuffer stringBuffer = new StringBuffer();
        try {
            //Attaching BufferedReader to the FileInputStream by the help of InputStreamReader
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    callingActivity.this.openFileInput(filename)));
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
        if(fun1.isEmpty())
        {
            String data;
            data="false";

            FileOutputStream fos;
            try {
                fos = callingActivity.this.openFileOutput(filename, Context.MODE_PRIVATE);
                //default mode is PRIVATE, can be APPEND etc.
                fos.write(data.getBytes());
                fos.close();


            } catch (FileNotFoundException e) {e.printStackTrace();}
            catch (IOException e) {e.printStackTrace();}


        }

        //back=findViewById(R.id.back_arrow_btn_caliing);
        /*back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(callingActivity.this,EmployeeDashboardActivity.class);
                startActivity(i);
                finish();
            }
        });*/
        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.back_arrow_icon);
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        CUR_EMP_ID =  getIntent().getStringExtra("IMEI");
        back = findViewById(R.id.back_button);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(callingActivity.this,EmployeeDashboardActivity.class);
            intent.putExtra("IMEI",CUR_EMP_ID);
            intent.putExtra("stop","true");
            startActivity(intent);
        });
        calling_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(callingActivity.this, Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(callingActivity.this,Manifest.permission.READ_PHONE_STATE)){

                        ActivityCompat.requestPermissions(callingActivity.this,
                                new String[]{Manifest.permission.READ_PHONE_STATE},1);

                    }
                    else {
                        ActivityCompat.requestPermissions(callingActivity.this,
                                new String[]{Manifest.permission.READ_PHONE_STATE},1);


                    }

                }
                else {

                }
                makePhoneCall();

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), callingFeedbackActivity.class));
                        finish();
                    }
                }, 7000);



            }
        });


    }

    private void makePhoneCall() {
        String phoneNumber = phone.getText().toString();


        if(ContextCompat.checkSelfPermission(callingActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(callingActivity.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL );


        }
        else{
            String dial = "tel:" + phoneNumber;
            if(phoneNumber.length()!=10)
            {
                Toast.makeText(this, "No data ", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(callingActivity.this);
                builder1.setMessage("No data Available for Calling ");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //dialog.cancel();
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(callingActivity.this);
                                builder1.setMessage("Do you want to go to Employee Activity");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                Intent i=new Intent(callingActivity.this,EmployeeDashboardActivity.class);
                                                i.putExtra("IMEI",CUR_EMP_ID);
                                                i.putExtra("stop","true");
                                                startActivity(i);

                                            }
                                        });
                                AlertDialog alert11 = builder1.create();
                                alert11.show();

                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
            else
            {
                Intent intent =new Intent(Intent.ACTION_CALL, Uri.parse(dial));
                ////  intent.putExtra("limit",limit[0]);

                startActivity(intent);
            }


        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                            == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(callingActivity.this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                }

            }
        }

    }


    @Override
    public void onStart() {

        //Toast.makeText(this, "OnStart", Toast.LENGTH_SHORT).show();

        if(isFirstTime()) {
            // Code to pop up attendance activicty
            fragment_attendance fragment_attendance = new fragment_attendance(CUR_EMP_ID);
            fragment_attendance.show(getSupportFragmentManager(), "MyFragment");
        }
        super.onStart();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Employee");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                limit[0] = snapshot.child(CUR_EMP_ID).child("DailyLimit").getValue().toString();

                if(Integer.parseInt(limit[0]) > 0)
                    getEMPID();
                else
                    Toast.makeText(getApplicationContext(),"You have Reached You Daily Limit",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void getEMPID() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String[] Emp_type = new String[1];
        if (databaseReference != null) {
            databaseReference.child("Employee").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Emp_type[0] = snapshot.child(CUR_EMP_ID).child("Type").getValue().toString();

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
        position[0]="n";
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(callingActivity.this, type, Toast.LENGTH_SHORT).show();

                //position[0]="2";
                position[0] =  snapshot.child(type).getValue().toString();



                set_data(position[0]);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void set_data(String s) {

        int pos = Integer.parseInt(s);
        if(DataList.size() > pos) {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child("Feedback").child(DataList.get(pos).getContact()).exists()) {
                        String Date = (String) snapshot.child("Feedback").child(DataList.get(pos).getContact()).child("LastDate").getValue();
                        String Status = (String) snapshot.child("Feedback").child(DataList.get(pos).getContact()).child("Status").getValue();
                        date.setText(Date);
                        status.setText(Status);
                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            name.setText(DataList.get(pos).getName());
            city.setText(DataList.get(pos).getCity());
            phone.setText(DataList.get(pos).getContact());

        }
        else
            Toast.makeText(callingActivity.this,"There is no Data left for calling",Toast.LENGTH_SHORT).show();
    }

    private boolean isFirstTime() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS1,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        if(sharedPreferences.getString(text,"2021-08-16").contentEquals(date1)){
            return false;

        }
        else{

            editor.putString(text,date1);
            editor.apply();
            return true;
        }

    }

    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i=new Intent(callingActivity.this,EmployeeDashboardActivity.class);
                startActivity(i);

                this.finish();
                //return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}