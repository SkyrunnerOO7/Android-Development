package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.Admin;
import com.crm.pvt.hapinicrm.models.Feedback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserFeedbackShowActivity extends AppCompatActivity {

    Spinner userFeedbackSpinner;


    private RecyclerView list;
    private DatabaseReference dbref;
    private DatabaseReference empref;
    private String parentDBname ="Admin";
    private int Tocall = 0;
    private int countfeedack;
    private TextView count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feedback_show);




        count = findViewById(R.id.Count_user_feedback);
        list = findViewById(R.id.rv_user_feedback);
        list.setLayoutManager(new LinearLayoutManager(this));
        dbref = FirebaseDatabase.getInstance().getReference().child("Feedback");


        userFeedbackSpinner = findViewById(R.id.spinner_user_feedback);
        final String[] choose_category = new String[1];
        userFeedbackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                choose_category[0] = userFeedbackSpinner.getSelectedItem().toString();

                if(choose_category[0].contentEquals("Name")){
                    Toast.makeText(UserFeedbackShowActivity.this, "Name Selected", Toast.LENGTH_SHORT).show();
                    FeedbackFirebaseSortName();
                    count.setText("Feedback Count: "+countfeedack);
                }else if(choose_category[0].contentEquals("Number")){
                    Toast.makeText(UserFeedbackShowActivity.this, "Number Selected", Toast.LENGTH_SHORT).show();
                }else if(choose_category[0].contentEquals("City")){
                    FeedbackFirebaseSortCity();
                    count.setText("Feedback Count: "+countfeedack);
                    Toast.makeText(UserFeedbackShowActivity.this, "City Selected", Toast.LENGTH_SHORT).show();
                }else{
                    feedbackFirebase();

                    //Toast.makeText(UserFeedbackShowActivity.this, "please select on field", Toast.LENGTH_SHORT).show();
                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setQueryHint("Search by Name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                FeedbackFirebasesearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                FeedbackFirebasesearch(s);

                return false;
            }
        });



        return super.onCreateOptionsMenu(menu);

    }
    public void FeedbackFirebasesearch(String s){
        Query query = dbref.orderByChild("Name").startAt(s).endAt(s+'\uf8ff');



        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                countfeedack = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<Feedback> options =
                new FirebaseRecyclerOptions.Builder<Feedback>()
                        .setQuery(query, Feedback.class)
                        .build();

        FirebaseRecyclerAdapter<Feedback, UserFeedbackShowActivity.AdminlistViewHolder> adpater = new FirebaseRecyclerAdapter<Feedback, UserFeedbackShowActivity.AdminlistViewHolder>(options) {
            @Override
            protected void onBindViewHolder(UserFeedbackShowActivity.AdminlistViewHolder holder, int position, Feedback model) {
                holder.name.setText("Name : "+model.getName());
                holder.city.setText("City : "+model.getCity());
                holder.remark.setText("Remark : "+model.getRemark());
                holder.problem.setText("Problem : " +model.getProblem());
                holder.contact.setText("Contact : " +model.getContact());
                holder.status.setText("Status : " +model.getStatus());


            }

            @Override
            public UserFeedbackShowActivity.AdminlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_layout,parent,false);
                return new UserFeedbackShowActivity.AdminlistViewHolder(view);
            }
        };

        list.setAdapter(adpater);
        adpater.startListening();

    }


    public void feedbackFirebase(){

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                countfeedack = (int) snapshot.getChildrenCount();
                count.setText("Feedback Count: "+countfeedack);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<Feedback> options =
                new FirebaseRecyclerOptions.Builder<Feedback>()
                        .setQuery(dbref, Feedback.class)
                        .build();

        FirebaseRecyclerAdapter<Feedback, UserFeedbackShowActivity.AdminlistViewHolder> adpater = new FirebaseRecyclerAdapter<Feedback, UserFeedbackShowActivity.AdminlistViewHolder>(options) {
            @Override
            protected void onBindViewHolder(UserFeedbackShowActivity.AdminlistViewHolder holder, int position, Feedback model) {
                holder.name.setText("Name : "+model.getName());
                holder.city.setText("City : "+model.getCity());
                holder.remark.setText("Remark : "+model.getRemark());
                holder.problem.setText("Problem : " +model.getProblem());
                holder.contact.setText("Contact : " +model.getContact());
                holder.status.setText("Status : " +model.getStatus());


            }

            @Override
            public UserFeedbackShowActivity.AdminlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_layout,parent,false);
                return new UserFeedbackShowActivity.AdminlistViewHolder(view);
            }
        };

        list.setAdapter(adpater);
        adpater.startListening();


    }

    public void FeedbackFirebaseSortCity(){
        Query query = dbref.orderByChild("City");



        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                countfeedack = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<Feedback> options =
                new FirebaseRecyclerOptions.Builder<Feedback>()
                        .setQuery(query, Feedback.class)
                        .build();

        FirebaseRecyclerAdapter<Feedback, UserFeedbackShowActivity.AdminlistViewHolder> adpater = new FirebaseRecyclerAdapter<Feedback, UserFeedbackShowActivity.AdminlistViewHolder>(options) {
            @Override
            protected void onBindViewHolder(UserFeedbackShowActivity.AdminlistViewHolder holder, int position, Feedback model) {
                holder.name.setText("Name : "+model.getName());
                holder.city.setText("City : "+model.getCity());
                holder.remark.setText("Remark : "+model.getRemark());
                holder.problem.setText("Problem : " +model.getProblem());
                holder.contact.setText("Contact : " +model.getContact());
                holder.status.setText("Status : " +model.getStatus());


            }

            @Override
            public UserFeedbackShowActivity.AdminlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_layout,parent,false);
                return new UserFeedbackShowActivity.AdminlistViewHolder(view);
            }
        };

        list.setAdapter(adpater);
        adpater.startListening();


    }

    public void FeedbackFirebaseSortName(){
        Query query = dbref.orderByChild("Name");



        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                countfeedack = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<Feedback> options =
                new FirebaseRecyclerOptions.Builder<Feedback>()
                        .setQuery(query, Feedback.class)
                        .build();

        FirebaseRecyclerAdapter<Feedback, UserFeedbackShowActivity.AdminlistViewHolder> adpater = new FirebaseRecyclerAdapter<Feedback, UserFeedbackShowActivity.AdminlistViewHolder>(options) {
            @Override
            protected void onBindViewHolder(UserFeedbackShowActivity.AdminlistViewHolder holder, int position, Feedback model) {
                holder.name.setText("Name : "+model.getName());
                holder.city.setText("City : "+model.getCity());
                holder.remark.setText("Remark : "+model.getRemark());
                holder.problem.setText("Problem : " +model.getProblem());
                holder.contact.setText("Contact : " +model.getContact());
                holder.status.setText("Status : " +model.getStatus());


            }

            @Override
            public UserFeedbackShowActivity.AdminlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_layout,parent,false);
                return new UserFeedbackShowActivity.AdminlistViewHolder(view);
            }
        };

        list.setAdapter(adpater);
        adpater.startListening();

    }







    public static class AdminlistViewHolder extends RecyclerView.ViewHolder{

        public TextView name,city,contact,problem,remark,status;
        public Button delete,att;
        public ImageView image;

        public AdminlistViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_feedback);
            contact = itemView.findViewById(R.id.contact_feedback);
            problem = itemView.findViewById(R.id.problem_feedback);
            remark = itemView.findViewById(R.id.remark_feedback);
            status = itemView.findViewById(R.id.status_feedback);
            city = itemView.findViewById(R.id.city_feedback);

        }

    }

}