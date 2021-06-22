package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EmployeeDashboardActivity extends AppCompatActivity {
    BottomNavigationView bnv;
    ImageView refresh;
    Handler handler;
    Runnable r;
    boolean doubleBackToExitPressedOnce= false;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String text = "text";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_dashboard);

        refresh=(ImageView)findViewById(R.id.refresh_employee_dashboard);
        bnv=(BottomNavigationView)findViewById(R.id.bottomNavigation);


        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                finish();
              startActivity(new Intent(EmployeeDashboardActivity.this,LoginActivity.class));

            }
        };
        startHandler();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EmployeeDashboardActivity.this,"Refresh",Toast.LENGTH_SHORT).show();
            }
        });

        //to select home icon as default
        int i=2131362089;
        bnv.setSelectedItemId(i);

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                Fragment temp=null;

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
    public void onUserInteraction() {
        super.onUserInteraction();
        stopHandler();
        startHandler();
    }

    private void stopHandler() {
        handler.removeCallbacks(r);
    }

    private void startHandler() {
        handler.postDelayed(r, 6000);
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
    protected void onStart() {

        if(isFirstTime()) {
            // added new Code to pop up calling Fragment
            fragment_calling fragment_calling = new fragment_calling();
            fragment_calling.show(getSupportFragmentManager(), "TAG");
        }

          // Code to pop up attendance activicty
          fragment_attendance fragment_attendance = new fragment_attendance();
          fragment_attendance.show(getSupportFragmentManager(),"MyFragment");

        super.onStart();
    }

    private boolean isFirstTime() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
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
