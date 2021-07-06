package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class ViewUserDetailsPdf extends AppCompatActivity {

    PDFView pdfView;
    public String s = null;
    public String p;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.share_menu,menu);
        MenuItem item1=menu.findItem(R.id.shareBtn);
        Intent intent = getIntent();
        p = intent.getStringExtra("name");


        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                String s = null;
                File file= new File(getExternalFilesDir(s).getPath() + p);


                //File file = new File(stringFile);
                if (!file.exists()){
                    Toast.makeText(ViewUserDetailsPdf.this, "File doesn't exists", Toast.LENGTH_LONG).show();

                }
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("application/pdf");
                intentShare.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+file));
                startActivity(Intent.createChooser(intentShare, "Share the file ..."));

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_details_pdf);


        pdfView=findViewById(R.id.pdfView);
        Intent intent = getIntent();
        p = intent.getStringExtra("name");




        File file= new File(getExternalFilesDir(s).getPath() + p);


        //File file = new File(stringFile);
        if (!file.exists()){
            Toast.makeText(ViewUserDetailsPdf.this, "File doesn't exists", Toast.LENGTH_LONG).show();
            return;

        }

        //File file = new File(Environment.getExternalStorageDirectory(),"Report.pdf");
        Uri path = Uri.fromFile(file);
        pdfView.fromUri(path)
                .defaultPage(0)
                .spacing(10)
                .load();

    }



}
