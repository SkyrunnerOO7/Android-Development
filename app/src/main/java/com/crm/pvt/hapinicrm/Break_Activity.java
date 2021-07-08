package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.Attendance;
import com.crm.pvt.hapinicrm.models.breakdb;
import com.crm.pvt.hapinicrm.prevalent.prevalent;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Break_Activity extends AppCompatActivity {
   public TextView timerText;
    public Timer timer;
//    public Chronometer chronometer;
    boolean running;
    private long pauseOffset;
    public TimerTask timerTask;
    Double time = 0.0;
    private Button stop,start;
    private String Time;
    private TextView timetext;
    private String User;
    String loginTime,breaktime;
    Attendance attendance;
    private boolean mTimerRunning;
    String  curTime,RestTime,StopRest,Difference;
    breakdb db;
    ArrayList arrayList;
    Calendar calendar = Calendar.getInstance();
    long restdiff,maindiff;

    private DatabaseReference att;
    private String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    private String imei = prevalent.CurrentOnlineEmloyee.getIMEI();
    private static final long START_TIME_IN_MILLIS = 600000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break);

        stop = findViewById(R.id.stop);
        timetext = findViewById(R.id.time);
        timerText = (TextView) findViewById(R.id.timer);

//        chronometer = findViewById(R.id.chronometer);
//        chronometer.setFormat("Time: %s");
//        chronometer.setBase(SystemClock.elapsedRealtime());
//

        timer = new Timer();
        startTimer();
        RestTime = getIntent().getStringExtra("RestTime");



        att = FirebaseDatabase.getInstance().getReference().child("Attendance");

        db = new breakdb(getApplicationContext());
        arrayList = db.getall();





        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                StopRest = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());

                DateFormat format = new SimpleDateFormat("hh:mm aa");
                Date d1 = null;
                try {
                    d1 = format.parse(RestTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date d2 = null;
                try {
                    d2 = format.parse(StopRest);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long diff = d2.getTime()-d1.getTime();
                long hr =    diff/(1000*60*60);
                long min = diff / (60 * 1000) % 60;
                restdiff = diff;


                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Attendance").child(prevalent.CurrentOnlineEmloyee.getIMEI());
                HashMap<String ,Object> hashMap = new HashMap<>();

                hashMap.put("RestTime",hr);

                ref.child(date).updateChildren(hashMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Break_Activity.this, "Rest Time has Stopped..", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),Emp_settings_Activity.class));
                                    finish();
                                }
                            }
                        });



//                if(!Time.isEmpty()){
//                    db.addtext(Time);
//                    Toast.makeText(Break_Activity.this, "Break Finished" + arrayList.size(), Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(),Emp_settings_Activity.class));
//











            }
        });



    }

    @Override
    public void onBackPressed() {

    }





    void readloginTime(){
        att.child(imei).child(date)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        String time = snapshot.child("Time").getValue(String.class);
                        String logtime = snapshot.child("Logout").getValue(String.class);
                        try {
                            calculatemain(time,logtime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        try {
                            calculate();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void calculatemain(String time, String logtime) throws ParseException {
        DateFormat format = new SimpleDateFormat("hh:mm aa");
        Date d1 = format.parse(time);
        Date d2 = format.parse(logtime);
        long diff1 = d2.getTime()-d1.getTime();
        maindiff = diff1;
    }


    public void calculate() throws ParseException {
        DateFormat format = new SimpleDateFormat("hh:mm aa");
        Date d1 = format.parse(RestTime);
        Date d2 = format.parse(StopRest);
        long diff = d2.getTime()-d1.getTime();
        long hr = diff/(1000*60*60);
        long min = diff / (60 * 1000) % 60;


    }



    private void calculaterest() throws ParseException {

        DateFormat readFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        DateFormat writeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Date date1 = null;
        Date date2 = null;

        String in,out;


        try {
            date1 = readFormat.parse(RestTime);
            date2 = readFormat.parse(StopRest);

        } catch (ParseException e) {
        }

        if (date1 != null && date2 != null) {
            in = writeFormat.format(date1);
            out = writeFormat.format(date2);
        }
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(writeFormat.format(date1));
            d2 = format.parse(writeFormat.format(date2));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // Get msec from each, and subtract.
        long diff = d2.getTime() - d1.getTime();

        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        long hour = TimeUnit.MILLISECONDS.toHours(diff);

//        timetext.setText(hour+"  "+minutes);



    }


    private void startTimer()
    {
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        time++;
                        timerText.setText(getTimerText());


                    }
                });
            }

        };
        timer.scheduleAtFixedRate(timerTask, 0 ,1000);

    }

    private String getTimerText()
    {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }
    private String formatTime(int seconds, int minutes, int hours)
    {
        return String.format("%02d",hours) + " : " + String.format("%02d",minutes) + " : " + String.format("%02d",seconds);
    }



}