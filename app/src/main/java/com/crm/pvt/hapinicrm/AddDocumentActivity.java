package com.crm.pvt.hapinicrm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddDocumentActivity extends AppCompatActivity {

    Button pick;
    Intent DocSelect;
    private DatabaseReference databaseReference;
    private ProgressDialog loadingBar;
    Spinner docSpinner;
    public String type,check_spinner;
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
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:


//                if(resultCode==RESULT_OK)
//                {
//                    Uri fileUri=data.getData();
//                    StorageReference file;
//
//                    if(type.equals("CustomerB2B"))
//                    {
//                        file= FirebaseStorage.getInstance().getReference().child("CustomerB2B");
//                    }
//                    else if(type.equals("CustomerB2C"))
//                    {
//                        file= FirebaseStorage.getInstance().getReference().child("CustomerB2C");
//                    }
//                    else if(type.equals("Candidate"))
//                    {
//                        file= FirebaseStorage.getInstance().getReference().child("Candidate");
//                    }
//                    else if(type.equals("Vendors"))
//                    {
//                        file= FirebaseStorage.getInstance().getReference().child("Vendors");
//                    }
//                    else
//                    {
//                        file= FirebaseStorage.getInstance().getReference().child("Documents");
//                    }
//
//                    StorageReference file_name=file.child("Doc"+fileUri.getLastPathSegment());
//
//                    loadingBar = new ProgressDialog(AddDocumentActivity.this);
//                    loadingBar.show();
//                    loadingBar.setContentView(R.layout.progress_dialog);
//                    loadingBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//                    file_name.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//
//                            Toast.makeText(AddDocumentActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                            loadingBar.dismiss();
//                        }
//                    });
//
//                }
//                break;
        }
    }
}