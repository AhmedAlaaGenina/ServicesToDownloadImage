package com.ahmedg.servicestodownloadimage;

import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.JobIntentService;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class ImageJobIntentService extends JobIntentService {
    static final int JOB_ID = 1000;
    static final String IMAGE = "image";


    static void enqueueWork(Context context, Intent work) {

        enqueueWork(context, ImageJobIntentService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(Intent intent) {

        String url = intent.getStringExtra(MainActivity.URL);
        Bitmap bitmap = downloadImage(url);
        saveImage(getApplicationContext(), bitmap, "bitmap");

    }

    public Bitmap downloadImage(String url) {
        Bitmap result = null;
        URL urlObj = null;
        HttpsURLConnection httpConn;
        InputStream inputStream;

        try {
            urlObj = new URL(url);
            httpConn = (HttpsURLConnection) urlObj.openConnection();
            httpConn.connect();
            inputStream = httpConn.getInputStream();
            result = BitmapFactory.decodeStream(inputStream);
            httpConn.disconnect();
            inputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("TAG", "Downloading " + result);

        Log.i("TAG", "Downloading NOW........");
        return result;
    }


    public void saveImage(Context context, Bitmap b, String imageName) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            Log.i("TAG", "Saved Image Done.....");
            fileOutputStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 2, Something went wrong!");
            e.printStackTrace();
        }
        Intent intent=new Intent();
        intent.setAction(MainActivity.SEND);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Downloading Done.....", Toast.LENGTH_SHORT).show();
        Log.i("TAG", "onDestroy: Downloading Done.....");
    }

    @Override
    public boolean onStopCurrentWork() {
        Log.i("TAG", "onStopCurrentWork");
        return super.onStopCurrentWork();
    }
}