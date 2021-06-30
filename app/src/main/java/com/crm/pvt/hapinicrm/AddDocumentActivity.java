package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.models.Candidate;
import com.crm.pvt.hapinicrm.models.CustomerB2B;
import com.crm.pvt.hapinicrm.models.CustomerB2C;
import com.crm.pvt.hapinicrm.models.Vendors;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;

public class AddDocumentActivity extends AppCompatActivity {

    Button pick;
    Intent DocSelect;
    private DatabaseReference databaseReference;
    DatabaseReference RootRef;
    private ProgressDialog loadingBar;
    Spinner docSpinner;
    public String type,check_spinner;

    public int n,c,m,p,pw,a,q,s,o,ss;
    String[] ids;
    //new comment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document);
        pick=findViewById(R.id.uploadDoc);
        databaseReference= FirebaseDatabase.getInstance().getReference("Data");
        loadingBar = new ProgressDialog(this);
        docSpinner=findViewById(R.id.spinner_Doc);
        check_spinner="false";


        final String[] choose_category = new String[1];
        docSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                choose_category[0] = docSpinner.getSelectedItem().toString();


                choose_category[0] = docSpinner.getSelectedItem().toString();
                if(choose_category[0].contentEquals("Candidate")){
                    type="Candidate";
                    check_spinner="true";
                }else if(choose_category[0].contentEquals("Vendors")){
                    type="Vendors";
                    check_spinner="true";
                }else if(choose_category[0].contentEquals("CustomerB2B")){
                    type="CustomerB2B";
                    check_spinner="true";
                }else if(choose_category[0].contentEquals("CustomerB2C")){
                    type="CustomerB2C";
                    check_spinner="true";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check_spinner.equals("true"))
                {
                    DocSelect=new Intent(Intent.ACTION_GET_CONTENT);
                    DocSelect.setType("*/*");
                    startActivityForResult(DocSelect,10);

                }
                else
                {
                    Toast.makeText(AddDocumentActivity.this, "Please Select Category", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode == -1) {
                    //fileUri = data.getData();
                    //filePath = fileUri.getPath();


                    Uri content_describer = data.getData();
                    //get the path
                    Log.d("Path???", content_describer.getPath());
                    BufferedReader reader = null;
                    try {
                        // open the user-picked file for reading:
                        InputStream in = getApplicationContext().getContentResolver().openInputStream(content_describer);
                        // now read the content:
                        reader = new BufferedReader(new InputStreamReader(in));

                        String csvLine;
                        while ((csvLine = reader.readLine()) != null) {

                            ids=csvLine.split(",");
                            try{

                                if(type.equalsIgnoreCase("CustomerB2C"))
                                {


                                    for (int j=0;j<ids.length;j++)
                                    {
                                        //Toast.makeText(this,ids[j] , Toast.LENGTH_SHORT).show();

                                        if(ids[j].equalsIgnoreCase("name"))
                                        {
                                            n=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("city"))
                                        {
                                            c=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("mail")) {
                                            m=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("area"))
                                        {
                                            a=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("phone"))
                                        {
                                            p=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("password"))
                                        {
                                            pw=j;
                                        }
                                    }


                                    RootRef = FirebaseDatabase.getInstance().getReference();


                                    if(!ids[n].equalsIgnoreCase("name") && !ids[c].equalsIgnoreCase("city"))
                                    {
                                        DatabaseReference mDatabaseReference;
                                        CustomerB2C user = new CustomerB2C(ids[n],ids[a],ids[m],ids[pw],ids[c],ids[p]);
                                        mDatabaseReference = RootRef.child("Data").child("CustomerB2C").child(ids[p]);
                                        mDatabaseReference.setValue(user);
                                        Toast.makeText(getApplicationContext(), "Details has been Added Sucessfully.. ", Toast.LENGTH_SHORT).show();


                                    }


                                }
                                else if(type.equalsIgnoreCase("CustomerB2B"))
                                {
                                    for (int j=0;j<ids.length;j++)
                                    {
                                        //Toast.makeText(this,ids[j] , Toast.LENGTH_SHORT).show();

                                        if(ids[j].equalsIgnoreCase("name"))
                                        {
                                            n=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("city"))
                                        {
                                            c=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("mail")) {
                                            m=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("oraganization"))
                                        {
                                            a=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("phone"))
                                        {
                                            p=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("password"))
                                        {
                                            pw=j;
                                        }
                                    }


                                    RootRef = FirebaseDatabase.getInstance().getReference();


                                    if(!ids[n].equalsIgnoreCase("name") && !ids[c].equalsIgnoreCase("city"))
                                    {
                                        DatabaseReference mDatabaseReference;
                                        CustomerB2B user = new CustomerB2B(ids[n],ids[a],ids[m],ids[pw],ids[c],ids[p]);
                                        mDatabaseReference = RootRef.child("Data").child("CustomerB2B").child(ids[p]);
                                        mDatabaseReference.setValue(user);
                                        Toast.makeText(getApplicationContext(), "Details has been Added Sucessfully.. ", Toast.LENGTH_SHORT).show();


                                    }







                                }
                                else if(type.equalsIgnoreCase("Candidate"))
                                {




                                    for (int j=0;j<ids.length;j++)
                                    {
                                        //Toast.makeText(this,ids[j] , Toast.LENGTH_SHORT).show();

                                        if(ids[j].equalsIgnoreCase("name"))
                                        {
                                            n=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("city"))
                                        {
                                            c=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("email")) {
                                            m=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("Experience"))
                                        {
                                            a=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("Contact"))
                                        {
                                            p=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("password"))
                                        {
                                            pw=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("qualification"))
                                        {
                                            q=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("skilss"))
                                        {
                                            s=j;
                                        }
                                    }


                                    RootRef = FirebaseDatabase.getInstance().getReference();


                                    if(!ids[n].equalsIgnoreCase("name") && !ids[c].equalsIgnoreCase("city"))
                                    {
                                        DatabaseReference mDatabaseReference;
                                        Candidate user = new Candidate(ids[c],ids[p],ids[m],ids[a],ids[n],ids[pw],ids[q],ids[s]);
                                        mDatabaseReference = RootRef.child("Data").child("Candidate").child(ids[p]);
                                        mDatabaseReference.setValue(user);
                                        Toast.makeText(getApplicationContext(), "Details has been Added Sucessfully.. ", Toast.LENGTH_SHORT).show();


                                    }


                                }
                                else if(type.equalsIgnoreCase("Vendors"))
                                {


                                    for (int j=0;j<ids.length;j++)
                                    {
                                        //Toast.makeText(this,ids[j] , Toast.LENGTH_SHORT).show();

                                        if(ids[j].equalsIgnoreCase("name"))
                                        {
                                            n=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("city"))
                                        {
                                            c=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("email")) {
                                            m=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("Experience"))
                                        {
                                            a=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("Contact"))
                                        {
                                            p=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("password"))
                                        {
                                            pw=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("area"))
                                        {
                                            q=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("services"))
                                        {
                                            s=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("sub services"))
                                        {
                                            ss=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("organization"))
                                        {
                                            o=j;
                                        }
                                    }


                                    RootRef = FirebaseDatabase.getInstance().getReference();


                                    if(!ids[n].equalsIgnoreCase("name") && !ids[c].equalsIgnoreCase("city"))
                                    {
                                        DatabaseReference mDatabaseReference;

                                        Vendors user = new Vendors(ids[n],ids[o],ids[m],ids[pw],ids[c],ids[a],ids[q],ids[p],ids[s],ids[ss]);
                                        mDatabaseReference = RootRef.child("Data").child("Vendors").child(ids[p]);
                                        mDatabaseReference.setValue(user);
                                        Toast.makeText(getApplicationContext(), "Details has been Added Sucessfully.. ", Toast.LENGTH_SHORT).show();


                                    }



                                }



                            }catch (Exception e){
                                Toast.makeText(this,"Something went's wrong ", Toast.LENGTH_SHORT).show();

                            }
                        }


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }








                    }

                break;
        }


    }














 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:


                if(resultCode==RESULT_OK)
                {
                    Uri fileUri=data.getData();
                    StorageReference file;

                    if(type.equals("CustomerB2B"))
                    {
                        file= FirebaseStorage.getInstance().getReference().child("CustomerB2B");
                    }
                    else if(type.equals("CustomerB2C"))
                    {
                        file= FirebaseStorage.getInstance().getReference().child("CustomerB2C");
                    }
                    else if(type.equals("Candidate"))
                    {
                        file= FirebaseStorage.getInstance().getReference().child("Candidate");
                    }
                    else if(type.equals("Vendors"))
                    {
                        file= FirebaseStorage.getInstance().getReference().child("Vendors");
                    }
                    else
                    {
                        file= FirebaseStorage.getInstance().getReference().child("Documents");
                    }

                    StorageReference file_name=file.child("Doc"+fileUri.getLastPathSegment());

                    loadingBar = new ProgressDialog(AddDocumentActivity.this);
                    loadingBar.show();
                    loadingBar.setContentView(R.layout.progress_dialog);
                    loadingBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    file_name.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            Toast.makeText(AddDocumentActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    });

                }
                break;
        }
    }*/
}