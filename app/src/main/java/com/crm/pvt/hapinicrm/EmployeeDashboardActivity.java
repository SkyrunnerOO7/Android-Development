package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.Employee;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class EmployeeDashboardActivity extends AppCompatActivity {
    BottomNavigationView bnv;
    ImageView refresh;
    public TextView timerText;
    public Timer timer,timer1;
    public TimerTask timerTask,timerTask1;
    Double time = 0.0;
    public Double time1 = 0.0;
    RelativeLayout relativeLayout;
    boolean doubleBackToExitPressedOnce= false;


    // here, timer will display on screen
    //and timer1 will calculate 10 mins and after every touch it will be set to 0
    public EmployeeDashboardActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_dashboard);

        timerText = (TextView) findViewById(R.id.time_employee_dashboard);
        refresh=(ImageView)findViewById(R.id.refresh_employee_dashboard);
        bnv=(BottomNavigationView)findViewById(R.id.bottomNavigation);
        relativeLayout=(RelativeLayout)findViewById(R.id.layout_emp_dashboard);
        // to open home fragment Bydefault
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameConatiner,new fragment_calling()).commit();
        // To add timer
        timer = new Timer();
        startTimer();
        timer1 = new Timer();
        startTimer1();
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                timerTask1.cancel();
                time1 = 0.0;
                startTimer1();
                return true;
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EmployeeDashboardActivity.this,"Refresh",Toast.LENGTH_SHORT).show();
                timerTask1.cancel();
                time1 = 0.0;
                startTimer1();
            }
        });


        //to select home icon as default
        int i=2131362141;
        bnv.setSelectedItemId(i);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                Fragment temp=null;
                timerTask1.cancel();
                time1 = 0.0;
                startTimer1();
                switch (item.getItemId())
                {
                    case R.id.menu_home : temp=new fragment_calling();
                        getSupportFragmentManager().beginTransaction().replace(R.id.FrameConatiner,temp).commit();

                        break;
                    case R.id.menu_feedback: temp=new fragment_feedback();
                        getSupportFragmentManager().beginTransaction().replace(R.id.FrameConatiner,temp).commit();
                        break;
                    case R.id.menu_profile: temp=new EmployeeProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.FrameConatiner,temp).commit();
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

    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            this.finishAffinity();
            super.onBackPressed();
            timerTask1.cancel();
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
    protected void onStart() {
        fragment_attendance fragment_attendance = new fragment_attendance();
        fragment_attendance.show(getSupportFragmentManager(),"MyFragment");
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



    private void startTimer1()
    {
        timerTask1 = new TimerTask()
        {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        time1++;
                        //timerText1.setText(getTimerText());

                        if(getTimerText1().equals("00 : 10 : 00"))
                        {
                            Intent i=new Intent(EmployeeDashboardActivity.this,LoginActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });
            }
        };
        timer1.scheduleAtFixedRate(timerTask1, 0 ,1000);

    }

    private String getTimerText1()
    {
        int rounded = (int) Math.round(time1);

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
