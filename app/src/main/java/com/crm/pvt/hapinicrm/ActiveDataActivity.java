package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.Admin;
import com.crm.pvt.hapinicrm.models.Candidate;
import com.crm.pvt.hapinicrm.models.CustomerB2B;
import com.crm.pvt.hapinicrm.models.CustomerB2C;
import com.crm.pvt.hapinicrm.models.Vendors;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActiveDataActivity extends AppCompatActivity {
    private RecyclerView list;
    private DatabaseReference Candref;
    private DatabaseReference b2bref;
    private DatabaseReference b2cref;
    private DatabaseReference Vref;
    private String parentDBname ="Admin";
    private SwitchCompat switchCompat;
    private int Tocall = 0;
    private int countemp,countadmin;
    private TextView count;
    private EditText inputtext;
    ImageButton img;
    private int countCand = 0;


    Button choosedb;
    ArrayList<String> databases = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_data);

        Candref = FirebaseDatabase.getInstance().getReference().child("Data").child("Candidate");
        count = findViewById(R.id.sizeAD);

        choosedb = findViewById(R.id.ChooseAD);
        list = findViewById(R.id.rvAD);
        list.setLayoutManager(new LinearLayoutManager(this));
        b2bref = FirebaseDatabase.getInstance().getReference().child("Data").child("CustomerB2B");
        b2cref = FirebaseDatabase.getInstance().getReference().child("Data").child("CustomerB2C");
        Vref =FirebaseDatabase.getInstance().getReference().child("Data").child("Vendors");

