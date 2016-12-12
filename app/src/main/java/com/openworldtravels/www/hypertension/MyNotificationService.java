package com.openworldtravels.www.hypertension;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class MyNotificationService extends IntentService {

    private String jsonString, medBrandString, takeTimeString, medIdString;

    public MyNotificationService() {
        super("MyNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            jsonString = intent.getStringExtra("jsondata");
            medBrandString = intent.getStringExtra("medbrand");
            medIdString = intent.getStringExtra("medid");
            takeTimeString = intent.getStringExtra("taketime");

            createNotification();
            MyAlarmReceiver.completeWakefulIntent(intent);
        }
    }

    private void createNotification() {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (soundUri == null) {
            soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        int menuTime = 0;
        if(takeTimeString == "morning"){
            menuTime = 1;
        }else if(takeTimeString == "lunch"){
            menuTime = 2;
        }else if(takeTimeString == "evening"){
            menuTime = 3;
        }

        String a = medIdString + "" + String.valueOf(menuTime);

        Log.d("Alarm notify", a);

        int NOTIFICATION_ID = Integer.parseInt(a);


        String title = "ทานยาได้แล้วค่ะ! " + medIdString + ',' + a;
        String msg = "แตะถ้าทานแล้ว!";

        Intent intent = new Intent(getApplicationContext(), ScheduleReceiver.class);
        intent.putExtra("jsondata", jsonString);
        intent.putExtra("medid", medIdString);
        intent.putExtra("taketime", takeTimeString);
        intent.putExtra("notificationid", NOTIFICATION_ID);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent contentIntent = PendingIntent.getBroadcast(MyNotificationService.this, 0
                , intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(msg)
                        .setSound(soundUri)
                        .setContentIntent(contentIntent)
                        .build();


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
        wl.acquire(10000);
        wl.release();


    }


}
