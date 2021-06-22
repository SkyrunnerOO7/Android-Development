package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.crm.pvt.hapinicrm.models.Employee;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

public class Employee_Active_user extends AppCompatActivity {

    private RecyclerView list;
    private DatabaseReference dbref;
    private DatabaseReference empref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_active_user);

        list = findViewById(R.id.rv1);
        list.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        EmployeeFirebase();
    }

    public void EmployeeFirebase() {
        FirebaseRecyclerOptions<Employee> empoptions =
                new FirebaseRecyclerOptions.Builder<Employee>()
                        .setQuery(empref, Employee.class)
                        .build();

        FirebaseRecyclerAdapter<Employee, ActiveUserActivity.EmplistViewHolder> empadapter = new FirebaseRecyclerAdapter<Employee, ActiveUserActivity.EmplistViewHolder>(empoptions) {
            @Override
            protected void onBindViewHolder(@NonNull ActiveUserActivity.EmplistViewHolder holder, int position, @NonNull Employee model) {
                holder.Username.setText("Name : "+model.getName());
                holder.Passcode.setText("IMEI : "+model.getIMEI());
                holder.password.setText("password : "+model.getPassword());
                holder.mailED.setText("MailID : " +model.getMail());
                holder.city.setText("City : " +model.getCity());
                holder.phone.setText("Phone : " +model.getPhone());
                holder.profile.setText("profile : " + "Employee");

                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "Yes",
                                "No"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(Employee_Active_user.this);
                        builder.setTitle("Sure want to Delete this Employee profile ?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if(i==0){
                                    String uID = getRef(position).getKey();
                                    RemoveEmp(uID);

                                }else{
                                    finish();
                                }

                            }
                        });
                        builder.show();
                    }
                });


            }


            @NonNull

            @Override
            public ActiveUserActivity.EmplistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_display_layout,parent,false);
                return new ActiveUserActivity.EmplistViewHolder(view);
            }
        };

        list.setAdapter(empadapter);
        empadapter.startListening();
    }

    public static class EmplistViewHolder extends RecyclerView.ViewHolder{

        public TextView Username,Passcode,mailED,password,profile,city,phone;
        public Button delete;

        public EmplistViewHolder(@NonNull View itemView) {
            super(itemView);

            Username = itemView.findViewById(R.id.name_emp);
            Passcode = itemView.findViewById(R.id.imei_emp);
            mailED = itemView.findViewById(R.id.mail_emp);
            password = itemView.findViewById(R.id.password_emp);
            delete = itemView.findViewById(R.id.delete_emp);
            profile = itemView.findViewById(R.id.profile_emp);
            city = itemView.findViewById(R.id.city_emp);
            phone = itemView.findViewById(R.id.phone_emp);
        }


    }


    private void RemoveEmp(String uID) {
        empref.child(uID).removeValue();
    }
}