//        DisplayCandidate();
//        DisplayB2B();
//        DisplayB2C();
//        DisplayV();


    }


    @Override
    protected void onStart() {
        super.onStart();
        choosedb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence choose[] = new CharSequence[]{
                        "Candidate",
                        "CustomerB2B",
                        "CustomerB2C",
                        "Vendors"
                };
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ActiveDataActivity.this);
                builder1.setTitle("Category ");
                builder1.setItems(choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(i==0){
                            DisplayCandidate();
                            choosedb.setText("Candidate");
                        }else if(i==1){
                            DisplayB2B();
                            choosedb.setText("Customer B2B");
                        }else if(i==2){
                            DisplayB2C();
                            choosedb.setText("Customer B2C");
                        }else{
                            DisplayV();
                            choosedb.setText("Vendors");
                        }

                    }
                });
                builder1.show();

            }
        });

    }

    public void DisplayCandidate(){
        Candref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                countCand = (int) snapshot.getChildrenCount();
                count.setText("count = "+ countCand);

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

        FirebaseRecyclerOptions<Candidate> options =
                new FirebaseRecyclerOptions.Builder<Candidate>()
                        .setQuery(Candref, Candidate.class)
                        .build();

        FirebaseRecyclerAdapter<Candidate,CandViewHolder> adpater = new FirebaseRecyclerAdapter<Candidate, CandViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull ActiveDataActivity.CandViewHolder holder, int position, @NonNull  Candidate model) {
                holder.name.setText("Name : "+model.getName());
                holder.skills.setText("Skills : "+model.getSkills());
                holder.mail.setText("Mail : "+model.getEmail());
                holder.password.setText("Password : " +model.getPassword());
                holder.city.setText("City : " +model.getCity());
                holder.Experience.setText("Experience : " +model.getExperience());
                holder.Qualification.setText("Qualification : "+model.getQualification());
                holder.phone.setText("Phone : " + model.getContact());


                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "Yes",
                                "No"
                        };
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ActiveDataActivity.this);
                        builder1.setTitle("Sure want to Delete this Candidate profile ?");
                        builder1.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if(i==0){
                                    String uID = getRef(position).getKey();
                                    Removecand(uID);

                                }else{
                                    finish();
                                }

                            }
                        });
                        builder1.show();
                    }
                });
            }

            @NonNull
            @Override
            public CandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_candidate,parent,false);
                return new CandViewHolder(view);
            }


        };

        list.setAdapter(adpater);
        adpater.startListening();



    }

    public static class CandViewHolder extends RecyclerView.ViewHolder{

        public TextView name,skills,mail,password,city,Experience,Qualification,phone;
        public Button delete;

        public CandViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.user_nameC);
            skills = itemView.findViewById(R.id.skillsADC);
            mail = itemView.findViewById(R.id.emailTextC);
            password = itemView.findViewById(R.id.passwordEDC);
            city = itemView.findViewById(R.id.CityTextC);
            Experience = itemView.findViewById(R.id.ExpADC);
            Qualification = itemView.findViewById(R.id.QualificationTextADC);
            phone = itemView.findViewById(R.id.PhoneTextC);
            delete = itemView.findViewById(R.id.delete_btnC);
        }
    }
    private void Removecand(String uID) {
        Candref.child(uID).removeValue();
    }

    public void DisplayB2B(){
        b2bref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                countCand = (int) snapshot.getChildrenCount();
                count.setText("count = "+ countCand);

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

        FirebaseRecyclerOptions<CustomerB2B> options =
                new FirebaseRecyclerOptions.Builder<CustomerB2B>()
                        .setQuery(b2bref, CustomerB2B.class)
                        .build();

        FirebaseRecyclerAdapter<CustomerB2B,b2bViewHolder> adpater = new FirebaseRecyclerAdapter<CustomerB2B, b2bViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull ActiveDataActivity.b2bViewHolder holder, int position, @NonNull  CustomerB2B model) {
                holder.name.setText("Name : "+model.getName());
                holder.Org.setText("Organization : "+model.getOrganization());
                holder.mail.setText("Mail : "+model.getMail());
                holder.password.setText("Password : " +model.getPassword());
                holder.city.setText("City : " +model.getCity());
                holder.phone.setText("Phone: " + model.getPhone());


                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "Yes",
                                "No"
                        };
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ActiveDataActivity.this);
                        builder1.setTitle("Sure want to Delete this Customer B2B profile ?");
                        builder1.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if(i==0){
                                    String uID = getRef(position).getKey();
                                    Removeb2b(uID);

                                }else{
                                    finish();
                                }

                            }
                        });
                        builder1.show();
                    }
                });
            }


            @NonNull
            @Override
            public b2bViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_b2b,parent,false);
                return new b2bViewHolder(view);
            }


        };

        list.setAdapter(adpater);
        adpater.startListening();



    }

    public static class b2bViewHolder extends RecyclerView.ViewHolder{

        public TextView name,Org,mail,password,city,phone;
        public Button delete;

        public b2bViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.user_nameb2b);
            Org = itemView.findViewById(R.id.orgb2b);
            mail = itemView.findViewById(R.id.emailTextCb2b);
            password = itemView.findViewById(R.id.passwordEDb2b);
            city = itemView.findViewById(R.id.CityTextb2b);
            phone = itemView.findViewById(R.id.PhoneTextb2b);
            delete = itemView.findViewById(R.id.delete_btnb2b);
        }
    }
    private void Removeb2b(String uID) {
        b2bref.child(uID).removeValue();
    }


    public void DisplayB2C(){
        b2cref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                countCand = (int) snapshot.getChildrenCount();
                count.setText("count = "+ countCand);

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

        FirebaseRecyclerOptions<CustomerB2C> options =
                new FirebaseRecyclerOptions.Builder<CustomerB2C>()
                        .setQuery(b2cref, CustomerB2C.class)
                        .build();

        FirebaseRecyclerAdapter<CustomerB2C,b2cViewHolder> adpater = new FirebaseRecyclerAdapter<CustomerB2C, b2cViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull ActiveDataActivity.b2cViewHolder holder, int position, @NonNull  CustomerB2C model) {
                holder.name.setText("Name : "+model.getName());
                holder.Area.setText("Area : "+model.getArea());
                holder.mail.setText("Mail : "+model.getMail());
                holder.password.setText("Password : " +model.getPassword());
                holder.city.setText("City : " +model.getCity());
                holder.phone.setText("phone : " + model.getPhone());


                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "Yes",
                                "No"
                        };
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ActiveDataActivity.this);
                        builder1.setTitle("Sure want to Delete this Customer B2C profile ?");
                        builder1.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if(i==0){
                                    String uID = getRef(position).getKey();
                                    Removeb2c(uID);

                                }else{
                                    finish();
                                }

                            }
                        });
                        builder1.show();
                    }
                });
            }
            @NonNull
            @Override
            public b2cViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_b2c,parent,false);
                return new b2cViewHolder(view);
            }


        };

        list.setAdapter(adpater);
        adpater.startListening();



    }

    public static class b2cViewHolder extends RecyclerView.ViewHolder{

        public TextView name,Area,mail,password,city,phone;
        public Button delete;

        public b2cViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.user_nameb2c);
            Area = itemView.findViewById(R.id.Areab2c);
            mail = itemView.findViewById(R.id.emailTextCb2c);
            password = itemView.findViewById(R.id.passwordEDb2c);
            city = itemView.findViewById(R.id.CityTextb2c);
            phone = itemView.findViewById(R.id.PhoneTextb2c);
            delete = itemView.findViewById(R.id.delete_btnb2c);
        }
    }
    private void Removeb2c(String uID) {
        b2cref.child(uID).removeValue();
    }


    public void DisplayV(){
        Vref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                countCand = (int) snapshot.getChildrenCount();
                count.setText("count = "+ countCand);

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

        FirebaseRecyclerOptions<Vendors> options =
                new FirebaseRecyclerOptions.Builder<Vendors>()
                        .setQuery(Vref,Vendors.class)
                        .build();

        FirebaseRecyclerAdapter<Vendors,VeViewHolder> adpater = new FirebaseRecyclerAdapter<Vendors, VeViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull ActiveDataActivity.VeViewHolder holder, int position, @NonNull Vendors model) {
                holder.name.setText("Name : "+model.getName());
                holder.Org.setText("Organization : "+model.getArea());
                holder.mail.setText("Mail : "+model.getMail());
                holder.password.setText("Password : " +model.getPassword());
                holder.city.setText("City : " +model.getCity());
                holder.Experience.setText("Experience : " + model.getExperience());
                holder.Area.setText("Area : " + model.getArea());
                holder.Phone.setText("phone : " + model.getPhone());
                holder.Service.setText("Service : " + model.getService());
                holder.SubService.setText("SubService : " + model.getSubService());



                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "Yes",
                                "No"
                        };
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ActiveDataActivity.this);
                        builder1.setTitle("Sure want to Delete this Customer Vendor profile ?");
                        builder1.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if(i==0){
                                    String uID = getRef(position).getKey();
                                    RemoveV(uID);

                                }else{
                                    finish();
                                }

                            }
                        });
                        builder1.show();
                    }
                });
            }
            @NonNull
            @Override
            public VeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendors_layout,parent,false);
                return new VeViewHolder(view);
            }


        };

        list.setAdapter(adpater);
        adpater.startListening();



    }

    public static class VeViewHolder extends RecyclerView.ViewHolder{

        public TextView name,Org,mail,password,city,Experience,Area,Phone,Service,SubService;
        public Button delete;

        public VeViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.user_nameV);
            Org = itemView.findViewById(R.id.orgV);
            mail = itemView.findViewById(R.id.emailTextV);
            password = itemView.findViewById(R.id.passwordEDV);
            city = itemView.findViewById(R.id.CityTextV);
            Experience = itemView.findViewById(R.id.ExpADV);
            Area = itemView.findViewById(R.id.AreaADV);
            Phone = itemView.findViewById(R.id.PhoneTextV);
            Service = itemView.findViewById(R.id.ServiceV);
            SubService = itemView.findViewById(R.id.SubServiceV);
            delete= itemView.findViewById(R.id.delete_btnV);
        }
    }
    private void RemoveV(String uID) {
        Vref.child(uID).removeValue();
    }

}