package com.crm.pvt.hapinicrm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;

public class AddDocumentActivity extends AppCompatActivity {

    Button pick,sample;
    Intent DocSelect;
    private DatabaseReference databaseReference;
    DatabaseReference RootRef;
    private ProgressDialog loadingBar;
    Spinner docSpinner;
    public String type,check_spinner;
    public int n,c,m,p,pw,a,q,s,o,ss,e;
    String[] ids;
    private String currentDate;
    private String currentTime,Time;
    Dialog dialog;
    private TextView errorText,errorHeading;
    private ProgressDialog loadingBar1;
    Bitmap bmp,scalebmp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document);
        pick=findViewById(R.id.uploadDoc);
        sample=findViewById(R.id.sampleDoc);
        databaseReference= FirebaseDatabase.getInstance().getReference("Data");
        loadingBar = new ProgressDialog(this);
        docSpinner=findViewById(R.id.spinner_Doc);
        check_spinner="false";
        currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm aa", Locale.getDefault()).format(new Date());



        loadingBar1 = new ProgressDialog(this);
        Time=currentDate+" @"+currentTime;
        n=-1;
        c=-1;
        m=-1;
        p=-1;
        pw=-1;
        a=-1;
        q=-1;
        s=-1;
        o=-1;
        ss=-1;
        e=-1;
        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddDocumentActivity.this);
        builder1.setMessage("Please select only csv file ");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
        dialog=new Dialog(AddDocumentActivity.this);
        dialog.setContentView(R.layout.layout_error404);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        errorText=dialog.findViewById(R.id.errortextoferrorAc);
        errorHeading=dialog.findViewById(R.id.homeHeading);

        Button CloseDialog=dialog.findViewById(R.id.CloseBtnErrorAC);
        CloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                errorHeading.setText("Error 404");
                errorText.setText("Something went wrong!");


            }
        });


        final String[] choose_category = new String[1];
        docSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                choose_category[0] = docSpinner.getSelectedItem().toString();


                choose_category[0] = docSpinner.getSelectedItem().toString();
                if(choose_category[0].contentEquals("candidate")){
                    type="Candidate";
                    check_spinner="true";
                }else if(choose_category[0].contentEquals("vendors")){
                    type="Vendors";
                    check_spinner="true";
                }else if(choose_category[0].contentEquals("customerb2b")){
                    type="CustomerB2B";
                    check_spinner="true";
                }else if(choose_category[0].contentEquals("customerb2c")){
                    type="CustomerB2C";
                    check_spinner="true";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        sample.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                createMyPDF();
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

                                    loadingBar1.setTitle("Login Account");
                                    loadingBar1.setMessage("please Wait while checking Credentials..");
                                    loadingBar1.setCanceledOnTouchOutside(false);
                                    loadingBar1.show();

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
                                    if(n<0 || c<0 || m<0 || p<0 || pw<0 || a<0)
                                    {
                                        loadingBar1.dismiss();
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddDocumentActivity.this);
                                        builder1.setMessage("It should contain 6 Attributes i.e. name, city, mail, area, phone, password.");
                                        builder1.setCancelable(true);

                                        builder1.setPositiveButton(
                                                "Ok",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                        AlertDialog alert11 = builder1.create();
                                        alert11.show();
                                    }
                                    else if(!ids[n].equalsIgnoreCase("name") && !ids[c].equalsIgnoreCase("city"))
                                    {
                                        DatabaseReference mDatabaseReference;
                                        CustomerB2C user = new CustomerB2C(ids[n],ids[a],ids[m],ids[pw],ids[c],ids[p]);
                                        mDatabaseReference = RootRef.child("Data").child("CustomerB2C").child(ids[p]);
                                        mDatabaseReference.setValue(user);
                                        AddNewDocument(ids[n],ids[p],ids[c],"CustomerB2C");


                                    }


                                }
                                else if(type.equalsIgnoreCase("CustomerB2B"))
                                {
                                    loadingBar1.setTitle("Login Account");
                                    loadingBar1.setMessage("please Wait while checking Credentials..");
                                    loadingBar1.setCanceledOnTouchOutside(false);
                                    loadingBar1.show();

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
                                        else if(ids[j].equalsIgnoreCase("organization"))
                                        {
                                            o=j;

                                        }
                                        else if(ids[j].equalsIgnoreCase("phone"))
                                        {
                                            p=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("password"))
                                        {
                                            pw=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("area"))
                                        {
                                            a=j;
                                        }

                                    }
                                    RootRef = FirebaseDatabase.getInstance().getReference();

                                    //if(n<0 || c<0 || m<0 || p<0 || pw<0 || a<0 || q<0 ||s<0 || o<0 ||ss<0 )
                                    if(n<0 || c<0 || m<0 || p<0 || pw<0 || o<0 || a<0)
                                    {
                                        loadingBar1.dismiss();
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddDocumentActivity.this);
                                        builder1.setMessage("It should contain name,city,mail,oraganization,phone,password,area attributes ");
                                        builder1.setCancelable(true);

                                        builder1.setPositiveButton(
                                                "Ok",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                        AlertDialog alert11 = builder1.create();
                                        alert11.show();
                                    }
                                    else if(!ids[n].equalsIgnoreCase("name") && !ids[c].equalsIgnoreCase("city"))
                                    {
                                        DatabaseReference mDatabaseReference;
                                        CustomerB2B user = new CustomerB2B(ids[n],ids[o],ids[m],ids[pw],ids[c],ids[p],ids[a]);
                                        mDatabaseReference = RootRef.child("Data").child("CustomerB2B").child(ids[p]);
                                        mDatabaseReference.setValue(user);
                                        AddNewDocument(ids[n],ids[p],ids[c],"CustomerB2B");

                                    }







                                }
                                else if(type.equalsIgnoreCase("Candidate"))
                                {


                                    loadingBar1.setTitle("Login Account");
                                    loadingBar1.setMessage("please Wait while checking Credentials..");
                                    loadingBar1.setCanceledOnTouchOutside(false);
                                    loadingBar1.show();



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
                                            m = j;
                                        }

                                        else if(ids[j].equalsIgnoreCase("Experience"))
                                        {
                                            e=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("phone"))
                                        {
                                            p=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("password"))
                                        {
                                            pw=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("Organization"))
                                        {
                                            q=j;
                                        }
                                        else if(ids[j].equalsIgnoreCase("Area"))
                                        {
                                            s=j;
                                        }
                                    }


                                    RootRef = FirebaseDatabase.getInstance().getReference();

                                    if(n<0 || c<0 || m<0 || p<0 || pw<0 || e<0 ||s<0 || q<0 )
                                    {
                                        loadingBar1.dismiss();

                                        Toast.makeText(this, "n ="+String.valueOf(n), Toast.LENGTH_SHORT).show();
                                        Toast.makeText(this, "c ="+String.valueOf(c), Toast.LENGTH_SHORT).show();
                                        Toast.makeText(this, "m ="+String.valueOf(m), Toast.LENGTH_SHORT).show();
                                        Toast.makeText(this, "p ="+String.valueOf(p), Toast.LENGTH_SHORT).show();
                                        Toast.makeText(this, "pw ="+String.valueOf(pw), Toast.LENGTH_SHORT).show();
                                        Toast.makeText(this, "e ="+String.valueOf(e), Toast.LENGTH_SHORT).show();
                                        Toast.makeText(this, "s ="+String.valueOf(s), Toast.LENGTH_SHORT).show();
                                        Toast.makeText(this, "q ="+String.valueOf(q), Toast.LENGTH_SHORT).show();


                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddDocumentActivity.this);
                                        builder1.setMessage("It should contain 8 attributes i.e. name, city, mail, Experience, phone, password ,Area, Organization ");
                                        builder1.setCancelable(true);

                                        builder1.setPositiveButton(
                                                "Ok",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                        AlertDialog alert11 = builder1.create();
                                        alert11.show();

                                    }
                                    else if(!ids[n].equalsIgnoreCase("name") && !ids[c].equalsIgnoreCase("city"))
                                    {
                                        DatabaseReference mDatabaseReference;
                                        Candidate user = new Candidate(ids[c],ids[p],ids[m],ids[e],ids[n],ids[pw],ids[q],ids[s]);
                                        mDatabaseReference = RootRef.child("Data").child("Candidate").child(ids[p]);
                                        mDatabaseReference.setValue(user);
                                        AddNewDocument(ids[n],ids[p],ids[c],"Candidate");

                                       /* n=-1;
                                        c=-1;
                                        m=-1;
                                        p=-1;
                                        pw=-1;
                                        a=-1;
                                        q=-1;
                                        s=-1;
                                        o=-1;
                                        ss=-1;*/

                                    }


                                }
                                else if(type.equalsIgnoreCase("Vendors"))
                                {


                                    loadingBar1.setTitle("Login Account");
                                    loadingBar1.setMessage("please Wait while checking Credentials..");
                                    loadingBar1.setCanceledOnTouchOutside(false);
                                    loadingBar1.show();

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
                                            e=j;
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
                                            a=j;
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
                                    if(n<0 || c<0 || m<0 || p<0 || pw<0 || e<0 || a<0 ||s<0 || o<0 ||ss<0 )
                                    {
                                        loadingBar1.dismiss();
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddDocumentActivity.this);
                                        builder1.setMessage("It should contain 10 attributes i.e. name, city, mail, Experience, phone, password ,organization, area, services, sub services ");
                                        builder1.setCancelable(true);

                                        builder1.setPositiveButton(
                                                "Ok",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                        AlertDialog alert11 = builder1.create();
                                        alert11.show();


                                    }

                                    if(!ids[n].equalsIgnoreCase("name") && !ids[c].equalsIgnoreCase("city"))
                                    {
                                        DatabaseReference mDatabaseReference;
                                        Vendors user = new Vendors(ids[n],ids[o],ids[m],ids[pw],ids[c],ids[e],ids[a],ids[p],ids[s],ids[ss]);
                                        mDatabaseReference = RootRef.child("Data").child("Vendors").child(ids[p]);
                                        mDatabaseReference.setValue(user);

                                        AddNewDocument(ids[n],ids[p],ids[c],"Vendors");

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
    private void AddNewDocument(String fullName,String phoneNumber,String city_st,String Type)
    {
        final DatabaseReference RootRef1;
        RootRef1 = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> UserNewDataMap = new HashMap<>();
        UserNewDataMap.put("Name",fullName);
        UserNewDataMap.put("Contact",phoneNumber);
        UserNewDataMap.put("City",city_st);
        UserNewDataMap.put("Type",Type);
        UserNewDataMap.put("Time",Time);

        RootRef1.child("NewData").child(phoneNumber).updateChildren(UserNewDataMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Details has been Added Sucessfully.. ", Toast.LENGTH_SHORT).show();

                            loadingBar1.dismiss();
                        }else{
                            loadingBar.dismiss();
                            dialog.show();


                            //Toast.makeText(getApplicationContext(), "Somthing Went Wrong.. Please Try Again After Some time..", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }





    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createMyPDF(){


        final String pdf;


        pdf="/"+"Sample.pdf";
        PdfDocument myPdfDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(1100,1800,1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);
        Canvas canvas=myPage.getCanvas();
        //Paint titlePaint=new Paint();
        Paint titlePaint1=new Paint();

        //Paint myPaint = new Paint();

        titlePaint1.setTextAlign(Paint.Align.CENTER);
        titlePaint1.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        titlePaint1.setTextSize(60);
        canvas.drawText("Sample Pdf",550,80,titlePaint1);
        titlePaint1.setTextSize(27);

        bmp= BitmapFactory.decodeResource(getResources(),R.drawable.customerb2b2);
        scalebmp= Bitmap.createScaledBitmap(bmp,900,200,false);
        canvas.drawText("CustomerB2B Sample",220,200,titlePaint1);
        canvas.drawBitmap(scalebmp,110,230,titlePaint1);

        bmp= BitmapFactory.decodeResource(getResources(),R.drawable.customerb2c2);
        scalebmp= Bitmap.createScaledBitmap(bmp,900,200,false);
        canvas.drawText("CustomerB2C Sample",220,530,titlePaint1);
        canvas.drawBitmap(scalebmp,110,560,titlePaint1);

        bmp= BitmapFactory.decodeResource(getResources(),R.drawable.candiadte1);
        scalebmp= Bitmap.createScaledBitmap(bmp,900,200,false);
        canvas.drawText("Candidate Sample",220,830,titlePaint1);
        canvas.drawBitmap(scalebmp,110,860,titlePaint1);

        bmp= BitmapFactory.decodeResource(getResources(),R.drawable.vendors2);
        scalebmp= Bitmap.createScaledBitmap(bmp,900,200,false);
        canvas.drawText("Vendors Sample",220,1130,titlePaint1);
        canvas.drawBitmap(scalebmp,110,1160,titlePaint1);


        myPdfDocument.finishPage(myPage);

        //Environment.getExternalStoragePublicDirectory();
        String s = null;
        File d2= new File(getExternalFilesDir(s).getPath() + pdf);

        String myFilePath = Environment.getExternalStorageDirectory().getPath() + pdf;
        //String myFilePath = Environment.getExternalStorageDirectory().getPath() + "/myPDFFile.pdf";
        //Toast.makeText(MainActivity.this, myFilePath, Toast.LENGTH_LONG).show();
        //File myFile = new File(myFilePath);
        try {
            myPdfDocument.writeTo(new FileOutputStream(d2));
        }
        catch (Exception e){
            e.printStackTrace();

        }


        myPdfDocument.close();
        Intent i =new Intent(AddDocumentActivity.this,ViewSamplePdf.class);
        i.putExtra("name",pdf);
        startActivity(i);

        /*android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Pdf Created");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });
        builder.show();*/
                /*Intent i=new Intent(MainActivity.this,patientDetails.class);


                startActivity(i);
                finish();*/



    }



}


