package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.os.Handler;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.Employee;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;


import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EmployeeDashboardActivity extends AppCompatActivity {
    BottomNavigationView bnv;

    public TextView timerText;
    public Timer timer;
    public TimerTask timerTask;
    Double time = 0.0;
    boolean doubleBackToExitPressedOnce= false;
    private TextView logbuton;

    Handler handler;
    Runnable r;
    public static final String SHARED_PREFS = "sharedPrefsAttendance";
    public static final String SHARED_PREFS1 = "sharedPrefsAttendance___";
    public static final String text = "text1";
    public String IMEI_emp,check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_dashboard);

        check="false";
        timerText = (TextView) findViewById(R.id.time_employee_dashboard);


        bnv=(BottomNavigationView)findViewById(R.id.bottomNavigation);

        Intent intent = getIntent();
        IMEI_emp = intent.getStringExtra("IMEI");
        check = intent.getStringExtra("stop");


        // to open home fragment Bydefault
        //getSupportFragmentManager().beginTransaction().replace(R.id.FrameConatiner,new fragment_calling(IMEI_emp)).commit();
        // To add timer

        if(check.equals("false"))
        {
            Intent i =new Intent(EmployeeDashboardActivity.this,callingActivity.class);
            i.putExtra("IMEI",IMEI_emp);
            startActivity(i);
            finish();

        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameConatiner,new fragment_feedback()).commit();
        }

        timer = new Timer();
        startTimer();


        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                finish();
              startActivity(new Intent(EmployeeDashboardActivity.this,LoginActivity.class));

            }
        };
        startHandler();

        //to select home icon as default
        //int i=2131362141;
        //bnv.setSelectedItemId(i);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                Fragment temp=null;

                switch (item.getItemId())
                {
                    case R.id.menu_home : //temp=new fragment_calling_feedback(IMEI_emp);
                        //getSupportFragmentManager().beginTransaction().replace(R.id.FrameConatiner,temp).commit();

                        Intent intent1=new Intent(EmployeeDashboardActivity.this,callingActivity.class);
                        intent1.putExtra("IMEI",IMEI_emp);
                        startActivity(intent1);

                        break;
                    case R.id.menu_feedback: temp=new fragment_feedback();
                        getSupportFragmentManager().beginTransaction().replace(R.id.FrameConatiner,temp).commit();
                        break;
                    case R.id.menu_profile: //temp=new EmployeeProfileFragment();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("IMEI", IMEI_emp);
//                        temp.setArguments(bundle);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.FrameConatiner,temp).commit();
                        Intent intent11=new Intent(EmployeeDashboardActivity.this,Emp_settings_Activity.class);
                        startActivity(intent11);
                        break;
                    case R.id.menu_support :
                        Intent intent=new Intent(EmployeeDashboardActivity.this,SupportActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_rate_us : temp=new fragment_rate_us();
                        getSupportFragmentManager().beginTransaction().replace(R.id.FrameConatiner,temp).commit();
                        break;
                }

                return true;
            }
        });






//        logbuton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    timer.cancel();
//                    String Etime = getTimerText();
//                    Intent i = new Intent(getApplicationContext(),Break_Activity.class);
//                    i.putExtra("Time",Etime);
//                    i.putExtra("loginTime",Etime);
//                    startActivity(i);
//            }
//        });

    }






    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        stopHandler();
        startHandler();
    }

    private void stopHandler() {
        handler.removeCallbacks(r);
    }

    private void startHandler() {
        handler.postDelayed(r, 600000);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            this.finishAffinity();
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onStart() {




        // added new Code to pop up calling Fragment
//        fragment_attendance fragment_attendance = new fragment_attendance(IMEI_emp);
//        fragment_attendance.show(getSupportFragmentManager(), "MyFragment");

//
            if(isFirstTime()) {
                // Code to pop up attendance activicty
                fragment_attendance fragment_attendance = new fragment_attendance(IMEI_emp);
                fragment_attendance.show(getSupportFragmentManager(), "MyFragment");
            }
//            else{
//                fragment_calling fragment_calling = new fragment_calling(IMEI_emp);
//               fragment_calling.show(getSupportFragmentManager(), "TAG");
//            }

        super.onStart();
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
}
