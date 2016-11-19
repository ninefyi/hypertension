package com.openworldtravels.www.hypertension;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;


public class MyNotificationService extends IntentService {

    private String jsonString;
    private int NOTIFICATION_ID = 1000;
    private Notification notification;

    public MyNotificationService() {
        super("MyNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            jsonString = intent.getStringExtra("jsondata");
            //Log.d("31oct", jsonString);
            createNotification("hello");
            MyAlarmReceiver.completeWakefulIntent(intent);
        }
    }

    private void createNotification(String message) {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (soundUri == null) {
            soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        String title = "ได้เวลาทานยาแล้วค่ะ!";
        String msg = "แตะเพื่อตอบว่าทานยาแล้ว!";
        Intent yesIntent = new Intent(getApplicationContext(), ChatActivity.class);
        yesIntent.setAction("YES");
        yesIntent.putExtra("jsondata", jsonString);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(MyNotificationService.this);
        stackBuilder.addParentStack(ChatActivity.class);
        stackBuilder.addNextIntent(yesIntent);

        PendingIntent contentIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        notification =
            new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                    .setContentText(msg)
                    .setSound(soundUri)
                    //.setAutoCancel(true)
                    .setContentIntent(contentIntent)
                    .build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);

        /*
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"My Tag");
            wl.acquire(10000);
            wl.release();
         */

    }


}
