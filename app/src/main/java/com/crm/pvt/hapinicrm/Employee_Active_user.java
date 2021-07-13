package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.Admin_picture_Model;
import com.crm.pvt.hapinicrm.models.Employee;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.crm.pvt.hapinicrm.R.drawable.admin_profile_icon1;

public class Employee_Active_user extends AppCompatActivity {

    private RecyclerView list;
    //public DatabaseReference databaseReference;
    private DatabaseReference empref;
    Query query1,query2,query3;
    private String orderby = "Name";
    private int count;
    TextView text;
    private EditText inputtext;
    ImageButton img;
    //ImageView profileImageOfEmployee;
    public int z;
    public StorageReference storageReference;

    Bitmap bmp,scalebmp;
    String child1;
    public String pdf;
    private String stringFile = Environment.getExternalStorageDirectory().getPath() + File.separator + "Test.pdf";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_active_user);

        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());





        list = findViewById(R.id.rv1);
        list.setLayoutManager(new LinearLayoutManager(this));

        text = findViewById(R.id.count);

        empref = FirebaseDatabase.getInstance().getReference().child("Employee");
        query1 = empref.orderByChild("City");
        query2 = empref.orderByChild("Name");

        inputtext = findViewById(R.id.searchtextE);
        img = findViewById(R.id.searchbtnE);
       // profileImageOfEmployee=findViewById(R.id.emp_profile);


        //SetEmployeeProfilePicture();



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

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeeFirebasesearch();
            }
        });

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
                holder.area.setText("Area : "+model.getArea());

                if(model.getVerified().equals("true"))
                {
                    holder.verified.setVisibility(View.VISIBLE);
                }


                //Toast.makeText(Employee_Active_user.this, model.getArea(), Toast.LENGTH_SHORT).show();
                holder.verifyemp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(Employee_Active_user.this,VerifyEmployee.class);
                        i.putExtra("imei",model.getIMEI());
                        startActivity(i);
                    }
                });

                final String[] date = new String[1];


                holder.download.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(Employee_Active_user.this, "Downloaded", Toast.LENGTH_SHORT).show();
                        createEmployeePdf(model.getName(),model.getIMEI(),model.getPassword(),model.getMail(),model.getCity(),model.getPhone(),model.getUrl());





                    }
                });

                Picasso.get().load(model.getImage()).into(holder.profileimg1);

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

                holder.add.setOnClickListener(view -> {
                    if(holder.Limit.getText().toString().isEmpty())
                    {
                        Toast.makeText(Employee_Active_user.this, "Please enter Limit", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //holder.Limit.setText(String.valueOf(Integer.parseInt(holder.Limit.getText().toString()+1)));
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Employee");
                        HashMap<String,Object> map = new HashMap<>();
                        map.put("DailyLimit",holder.Limit.getText().toString());
                        databaseReference.child(model.getIMEI()).updateChildren(map);
                        Toast.makeText(getApplicationContext(),"Limit Changed Successfully",Toast.LENGTH_SHORT).show();
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

    public void EmployeeFirebasesearch() {

        String s = inputtext.getText().toString();
        Query query = empref.orderByChild("Name").startAt(s);

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
                        .setQuery(query, Employee.class)
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
                holder.area.setText("Area : "+model.getArea());

                if(model.getVerified().equals("true"))
                {
                    holder.verified.setVisibility(View.VISIBLE);
                }
                holder.verifyemp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(Employee_Active_user.this,VerifyEmployee.class);
                        i.putExtra("imei",model.getIMEI());
                        startActivity(i);
                    }
                });


                holder.download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Toast.makeText(Employee_Active_user.this, "Downloaded", Toast.LENGTH_SHORT).show();
                        createEmployeePdf(model.getName(),model.getIMEI(),model.getPassword(),model.getMail(),model.getCity(),model.getPhone(),model.getUrl());
                        /*DatabaseReference ref;
                        ref=FirebaseDatabase.getInstance().getReference().child("Attendance").child(model.getIMEI());
                        ref.addChildEventListener(new ChildEventListener() {
                            // Retrieve new posts as they are added to Firebase
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

                        });*/

                    }
                });

                Picasso.get().load(model.getImage()).into(holder.profileimg1);

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
            public ActiveUserActivity.EmplistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_display_layout,parent,false);
                return new ActiveUserActivity.EmplistViewHolder(view);
            }
        };

        list.setAdapter(empadapter);
        empadapter.startListening();
    }

    public void EmployeeFirebase1() {
        Query query = empref.orderByChild("Name");
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
                        .setQuery(query, Employee.class)
                        .build();

        FirebaseRecyclerAdapter<Employee, ActiveUserActivity.EmplistViewHolder> empadapter = new FirebaseRecyclerAdapter<Employee, ActiveUserActivity.EmplistViewHolder>(empoptions) {
            @Override
            protected void onBindViewHolder(@NonNull ActiveUserActivity.EmplistViewHolder holder, int position, @NonNull Employee model) {

                //Toast.makeText(Employee_Active_user.this, model.getName(), Toast.LENGTH_SHORT).show();
                holder.Username.setText("Name : "+model.getName());
                holder.Passcode.setText("IMEI : "+model.getIMEI());
                holder.password.setText("password : "+model.getPassword());
                holder.mailED.setText("MailID : " +model.getMail());
                holder.city.setText("City : " +model.getCity());
                holder.phone.setText("Phone : " +model.getPhone());
                holder.profile.setText("profile : " + "Employee");
                holder.area.setText("Area : "+model.getArea());

                if(model.getVerified().equals("true"))
                {
                    holder.verified.setVisibility(View.VISIBLE);
                }
                holder.verifyemp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(Employee_Active_user.this,VerifyEmployee.class);
                        i.putExtra("imei",model.getIMEI());
                        startActivity(i);
                    }
                });


                holder.download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(Employee_Active_user.this, "Downloaded", Toast.LENGTH_SHORT).show();
                        createEmployeePdf(model.getName(),model.getIMEI(),model.getPassword(),model.getMail(),model.getCity(),model.getPhone(),model.getUrl());
                    }
                });

                Picasso.get().load(model.getImage()).into(holder.profileimg1);

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
                holder.area.setText("Area : "+model.getArea());

                if(model.getVerified().equals("true"))
                {
                    holder.verified.setVisibility(View.VISIBLE);
                }
                holder.verifyemp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(Employee_Active_user.this,VerifyEmployee.class);
                        i.putExtra("imei",model.getIMEI());
                        startActivity(i);
                    }
                });


                holder.download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createEmployeePdf(model.getName(),model.getIMEI(),model.getPassword(),model.getMail(),model.getCity(),model.getPhone(),model.getUrl());
                    }
                });

                Picasso.get().load(model.getImage()).into(holder.profileimg1);

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
            public ActiveUserActivity.EmplistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_display_layout,parent,false);
                return new ActiveUserActivity.EmplistViewHolder(view);
            }
        };

        list.setAdapter(empadapter);
        empadapter.startListening();
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    public void createEmployeePdf(String name,String imei,String password,String mail,String city,String phone,String url1)
    {

        //bmp= BitmapFactory.decodeResource(getResources(), admin_profile_icon1);
        //scalebmp=Bitmap.createScaledBitmap(bmp,45,45,false);



        pdf="/"+imei+".pdf";
        PdfDocument myPdfDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(800,800,1).create();
        PdfDocument.PageInfo myPageInfo2 = new PdfDocument.PageInfo.Builder(800,800,2).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);
        Canvas canvas=myPage.getCanvas();
        Paint titlePaint1=new Paint();

        //titlePaint1.setTextColor(Color.parseColor("#006400"));

        titlePaint1.setTextAlign(Paint.Align.CENTER);
        titlePaint1.setTextSize(28);




        /*DatabaseReference ref;
        ref=FirebaseDatabase.getInstance().getReference().child("Attendance").child(imei);
        ref.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to Firebase
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                String date,time;
                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();


                date=newPost.get("Date").toString();
                time=newPost.get("Time").toString();
                canvas.drawText("Date: ",250,500,titlePaint1);
                canvas.drawText("Login Time: ",250,550,titlePaint1);

                canvas.drawText(date,500,500,titlePaint1);
                canvas.drawText(time,500,550,titlePaint1);
                //y += 50;
                if(y>700)
                {
                    PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo2);
                    Canvas canvas2=myPage.getCanvas();
                    Paint titlePaint2=new Paint();

                }



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

                //Toast.makeText(Employee_Active_user.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }

        });*/


        canvas.drawText(name,500,200,titlePaint1);
        canvas.drawText(imei,500,250,titlePaint1);
        canvas.drawText(password,500,300,titlePaint1);
        canvas.drawText(mail,500,350,titlePaint1);
        canvas.drawText(city,500,400,titlePaint1);
        canvas.drawText(phone,500,450,titlePaint1);
        //canvas.drawText(date,500,500,titlePaint1);
        //canvas.drawText(time,500,550,titlePaint1);


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
        /*canvas.drawText("Date: ",250,500,titlePaint1);
        canvas.drawText("Login Time: ",250,550,titlePaint1);*/



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
            //Toast.makeText(this, "pdf write", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();

        }
        child1=null;

        myPdfDocument.close();
        //Toast.makeText(this, "pdf created", Toast.LENGTH_SHORT).show();

        Intent i =new Intent(Employee_Active_user.this,ViewUserDetailsPdf.class);
        i.putExtra("name",pdf);
        startActivity(i);
    }



        public static class EmplistViewHolder extends RecyclerView.ViewHolder{

        public TextView Username,Passcode,mailED,password,profile,city,phone;

        public Button delete,download;
        //public ImageView profileimg;

        public EditText Limit;
        public ImageButton add;
        public ImageView profileimgE;




        public EmplistViewHolder(@NonNull View itemView) {
            super(itemView);

            Username = itemView.findViewById(R.id.name_emp);
            Passcode = itemView.findViewById(R.id.imei_emp);
            mailED = itemView.findViewById(R.id.mail_emp);
            password = itemView.findViewById(R.id.password_emp);
            delete = itemView.findViewById(R.id.delete_emp);
            profileimgE=itemView.findViewById(R.id.emp_profile);
            profile = itemView.findViewById(R.id.profile_emp);
            city = itemView.findViewById(R.id.city_emp);
            //area = itemView.findViewById(R.id.area_emp);
            phone = itemView.findViewById(R.id.phone_emp);
            download=itemView.findViewById(R.id.download_btnE);
            add = itemView.findViewById(R.id.Add);
            Limit = itemView.findViewById(R.id.limit);


        }


    }


    private void RemoveEmp(String uID) {
        empref.child(uID).removeValue();
    }


}



