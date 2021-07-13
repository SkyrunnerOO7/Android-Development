package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.Admin;
import com.crm.pvt.hapinicrm.models.Attendance;
import com.crm.pvt.hapinicrm.prevalent.prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Attendance_Activity extends AppCompatActivity {

    TextView passcode,Name,email,attendance;
    DatabaseReference empref;
    String passcode_i;
    String date;
    String imei;
    String Time;
    private RecyclerView list;
    DatabaseReference att;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);


        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        passcode_i = intent.getStringExtra("Passcode");

        attendance = findViewById(R.id.attendance_text);
        empref = FirebaseDatabase.getInstance().getReference().child("Employee");
        att = FirebaseDatabase.getInstance().getReference().child("Attendance").child(passcode_i);

        list = findViewById(R.id.attrv);
        list.setLayoutManager(new LinearLayoutManager(this));






        showattendance(passcode_i);












//        String profile_i = intent.getStringExtra("profile");
//        att.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull  DataSnapshot snapshot) {
//                imei = snapshot.getKey();
//                if(imei.equals(passcode_i)){
//                    date = snapshot.child(imei).getKey();
//                    Time = (String) snapshot.child(imei).child("Time").getValue();
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull  DatabaseError error) {
//
//            }
//        });
//        attendance.setText("date : "+ date + "  Time:  "+ Time);








    }
    public void showattendance(String s){

        Query query = att.orderByChild("IMEI").startAt(s).endAt(s+'\uf8ff');

        FirebaseRecyclerOptions<Attendance> options =
                new FirebaseRecyclerOptions.Builder<Attendance>()
                        .setQuery(query, Attendance.class)
                        .build();

        FirebaseRecyclerAdapter<Attendance,AttendanceViewHolder> adapter = new FirebaseRecyclerAdapter<Attendance, AttendanceViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull  Attendance_Activity.AttendanceViewHolder holder, int position, @NonNull  Attendance model) {


                if(model.getIMEI().equals(passcode_i))
                {
                    holder.DateD.setText(model.getDate());
                    holder.TimeD.setText(model.getTime());
                    holder.logoutD.setText(model.getLogout());
                    holder.countD.setText(""+model.getBreakTime());

                }
                else
                {
                    Toast.makeText(Attendance_Activity.this, "Data not present", Toast.LENGTH_SHORT).show();
                }
                /*holder.DateD.setText(model.getDate());
                holder.TimeD.setText(model.getTime());
                holder.logoutD.setText(model.getLogout());
                holder.countD.setText(""+model.getBreakTime());*/



            }

            @NonNull
            @Override
            public AttendanceViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_layout,parent,false);
                  return new AttendanceViewHolder(view);
            }
        };

            list.setAdapter(adapter);
            adapter.startListening();


//        FirebaseRecyclerAdapter<Admin, ActiveUserActivity.AdminlistViewHolder> adpater = new FirebaseRecyclerAdapter<Admin, ActiveUserActivity.AdminlistViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(ActiveUserActivity.AdminlistViewHolder holder, int position, Admin model) {
//                holder.Username.setText("Name : "+model.getName());
//                holder.Passcode.setText("Passcode : "+model.getPasscode());
//                holder.password.setText("password : "+model.getPassword());
//                holder.mailED.setText("MailID : " +model.getEmail());
//                holder.city.setText("City : " +model.getCity());
//                holder.phone.setText("Phone : " +model.getPhone());
//                holder.profile.setText("profile : " + "Admin");
//                Picasso.get().load(model.getImage()).into(holder.image);
//
//
//                holder.att.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(getApplicationContext(),Attendance_Activity.class);
//                        intent.putExtra("Passcode",model.getPasscode());
//                        intent.putExtra("profile","Admin");
//                    }
//                });
//
//
//
//
//            }
//
//            @Override
//            public ActiveUserActivity.AdminlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_layout,parent,false);
//                return new ActiveUserActivity.AdminlistViewHolder(view);
//            }
//        };
//
//        list.setAdapter(adpater);
//        adpater.startListening();


    }
    public static class AttendanceViewHolder extends RecyclerView.ViewHolder{

        public TextView DateT,TimeT,DateD,TimeD,logoutT,logoutD,count,countD;

        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);

           DateT = itemView.findViewById(R.id.Date_text);
           TimeT = itemView.findViewById(R.id.Time_text);
           DateD = itemView.findViewById(R.id.Ddata_text);
           TimeD = itemView.findViewById(R.id.TimeD_text);
           logoutT = itemView.findViewById(R.id.Logout_text);
           logoutD = itemView.findViewById(R.id.logoutD_text);
           count = itemView.findViewById(R.id.break_btn);
           countD = itemView.findViewById(R.id.breakDt);

        }


    }


}