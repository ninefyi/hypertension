package com.openworldtravels.www.hypertension;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.List;

import io.netpie.microgear.Microgear;

public class ChatBackgroudService extends IntentService {

    public ChatBackgroudService() {
        super("ChatBackgroudService");
    }

    private final Microgear microgear = new Microgear(ChatBackgroudService.this);
    private String appString, keyString, secretString, jsonString;
    private PatientJSONConverter patientJSONConverter;
    private String topicString = "patient";

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String string = bundle.getString("myKey");
            Log.d("Alarm Message", string);

            ComponentName chatComponent = checkActivity();

            if (string.indexOf("patient") > 1) {
                if(chatComponent != null){
                    Intent intent = new Intent(getBaseContext(), ChatActivity.class);
                    intent.putExtra("jsondata", jsonString);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Log.d("Alarm Chat", jsonString);
                    getApplication().startActivity(intent);
                }else{
                    createNotification();
                }


            }

        }
        private ComponentName checkActivity() {
            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                List<ActivityManager.AppTask> appTaskList = am.getAppTasks();
                for (ActivityManager.AppTask appTask : appTaskList) {
                    if(appTask.getTaskInfo().topActivity != null){
                        String s = appTask.getTaskInfo().topActivity.getClassName();
                        if (s != null && s.indexOf("ChatActivity") > 1) {
                            return appTask.getTaskInfo().topActivity;
                        }
                    }
                }
            }
            return null;
        }
    };

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {

                jsonString = intent.getStringExtra("jsondata");
                patientJSONConverter = new PatientJSONConverter(jsonString);

                final MyConstant myConstant = new MyConstant();
                appString = myConstant.getAppString();
                keyString = myConstant.getKeyString();
                secretString = myConstant.getSecretString();
                topicString = topicString + patientJSONConverter.getIdString();

                NetpieIOHelper callback = new NetpieIOHelper(handler);
                microgear.connect(appString, keyString, secretString);
                microgear.setCallback(callback);
                microgear.subscribe(topicString);
                microgear.setalias("htandroid" + patientJSONConverter.getIdString());

                Log.d("Alarm Chat Service", appString);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createNotification() {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (soundUri == null) {
            soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        int NOTIFICATION_ID = 9999;

        String title = "มีข้อความจากโรงพยาบาล!";
        String msg = "แตะเพื่อเปิดอ่าน";

        Intent intent = new Intent(this.getApplicationContext(), ChatActivity.class);
        intent.putExtra("jsondata", jsonString);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(ChatBackgroudService.this, NOTIFICATION_ID
                , intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(msg)
                        .setSound(soundUri)
                        .setAutoCancel(true)
                        .setContentIntent(contentIntent)
                        .build();


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
        wl.acquire(10000);
        //wl.release();


    }

}
