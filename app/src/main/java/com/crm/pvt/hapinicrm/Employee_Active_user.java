package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.Employee;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Employee_Active_user extends AppCompatActivity {

    private RecyclerView list;
    private DatabaseReference dbref;
    private DatabaseReference empref;
    Query query1,query2,query3;
    private String orderby = "Name";
    private int count;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_active_user);

        list = findViewById(R.id.rv1);
        list.setLayoutManager(new LinearLayoutManager(this));

        text = findViewById(R.id.count);

        empref = FirebaseDatabase.getInstance().getReference().child("Employee");
        query1 = empref.orderByChild("City");
        query2 = empref.orderByChild("Name");





    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sorting_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.citys:
                EmployeeFirebase1();
                break;

            case R.id.names:
                EmployeeFirebase2();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EmployeeFirebase();

    }

    public void EmployeeFirebase() {

        empref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                count = (int) snapshot.getChildrenCount();
                text.setText("count :"+count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    public void EmployeeFirebase1() {
        empref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                count = (int) snapshot.getChildrenCount();
                text.setText("count :"+count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<Employee> empoptions =
                new FirebaseRecyclerOptions.Builder<Employee>()
                        .setQuery(query1, Employee.class)
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


    public void EmployeeFirebase2() {

        empref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                count = (int) snapshot.getChildrenCount();
                text.setText("count :"+count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<Employee> empoptions =
                new FirebaseRecyclerOptions.Builder<Employee>()
                        .setQuery(query2, Employee.class)
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