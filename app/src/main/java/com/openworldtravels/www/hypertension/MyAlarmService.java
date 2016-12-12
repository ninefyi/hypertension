package com.openworldtravels.www.hypertension;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;


public class MyAlarmService extends IntentService {

    private String jsonString, scheduleString;

    public MyAlarmService() {
        super("MyAlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            jsonString = intent.getStringExtra("jsondata");
            scheduleString = intent.getStringExtra("schedule");
            //Log.d("Alarm Schedule:", scheduleString);
            setScheduleAlarm();
        }
    }

    public void setScheduleAlarm() {
        Calendar calendar = Calendar.getInstance();
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        intent.putExtra("jsondata", jsonString);
        intent.putExtra("schedule", scheduleString);
        final PendingIntent pIntent = PendingIntent.getBroadcast(MyAlarmService.this
                                    , MyAlarmReceiver.REQUEST_CODE
                                    , intent
                                    , PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), 1*60*1000, pIntent);
    }


}
