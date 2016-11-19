package com.openworldtravels.www.hypertension;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class MyAlarmReceiver extends WakefulBroadcastReceiver {

    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.openworldtravels.www.hypertension.alarm";
    private String jsonString;

    public MyAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Alarm", "hello again");
        jsonString = intent.getStringExtra("jsondata");
        Intent myService = new Intent(context, MyNotificationService.class);
        myService.putExtra("jsondata", jsonString);
        startWakefulService(context, myService);
        setResultCode(Activity.RESULT_OK);
    }

}
