package com.openworldtravels.www.hypertension;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;


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
        String msg = "ได้เวลาทานยาแล้วค่ะ!";
        Intent yesReceive = new Intent(getApplicationContext(), ChatActivity.class);
        yesReceive.setAction("YES");
        yesReceive.putExtra("jsondata", jsonString);
        PendingIntent pendingIntentYes = PendingIntent.getActivity(this, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        notification =
            new NotificationCompat.Builder(this) // this is context
                    .setSmallIcon(R.drawable.nobita48)
                    .setContentTitle("Hypertension")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                    .setContentText(msg)
                    .setSound(soundUri)
                    .setAutoCancel(true)
                    .addAction(new NotificationCompat.Action(R.mipmap.ic_launcher, "ทานแล้ว", pendingIntentYes))
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
