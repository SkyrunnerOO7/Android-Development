package com.crm.pvt.hapinicrm;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crm.pvt.hapinicrm.models.Admin_picture_Model;
import com.crm.pvt.hapinicrm.prevalent.prevalent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
//import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso;
import com.sun.mail.imap.protocol.INTERNALDATE;

import java.util.HashMap;

/**
 * Created by Kuldeep Sahu on 04/06/2021.
 * E-mail: sahukuldeep912001@gmail.com
 * http://skywarrior09.gq
 */

public class AdminDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private NavigationView navigationView;
    boolean doubleBackToExitPressedOnce = false;

    View hView;
    ImageView profileImage;
    TextView Uname,Uprofile,Uemail;
    public String passcode;
//    private static final int PICK_IMAGE=1,RESULT_OK=-1;
    Uri imageUri;


//    public StorageReference mStorageRef,storageReference;
//    private DatabaseReference root,databaseReference;
//    private StorageTask mUploadTask;
//    private ProgressDialog loadingBar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        setTitle(null);

        Intent intent = getIntent();
        passcode = intent.getStringExtra("passcode");
//        root= FirebaseDatabase.getInstance().getReference();
//        //spinner=findViewById(R.id.category_spinner);
////        mStorageRef = FirebaseStorage.getInstance().getReference("Admin").child(passcode);
//        loadingBar = new ProgressDialog(this);

        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.nav_view);

        Toolbar toolbar = findViewById(R.id.Menu_toolbar);
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);



        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //to add image picker in header part
        NavigationView navigationView=(NavigationView) findViewById(R.id.nav_view);
        hView=navigationView.getHeaderView(0);
        profileImage=(ImageView)hView.findViewById(R.id.admin_profile_image);
        Uname = hView.findViewById(R.id.userid);
        Uname.setText(prevalent.CurrentOnlineAdmin.getName());
        Uprofile = hView.findViewById(R.id.userProfile);
        Uprofile.setText("Admin");
        Uemail = hView.findViewById(R.id.userEmail);
        Uemail.setText(prevalent.CurrentOnlineAdmin.getEmail());
        Picasso.get().load(prevalent.CurrentOnlineAdmin.getImage()).placeholder(R.drawable.admin_profile_icon1).into(profileImage);



        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE);
                startActivity(new Intent(getApplicationContext(),settings_Activity.class));
            }
        });
        navigationView.setNavigationItemSelectedListener(this);






        if(savedInstanceState==null){
            navigationView.setNavigationItemSelectedListener(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new fragment_admin_dashboard())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }



    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                //add Home fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new fragment_admin_dashboard())
                        .commit();

                break;


            case R.id.nav_Feedback:
                //add Home fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new fragment_feedback())
                        .commit();

                break;

            case R.id.nav_Rate:
                //add Home fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new fragment_rate_us())
                        .commit();

                break;

            case R.id.nav_signout:
                //add Home fragment
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                finish();
                break;


        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }



    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            imageUri = data.getData();
//            storageReference= FirebaseStorage.getInstance().getReference("Admin").child(passcode).child(passcode + "." + getFileExtension(imageUri));
//            String s="true";
//            //profileImage.setImageURI(imageUri);
//            if (imageUri != null) {
//
//                loadingBar = new ProgressDialog(AdminDashboardActivity.this);
//                loadingBar.setTitle("Please Wait");
//                loadingBar.setMessage("It will take few seconds..");
//                loadingBar.setCanceledOnTouchOutside(false);
//                loadingBar.show();
//
//
//                final StorageReference fileReference = mStorageRef.child(passcode + "." + getFileExtension(imageUri));
//
//                mUploadTask = fileReference.putFile(imageUri)
//
//                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//
//                            @Override
//
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                                Admin_picture_Model model=new Admin_picture_Model(imageUri.toString(),passcode);
//                                //String modelId=root.push().getKey();
//                                //root.child(passcode).setValue(model);
//
//                                HashMap hashMap=new HashMap();
//                                hashMap.put("Image",imageUri.toString());
//
//
//                                root.child("Admin").child(passcode).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
//                                    @Override
//                                    public void onSuccess(Object o) {
//                                        setImage();
//                                    }
//                                });
//
//
//
//
//                                Toast.makeText(AdminDashboardActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                                loadingBar.dismiss();
//
//                                //setimage();
//
//                            }
//
//                        })
//
//                        .addOnFailureListener(new OnFailureListener() {
//
//                            @Override
//
//                            public void onFailure(@NonNull Exception e) {
//
//                                Toast.makeText(AdminDashboardActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//
//                            }
//
//                        });
//
//
//            } else {
//
//                Toast.makeText(AdminDashboardActivity.this, "No profile image selected", Toast.LENGTH_SHORT).show();
//
//            }
//
//
//        }
//    }
//
//
//    public String getFileExtension(Uri uri) {
//
//        ContentResolver contentResolver = getContentResolver();
//
//        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//
//        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
//
//    }
//    public void setImage()
//    {
//
//        databaseReference=FirebaseDatabase.getInstance().getReference().child("Admin");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String img=snapshot.child(passcode).child("Image").getValue(String.class);
//                if(!(img.equals("null")))
//                {
//
//                  profileImage.setImageURI(Uri.parse(img));
////                    Picasso.get().load(img).into(profileImage);
//
//                    Glide.with(getApplicationContext()).load(img).into(profileImage);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//

//    }

}