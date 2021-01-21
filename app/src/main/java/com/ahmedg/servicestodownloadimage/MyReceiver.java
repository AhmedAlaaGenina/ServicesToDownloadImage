package com.ahmedg.servicestodownloadimage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = MyReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(MainActivity.SEND)) {
            try {
                Intent activity = new Intent(context, ImageActivity.class);
                activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(activity);
            } catch (Exception e) {
                Log.d(TAG, e.getMessage() + "");
            }
        }
    }

}