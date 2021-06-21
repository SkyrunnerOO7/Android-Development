package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EmployeeDashboardActivity extends AppCompatActivity {
    BottomNavigationView bnv;
    ImageView refresh;
    boolean doubleBackToExitPressedOnce= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_dashboard);

        refresh=(ImageView)findViewById(R.id.refresh_employee_dashboard);
        bnv=(BottomNavigationView)findViewById(R.id.bottomNavigation);

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
        fragment_attendance fragment_attendance = new fragment_attendance();
        fragment_attendance.show(getSupportFragmentManager(),"MyFragment");
        super.onStart();
    }
}
