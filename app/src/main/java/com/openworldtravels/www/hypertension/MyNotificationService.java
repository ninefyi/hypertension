package com.openworldtravels.www.hypertension;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class MyNotificationService extends IntentService {

    private String jsonString;

    public MyNotificationService() {
        super("MyNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            jsonString = intent.getStringExtra("jsondata");
            Log.d("31oct", jsonString);
        }
    }


}
