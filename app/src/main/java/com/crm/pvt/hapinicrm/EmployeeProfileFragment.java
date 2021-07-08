package com.crm.pvt.hapinicrm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.Admin_picture_Model;
import com.crm.pvt.hapinicrm.models.Employee;
import com.crm.pvt.hapinicrm.prevalent.prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;


public class EmployeeProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private static final int PICK_IMAGE=1,RESULT_OK=-1;

    private String mParam1;
    public String IMEI_for_profile_pic,linkOfUrl;
    private String mParam2;

    private ImageView profileImg;
    public LinearLayout logout;
    Uri imageUri;
    TextView Ename,Email,Ephone,Fname,Lname;
    public StorageReference mStorageRef,storageReference;
    public DatabaseReference root,databaseReference;
    public StorageTask mUploadTask;
    public ProgressDialog loadingBar;



    public EmployeeProfileFragment() {
        // Required empty public constructor
    }

    public static EmployeeProfileFragment newInstance(String param1, String param2) {
        EmployeeProfileFragment fragment = new EmployeeProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            IMEI_for_profile_pic = getArguments().getString("IMEI");
            mParam2 = getArguments().getString(ARG_PARAM2);
            //Toast.makeText(getContext(), mParam1, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_employee_profile,container,false);
        profileImg=view.findViewById(R.id.emp_profile_image);
        Ename=view.findViewById(R.id.emp_Name);
        Email=view.findViewById(R.id.emp_email_Value);
        Ephone = view.findViewById(R.id.emp_phone_value);





        root= FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference("Employee").child(IMEI_for_profile_pic);
        loadingBar = new ProgressDialog(getContext());










        logout=view.findViewById(R.id.logout_layout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        Ename.setText(prevalent.CurrentOnlineEmloyee.getName());
        Email.setText(prevalent.CurrentOnlineEmloyee.getMail());
        Ephone.setText(prevalent.CurrentOnlineEmloyee.getPhone());



        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // to select image from phone storage
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE);
            }
        });

        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            String s="true";
            //profileImg.setImageURI(imageUri);
            if (imageUri != null) {

                loadingBar = new ProgressDialog(getContext());
                loadingBar.setTitle("Please Wait");
                loadingBar.setMessage("It will take few seconds..");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                final StorageReference fileReference = mStorageRef.child(IMEI_for_profile_pic + "." + getFileExtension(imageUri));

                mUploadTask = fileReference.putFile(imageUri)

                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                            @Override

                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Admin_picture_Model model=new Admin_picture_Model(imageUri.toString(),IMEI_for_profile_pic);
                                //String modelId=root.push().getKey();
                                //root.child(IMEI_for_profile_pic).setValue(model);
                                HashMap hashMap=new HashMap();
                                hashMap.put("Image",imageUri.toString());


                                root.child("Employee").child(IMEI_for_profile_pic).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        setImage();
                                        //Toast.makeText(getContext(), "condkjf", Toast.LENGTH_SHORT).show();
                                    }
                                });


                                Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();


                            }

                        })

                        .addOnFailureListener(new OnFailureListener() {

                            @Override

                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                            }

                        });


            } else {

                Toast.makeText(getContext(), "No profile image selected", Toast.LENGTH_SHORT).show();

            }


        }
    }


    public String getFileExtension(Uri uri) {

        ContentResolver contentResolver = getContext().getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));


    }
    public void setImage()
    {

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Employee");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String img=snapshot.child(IMEI_for_profile_pic).child("Image").getValue(String.class);
                Toast.makeText(getContext(), img, Toast.LENGTH_SHORT).show();
                if(!(img.equals("null")))
                {
                    Picasso.get().load(img).into(profileImg);
                }
                else {
                    Picasso.get().load(R.drawable.person_icon).into(profileImg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}