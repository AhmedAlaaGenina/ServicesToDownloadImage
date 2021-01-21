package com.ahmedg.servicestodownloadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    public static final String URL = "url";
    public static final String SEND = "Send";

    Button btnDownload;
    EditText edtTxtURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDownload = findViewById(R.id.btnDownloadImage);
        edtTxtURL = findViewById(R.id.edtTextURL);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edtTxtURL.getText().toString();
                Intent intent = new Intent(getApplicationContext(), MyReceiver.class);
                intent.putExtra(MainActivity.URL, url);
                ImageJobIntentService.enqueueWork(getApplicationContext(), intent);
            }
        });
        //  Registering......
        IntentFilter filter = new IntentFilter(MainActivity.SEND);
        MyReceiver receiver = new MyReceiver();
        registerReceiver(receiver, filter);
    }

}