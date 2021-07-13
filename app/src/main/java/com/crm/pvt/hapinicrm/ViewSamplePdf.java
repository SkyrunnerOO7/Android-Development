package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class ViewSamplePdf extends AppCompatActivity {

    PDFView pdfView;
    public String s = null;
    public String p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sample_pdf);

        pdfView=findViewById(R.id.pdfView1);
        Intent intent = getIntent();
        p = intent.getStringExtra("name");




        File file= new File(getExternalFilesDir(s).getPath() + p);


        //File file = new File(stringFile);
        if (!file.exists()){
            Toast.makeText(ViewSamplePdf.this, "File doesn't exists", Toast.LENGTH_LONG).show();
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