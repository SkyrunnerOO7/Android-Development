package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.TooltipCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;


import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.Admin;
import com.crm.pvt.hapinicrm.models.Employee;
import com.crm.pvt.hapinicrm.prevalent.prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
//import com.squareup.picasso.Picasso;

/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class ActiveUserActivity extends AppCompatActivity {

    private RecyclerView list;
    private DatabaseReference dbref;
    private DatabaseReference empref;
    //private DatabaseReference databaseReference;
    private String parentDBname ="Admin";
    private SwitchCompat switchCompat;
    private int Tocall = 0;
    private int countemp,countadmin;
    private TextView count;
    private Button sorting;
    public String pdf;
//    private EditText inputtext;
//    ImageButton img;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_user);
        getSupportActionBar().setTitle("Active Users");

        switchCompat = findViewById(R.id.switch1);
        switchCompat.setChecked(true);
        sorting = findViewById(R.id.sortAU);








        count = findViewById(R.id.size);









//        dbref = FirebaseDatabase.getInstance().getReference().child("Admin");
//        empref = FirebaseDatabase.getInstance().getReference().child("Employee");
//
        list = findViewById(R.id.rv);
        list.setLayoutManager(new LinearLayoutManager(this));













    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(switchCompat.getText()=="Admin"){
                    adminFirebasesearch(s);
                }else if(switchCompat.getText()=="Employee"){
                    EmployeeFirebasesearch(s);
                }else{
                    Toast.makeText(ActiveUserActivity.this, "please select a User", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if(switchCompat.getText()=="Admin"){
                    adminFirebasesearch(s);
                }else if(switchCompat.getText()=="Employee"){
                    EmployeeFirebasesearch(s);
                }else{
                    Toast.makeText(ActiveUserActivity.this, "please select a User", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });



        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onStart() {
        super.onStart();
        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchCompat.isChecked()){
                    dbref = FirebaseDatabase.getInstance().getReference().child("Admin");
                    adminFirebase();
                    count.setText("Admin count : "+countadmin);
                    switchCompat.setText("Admin");

                    switchCompat.setChecked(true);
                }else{
                    empref = FirebaseDatabase.getInstance().getReference().child("Employee");
                    EmployeeFirebase();
                    switchCompat.setText("Employee");
                    count.setText("Employee Count: "+countemp);
                    switchCompat.setChecked(false);

                }
            }
        });

        sorting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence sort[] = new CharSequence[]{
                        "By City",
                        "BY Name"
                };
                AlertDialog.Builder builder2 = new AlertDialog.Builder(ActiveUserActivity.this);
                builder2.setTitle("Category ");
                builder2.setItems(sort, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            if(switchCompat.isChecked()){
                                adminFirebaseSort();
                                switchCompat.setChecked(true);
                            }else {
                                EmployeeFirebaseSort();
                                switchCompat.setChecked(false);
                            }
                        }else{
                            if(switchCompat.isChecked()){
                                adminFirebaseSortName();
                                switchCompat.setChecked(true);
                            }else {
                                EmployeeFirebaseSortName();
                                switchCompat.setChecked(false);
                            }

                        }
                    }
                });
                builder2.show();
            }
        });

    }

    public void adminFirebase(){
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                countadmin = (int) snapshot.getChildrenCount();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<Admin> options =
                new FirebaseRecyclerOptions.Builder<Admin>()
                        .setQuery(dbref, Admin.class)
                        .build();

        FirebaseRecyclerAdapter<Admin, AdminlistViewHolder> adpater = new FirebaseRecyclerAdapter<Admin, AdminlistViewHolder>(options) {
            @Override
            protected void onBindViewHolder(AdminlistViewHolder holder, int position, Admin model) {
                holder.Username.setText("Name : "+model.getName());
                holder.Passcode.setText("Passcode : "+model.getPasscode());
                holder.password.setText("password : "+model.getPassword());
                holder.mailED.setText("MailID : " +model.getEmail());
                holder.city.setText("City : " +model.getCity());
                holder.phone.setText("Phone : " +model.getPhone());
                holder.profile.setText("profile : " + "Admin");


                Picasso.get().load(model.getImage()).into(holder.image);
                holder.DownloadUser.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ActiveUserActivity.this, "Downloaded", Toast.LENGTH_SHORT).show();
                        createAdminPdf(model.getName(),model.getPasscode(),model.getPassword(),model.getEmail(),model.getCity(),model.getPhone());
                    }
                });


                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "Yes",
                                "No"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActiveUserActivity.this);
                        builder.setTitle("Sure want to Delete this Admin profile ?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if(i==0){
                                    String uID = getRef(position).getKey();
                                    RemoveAdmin(uID);

                                }else{
                                    finish();
                                }

                            }
                        });
                        builder.show();
                    }
                });

            }

            @Override
            public AdminlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_layout,parent,false);
                return new AdminlistViewHolder(view);
            }
        };

        list.setAdapter(adpater);
        adpater.startListening();


    }

    public void adminFirebasesearch(String s){
        Query query = dbref.orderByChild("Name").startAt(s).endAt(s+'\uf8ff');



        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                countadmin = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<Admin> options =
                new FirebaseRecyclerOptions.Builder<Admin>()
                        .setQuery(query, Admin.class)
                        .build();

        FirebaseRecyclerAdapter<Admin, AdminlistViewHolder> adpater = new FirebaseRecyclerAdapter<Admin, AdminlistViewHolder>(options) {
            @Override
            protected void onBindViewHolder(AdminlistViewHolder holder, int position, Admin model) {
                holder.Username.setText("Name : "+model.getName());
                holder.Passcode.setText("Passcode : "+model.getPasscode());
                holder.password.setText("password : "+model.getPassword());
                holder.mailED.setText("MailID : " +model.getEmail());
                holder.city.setText("City : " +model.getCity());
                holder.phone.setText("Phone : " +model.getPhone());
                holder.profile.setText("profile : " + "Admin");
                Picasso.get().load(model.getImage()).into(holder.image);


                holder.DownloadUser.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ActiveUserActivity.this, "Downloaded", Toast.LENGTH_SHORT).show();
                        createAdminPdf(model.getName(),model.getPasscode(),model.getPassword(),model.getEmail(),model.getCity(),model.getPhone());
                    }
                });


                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "Yes",
                                "No"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActiveUserActivity.this);
                        builder.setTitle("Sure want to Delete this Admin profile ?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if(i==0){
                                    String uID = getRef(position).getKey();
                                    RemoveAdmin(uID);

                                }else{
                                    finish();
                                }

                            }
                        });
                        builder.show();
                    }
                });

            }

            @Override
            public AdminlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_layout,parent,false);
                return new AdminlistViewHolder(view);
            }
        };

        list.setAdapter(adpater);
        adpater.startListening();


    }
    public void adminFirebaseSort(){
        Query query = dbref.orderByChild("City");



        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                countadmin = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<Admin> options =
                new FirebaseRecyclerOptions.Builder<Admin>()
                        .setQuery(query, Admin.class)
                        .build();

        FirebaseRecyclerAdapter<Admin, AdminlistViewHolder> adpater = new FirebaseRecyclerAdapter<Admin, AdminlistViewHolder>(options) {
            @Override
            protected void onBindViewHolder(AdminlistViewHolder holder, int position, Admin model) {
                holder.Username.setText("Name : "+model.getName());
                holder.Passcode.setText("Passcode : "+model.getPasscode());
                holder.password.setText("password : "+model.getPassword());
                holder.mailED.setText("MailID : " +model.getEmail());
                holder.city.setText("City : " +model.getCity());
                holder.phone.setText("Phone : " +model.getPhone());
                holder.profile.setText("profile : " + "Admin");
                Picasso.get().load(model.getImage()).into(holder.image);

                holder.DownloadUser.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ActiveUserActivity.this, "Downloaded", Toast.LENGTH_SHORT).show();
                        createAdminPdf(model.getName(),model.getPasscode(),model.getPassword(),model.getEmail(),model.getCity(),model.getPhone());
                    }
                });



                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "Yes",
                                "No"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActiveUserActivity.this);
                        builder.setTitle("Sure want to Delete this Admin profile ?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if(i==0){
                                    String uID = getRef(position).getKey();
                                    RemoveAdmin(uID);

                                }else{
                                    finish();
                                }

                            }
                        });
                        builder.show();
                    }
                });

            }

            @Override
            public AdminlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_layout,parent,false);
                return new AdminlistViewHolder(view);
            }
        };

        list.setAdapter(adpater);
        adpater.startListening();


    }

    public void adminFirebaseSortName(){
        Query query = dbref.orderByChild("Name");



        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                countadmin = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<Admin> options =
                new FirebaseRecyclerOptions.Builder<Admin>()
                        .setQuery(query, Admin.class)
                        .build();

        FirebaseRecyclerAdapter<Admin, AdminlistViewHolder> adpater = new FirebaseRecyclerAdapter<Admin, AdminlistViewHolder>(options) {
            @Override
            protected void onBindViewHolder(AdminlistViewHolder holder, int position, Admin model) {
                holder.Username.setText("Name : "+model.getName());
                holder.Passcode.setText("Passcode : "+model.getPasscode());
                holder.password.setText("password : "+model.getPassword());
                holder.mailED.setText("MailID : " +model.getEmail());
                holder.city.setText("City : " +model.getCity());
                holder.phone.setText("Phone : " +model.getPhone());
                holder.profile.setText("profile : " + "Admin");
                Picasso.get().load(model.getImage()).into(holder.image);


                holder.DownloadUser.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ActiveUserActivity.this, "Downloaded", Toast.LENGTH_SHORT).show();
                        createAdminPdf(model.getName(),model.getPasscode(),model.getPassword(),model.getEmail(),model.getCity(),model.getPhone());
                    }
                });


                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "Yes",
                                "No"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActiveUserActivity.this);
                        builder.setTitle("Sure want to Delete this Admin profile ?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if(i==0){
                                    String uID = getRef(position).getKey();
                                    RemoveAdmin(uID);

                                }else{
                                    finish();
                                }

                            }
                        });
                        builder.show();
                    }
                });

            }

            @Override
            public AdminlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_layout,parent,false);
                return new AdminlistViewHolder(view);
            }
        };

        list.setAdapter(adpater);
        adpater.startListening();


    }

    public void EmployeeFirebase() {

        empref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                countemp = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<Employee> empoptions =
                new FirebaseRecyclerOptions.Builder<Employee>()
                        .setQuery(empref, Employee.class)
                        .build();

        FirebaseRecyclerAdapter<Employee,EmplistViewHolder> empadapter = new FirebaseRecyclerAdapter<Employee, EmplistViewHolder>(empoptions) {
            @Override
            protected void onBindViewHolder(@NonNull ActiveUserActivity.EmplistViewHolder holder, int position, @NonNull Employee model) {
                holder.Username.setText("Name : "+model.getName());
                holder.Passcode.setText("IMEI : "+model.getIMEI());
                holder.password.setText("password : "+model.getPassword());
                holder.mailED.setText("MailID : " +model.getMail());
                holder.city.setText("City : " +model.getCity());
                holder.phone.setText("Phone : " +model.getPhone());
                holder.profile.setText("profile : " + "Employee");
                holder.verifyemp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(ActiveUserActivity.this,VerifyEmployee.class);
                        i.putExtra("imei",model.getIMEI());
                        startActivity(i);
                    }
                });
                Picasso.get().load(model.getImage()).into(holder.profileimg1);

                holder.download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ActiveUserActivity.this, "Downloaded", Toast.LENGTH_SHORT).show();
                        DatabaseReference ref;
                        ref=FirebaseDatabase.getInstance().getReference().child("Attendance").child(model.getIMEI());
                        ref.addChildEventListener(new ChildEventListener() {
                            // Retrieve new posts as they are added to Firebase
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                                String date,time;
                                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();


                                date=newPost.get("Date").toString();
                                time=newPost.get("Time").toString();
                                createEmployeePdf(model.getName(),model.getIMEI(),model.getPassword(),model.getMail(),model.getCity(),model.getPhone(),model.getUrl(),date,time);

                            }

                            @Override
                            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }

                        });

                    }
                });

                holder.attE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),Attendance_Activity.class);
                        intent.putExtra("Passcode",model.getIMEI());
                        intent.putExtra("profile","Employee");
                        startActivity(intent);
                    }
                });
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "Yes",
                                "No"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActiveUserActivity.this);
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

                holder.add.setOnClickListener(view -> {
//                    holder.Limit.setText(String.valueOf(Integer.parseInt(holder.Limit.getText().toString()+1)));
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Employee");
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("DailyLimit",holder.Limit.getText().toString());
                    databaseReference.child(model.getIMEI()).updateChildren(map);

                    Toast.makeText(getApplicationContext(),"Limit Changed Successfully",Toast.LENGTH_SHORT).show();

                });



            }


            @NonNull

            @Override
            public EmplistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_display_layout,parent,false);
                return new EmplistViewHolder(view);
            }
        };

        list.setAdapter(empadapter);
        empadapter.startListening();
        }


    public void EmployeeFirebasesearch(String s) {
        Query q = empref.orderByChild("Name").startAt(s).endAt(s+'\uf8ff');

        empref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                countemp = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<Employee> empoptions =
                new FirebaseRecyclerOptions.Builder<Employee>()
                        .setQuery(q, Employee.class)
                        .build();

        FirebaseRecyclerAdapter<Employee,EmplistViewHolder> empadapter = new FirebaseRecyclerAdapter<Employee, EmplistViewHolder>(empoptions) {
            @Override
            protected void onBindViewHolder(@NonNull ActiveUserActivity.EmplistViewHolder holder, int position, @NonNull Employee model) {
                holder.Username.setText("Name : "+model.getName());
                holder.Passcode.setText("IMEI : "+model.getIMEI());
                holder.password.setText("password : "+model.getPassword());
                holder.mailED.setText("MailID : " +model.getMail());
                holder.city.setText("City : " +model.getCity());
                holder.phone.setText("Phone : " +model.getPhone());
                holder.profile.setText("profile : " + "Employee");
                holder.verifyemp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(ActiveUserActivity.this,VerifyEmployee.class);
                        i.putExtra("imei",model.getIMEI());
                        startActivity(i);
                    }
                });

                holder.download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ActiveUserActivity.this, "Downloaded", Toast.LENGTH_SHORT).show();
                        DatabaseReference ref;
                        ref=FirebaseDatabase.getInstance().getReference().child("Attendance").child(model.getIMEI());
                        ref.addChildEventListener(new ChildEventListener() {
                            // Retrieve new posts as they are added to Firebase
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                                String date,time;
                                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();


                                date=newPost.get("Date").toString();
                                time=newPost.get("Time").toString();
                                createEmployeePdf(model.getName(),model.getIMEI(),model.getPassword(),model.getMail(),model.getCity(),model.getPhone(),model.getUrl(),date,time);

                            }

                            @Override
                            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }

                        });

                    }
                });


                Picasso.get().load(model.getImage()).into(holder.profileimg1);

                holder.attE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(),Attendance_Activity.class));
                    }
                });

                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "Yes",
                                "No"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActiveUserActivity.this);
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
                holder.add.setOnClickListener(view -> {
//                    holder.Limit.setText(String.valueOf(Integer.parseInt(holder.Limit.getText().toString()+1)));
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Employee");
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("DailyLimit",holder.Limit.getText().toString());
                    databaseReference.child(model.getIMEI()).updateChildren(map);
                    Toast.makeText(getApplicationContext(),"Limit Changed Successfully",Toast.LENGTH_SHORT).show();
                });


            }


            @NonNull

            @Override
            public EmplistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_display_layout,parent,false);
                return new EmplistViewHolder(view);
            }
        };

        list.setAdapter(empadapter);
        empadapter.startListening();
    }

    public void EmployeeFirebaseSort() {
        Query q = dbref.orderByChild("City");

        empref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                countemp = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<Employee> empoptions =
                new FirebaseRecyclerOptions.Builder<Employee>()
                        .setQuery(q, Employee.class)
                        .build();

        FirebaseRecyclerAdapter<Employee,EmplistViewHolder> empadapter = new FirebaseRecyclerAdapter<Employee, EmplistViewHolder>(empoptions) {
            @Override
            protected void onBindViewHolder(@NonNull ActiveUserActivity.EmplistViewHolder holder, int position, @NonNull Employee model) {
                holder.Username.setText("Name : "+model.getName());
                holder.Passcode.setText("IMEI : "+model.getIMEI());
                holder.password.setText("password : "+model.getPassword());
                holder.mailED.setText("MailID : " +model.getMail());
                holder.city.setText("City : " +model.getCity());
                holder.phone.setText("Phone : " +model.getPhone());
                holder.profile.setText("profile : " + "Employee");
                holder.verifyemp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(ActiveUserActivity.this,VerifyEmployee.class);
                        i.putExtra("imei",model.getIMEI());
                        startActivity(i);
                    }
                });



                holder.download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ActiveUserActivity.this, "Downloaded", Toast.LENGTH_SHORT).show();
                        DatabaseReference ref;
                        ref=FirebaseDatabase.getInstance().getReference().child("Attendance").child(model.getIMEI());
                        ref.addChildEventListener(new ChildEventListener() {
                            // Retrieve new posts as they are added to Firebase
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                                String date,time;
                                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();


                                date=newPost.get("Date").toString();
                                time=newPost.get("Time").toString();
                                createEmployeePdf(model.getName(),model.getIMEI(),model.getPassword(),model.getMail(),model.getCity(),model.getPhone(),model.getUrl(),date,time);

                            }

                            @Override
                            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }

                        });

                    }
                });


                Picasso.get().load(model.getImage()).into(holder.profileimg1);

                holder.attE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(),Attendance_Activity.class));
                    }
                });

                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "Yes",
                                "No"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActiveUserActivity.this);
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

                holder.add.setOnClickListener(view -> {
//                    holder.Limit.setText(String.valueOf(Integer.parseInt(holder.Limit.getText().toString()+1)));
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Employee");
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("DailyLimit",holder.Limit.getText().toString());
                    databaseReference.child(model.getIMEI()).updateChildren(map);
                    Toast.makeText(getApplicationContext(),"Limit Changed Successfully",Toast.LENGTH_SHORT).show();
                });


            }


            @NonNull

            @Override
            public EmplistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_display_layout,parent,false);
                return new EmplistViewHolder(view);
            }
        };

        list.setAdapter(empadapter);
        empadapter.startListening();
    }

    public void EmployeeFirebaseSortName() {
        Query q = dbref.orderByChild("Name");

        empref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                countemp = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<Employee> empoptions =
                new FirebaseRecyclerOptions.Builder<Employee>()
                        .setQuery(q, Employee.class)
                        .build();

        FirebaseRecyclerAdapter<Employee,EmplistViewHolder> empadapter = new FirebaseRecyclerAdapter<Employee, EmplistViewHolder>(empoptions) {
            @Override
            protected void onBindViewHolder(@NonNull ActiveUserActivity.EmplistViewHolder holder, int position, @NonNull Employee model) {
                holder.Username.setText("Name : "+model.getName());
                holder.Passcode.setText("IMEI : "+model.getIMEI());
                holder.password.setText("password : "+model.getPassword());
                holder.mailED.setText("MailID : " +model.getMail());
                holder.city.setText("City : " +model.getCity());
                holder.phone.setText("Phone : " +model.getPhone());
                holder.profile.setText("profile : " + "Employee");

                holder.verifyemp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(ActiveUserActivity.this,VerifyEmployee.class);
                        i.putExtra("imei",model.getIMEI());
                        startActivity(i);
                    }
                });


                holder.download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ActiveUserActivity.this, "Downloaded", Toast.LENGTH_SHORT).show();
                        DatabaseReference ref;
                        ref=FirebaseDatabase.getInstance().getReference().child("Attendance").child(model.getIMEI());
                        ref.addChildEventListener(new ChildEventListener() {
                            // Retrieve new posts as they are added to Firebase
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                                String date,time;
                                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();


                                date=newPost.get("Date").toString();
                                time=newPost.get("Time").toString();
                                createEmployeePdf(model.getName(),model.getIMEI(),model.getPassword(),model.getMail(),model.getCity(),model.getPhone(),model.getUrl(),date,time);

                            }

                            @Override
                            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }

                        });

                    }
                });


                Picasso.get().load(model.getImage()).into(holder.profileimg1);

                holder.attE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(),Attendance_Activity.class));
                    }
                });

                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "Yes",
                                "No"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActiveUserActivity.this);
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

                holder.add.setOnClickListener(view -> {
//                    holder.Limit.setText(String.valueOf(Integer.parseInt(holder.Limit.getText().toString()+1)));
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Employee");
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("DailyLimit",holder.Limit.getText().toString());
                    databaseReference.child(model.getIMEI()).updateChildren(map);
                    Toast.makeText(getApplicationContext(),"Limit Changed Successfully",Toast.LENGTH_SHORT).show();
                });




            }


            @NonNull

            @Override
            public EmplistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_display_layout,parent,false);
                return new EmplistViewHolder(view);
            }
        };

        list.setAdapter(empadapter);
        empadapter.startListening();
    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createEmployeePdf(String name, String imei, String password, String mail, String city, String phone, String url1, String date, String time)
    {

        //bmp= BitmapFactory.decodeResource(getResources(), admin_profile_icon1);
        //scalebmp=Bitmap.createScaledBitmap(bmp,45,45,false);



        pdf="/"+imei+".pdf";
        PdfDocument myPdfDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(800,800,1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);
        Canvas canvas=myPage.getCanvas();
        Paint titlePaint1=new Paint();





        //titlePaint1.setTextColor(Color.parseColor("#006400"));

        titlePaint1.setTextAlign(Paint.Align.CENTER);
        titlePaint1.setTextSize(28);

        canvas.drawText(name,500,200,titlePaint1);
        canvas.drawText(imei,500,250,titlePaint1);
        canvas.drawText(password,500,300,titlePaint1);
        canvas.drawText(mail,500,350,titlePaint1);
        canvas.drawText(city,500,400,titlePaint1);
        canvas.drawText(phone,500,450,titlePaint1);
        canvas.drawText(date,500,500,titlePaint1);
        canvas.drawText(time,500,550,titlePaint1);


        int greenColorValue = Color.parseColor("#072f5f");

        titlePaint1.setColor(greenColorValue);
        titlePaint1.setTextSize(35);
        titlePaint1.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));


        canvas.drawText("Employee Profile",390,50,titlePaint1);
        titlePaint1.setTextSize(28);
        titlePaint1.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        canvas.drawText("Name: ",250,200,titlePaint1);
        canvas.drawText("IMEI: ",250,250,titlePaint1);
        canvas.drawText("Password: ",250,300,titlePaint1);
        canvas.drawText("Mail: ",250,350,titlePaint1);
        canvas.drawText("City: ",250,400,titlePaint1);
        canvas.drawText("Phone: ",250,450,titlePaint1);
        canvas.drawText("Date: ",250,500,titlePaint1);
        canvas.drawText("Login Time: ",250,550,titlePaint1);



        //canvas.drawBitmap(scalebmp,110,990,titlePaint1);



        titlePaint1.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));

        //canvas.drawLine(50,250,1050,250,myPaint);

        //canvas.drawLine(50,345,1050,345,myPaint);


        myPdfDocument.finishPage(myPage);

        String s = null;
        File d2= new File(getExternalFilesDir(s).getPath() + pdf);

        String myFilePath = Environment.getExternalStorageDirectory().getPath() + pdf;
        try {
            myPdfDocument.writeTo(new FileOutputStream(d2));
            Toast.makeText(this, "pdf write", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();

        }


        myPdfDocument.close();

        Intent i =new Intent(ActiveUserActivity.this,ViewUserDetailsPdf.class);
        i.putExtra("name",pdf);
        startActivity(i);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createAdminPdf(String name, String passcode, String password, String mail, String city, String phone)
    {

        //bmp= BitmapFactory.decodeResource(getResources(), admin_profile_icon1);
        //scalebmp=Bitmap.createScaledBitmap(bmp,45,45,false);



        pdf="/"+passcode+".pdf";
        PdfDocument myPdfDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(800,800,1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);
        Canvas canvas=myPage.getCanvas();
        Paint titlePaint1=new Paint();





        //titlePaint1.setTextColor(Color.parseColor("#006400"));

        titlePaint1.setTextAlign(Paint.Align.CENTER);
        titlePaint1.setTextSize(28);

        canvas.drawText(name,500,200,titlePaint1);
        canvas.drawText(passcode,500,250,titlePaint1);
        canvas.drawText(password,500,300,titlePaint1);
        canvas.drawText(mail,500,350,titlePaint1);
        canvas.drawText(city,500,400,titlePaint1);
        canvas.drawText(phone,500,450,titlePaint1);


        int greenColorValue = Color.parseColor("#072f5f");

        titlePaint1.setColor(greenColorValue);
        titlePaint1.setTextSize(38);
        titlePaint1.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));


        canvas.drawText("Admin Profile",390,50,titlePaint1);
        titlePaint1.setTextSize(28);
        titlePaint1.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        canvas.drawText("Name: ",250,200,titlePaint1);
        canvas.drawText("IMEI: ",250,250,titlePaint1);
        canvas.drawText("Password: ",250,300,titlePaint1);
        canvas.drawText("Mail: ",250,350,titlePaint1);
        canvas.drawText("City: ",250,400,titlePaint1);
        canvas.drawText("Phone: ",250,450,titlePaint1);



        //canvas.drawBitmap(scalebmp,110,990,titlePaint1);



        titlePaint1.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));

        //canvas.drawLine(50,250,1050,250,myPaint);

        //canvas.drawLine(50,345,1050,345,myPaint);


        myPdfDocument.finishPage(myPage);

        String s = null;
        File d2= new File(getExternalFilesDir(s).getPath() + pdf);

        String myFilePath = Environment.getExternalStorageDirectory().getPath() + pdf;
        try {
            myPdfDocument.writeTo(new FileOutputStream(d2));
            Toast.makeText(this, "pdf write", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();

        }


        myPdfDocument.close();

        Intent i =new Intent(ActiveUserActivity.this,ViewUserDetailsPdf.class);
        i.putExtra("name",pdf);
        startActivity(i);
    }



    public static class AdminlistViewHolder extends RecyclerView.ViewHolder{

        public TextView Username,Passcode,mailED,password,profile,city,phone;
        public Button delete,DownloadUser;
        public ImageView image;

        public AdminlistViewHolder(@NonNull View itemView) {
            super(itemView);

            Username = itemView.findViewById(R.id.user_name);
            Passcode = itemView.findViewById(R.id.passcode_imei);
            mailED = itemView.findViewById(R.id.emailText);
            password = itemView.findViewById(R.id.passwordED);
            delete = itemView.findViewById(R.id.delete_btn);
            profile = itemView.findViewById(R.id.profileED);
            city = itemView.findViewById(R.id.CityText);
            phone = itemView.findViewById(R.id.PhoneText);
            image = itemView.findViewById(R.id.admin_profile);

            DownloadUser=itemView.findViewById(R.id.downloadUser);



        }

    }

    public static class EmplistViewHolder extends RecyclerView.ViewHolder{

        public TextView Username,Passcode,mailED,password,profile,city,phone;
        public Button delete,attE,download,verifyemp;
        public ImageView profileimg1;
        public  EditText Limit;
        public ImageButton add;

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
            profileimg1=itemView.findViewById(R.id.emp_profile);
            attE = itemView.findViewById(R.id.att_btnE);
            download=itemView.findViewById(R.id.download_btnE);
            add = itemView.findViewById(R.id.Add);
            Limit = itemView.findViewById(R.id.limit);
            verifyemp=itemView.findViewById(R.id.verify_emp);
        }


    }





    private void RemoveEmp(String uID) {
        empref.child(uID).removeValue();
    }
    private void RemoveAdmin(String uID) {
        dbref.child(uID).removeValue();
    }
}