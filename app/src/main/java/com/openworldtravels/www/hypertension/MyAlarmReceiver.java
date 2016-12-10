package com.openworldtravels.www.hypertension;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class MyAlarmReceiver extends WakefulBroadcastReceiver {

    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.openworldtravels.www.hypertension.alarm";
    private String jsonString, scheduleString;
    private int morning, lunch, evening;

    public MyAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        jsonString = intent.getStringExtra("jsondata");
        scheduleString = intent.getStringExtra("schedule");
        morning = 0;
        lunch = 0;
        evening = 0;
        boolean notify = false;

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateformatForTime = new SimpleDateFormat("HHmm");
        dateformatForTime.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));
        String datetime = dateformatForTime.format(calendar.getTime());

        PatientJSONConverter patientJSONConverter = new PatientJSONConverter(jsonString);
        patientJSONConverter.execute();

        if (patientJSONConverter.getBreakfastString() != null && patientJSONConverter.getBreakfastString().length() > 2) {
            morning = Integer.parseInt(patientJSONConverter.getRealBreakfastString());
        }

        if (patientJSONConverter.getRealLunchString() != null && patientJSONConverter.getRealLunchString().length() > 2) {
            lunch = Integer.parseInt(patientJSONConverter.getRealLunchString());
        }

        if (patientJSONConverter.getRealDinnerString() != null && patientJSONConverter.getRealDinnerString().length() > 2) {
            evening = Integer.parseInt(patientJSONConverter.getRealDinnerString());
        }

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
        dateformat.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));
        String dateOnly = dateformat.format(calendar.getTime());

        ScheduleJSONConverter scheduleJSONConverter = new ScheduleJSONConverter(scheduleString);
        scheduleJSONConverter.execute();

        Schedule[] schedules = scheduleJSONConverter.getSchedules();
        if (schedules != null) {
            for (int i = 0; i < schedules.length; i++) {

                notify = false;

                String startDate = schedules[i].getStartDateString().replace("-", "");
                String endDate = schedules[i].getEndDateString().replace("-", "");
                String medBrand = schedules[i].getMedBrandString();
                String breakfastTime = schedules[i].getBreakfastString();
                String lunchTime = schedules[i].getLunchString();
                String dinnerTime = schedules[i].getDinnerString();

                int sd = Integer.parseInt(startDate);
                int ed = Integer.parseInt(endDate);
                int dte = Integer.parseInt(dateOnly);

                if(dte >= sd && dte <= ed){

                    if (Integer.parseInt(datetime) == morning) {
                        Log.d("Alarm morning", datetime);
                        notify = true;
                    }else if (Integer.parseInt(datetime) == lunch) {
                        Log.d("Alarm lunch", datetime);
                        notify = true;
                    }else if (Integer.parseInt(datetime) == evening) {
                        Log.d("Alarm evening", datetime);
                        notify = true;
                    }

                    if (notify) {
                        Intent myService = new Intent(context, MyNotificationService.class);
                        myService.putExtra("jsondata", jsonString);
                        myService.putExtra("medbrand", medBrand);
                        startWakefulService(context, myService);
                        setResultCode(Activity.RESULT_OK);
                    }

                }
            }
        }


        //Log.d("Alarm", "hello again!" + datetime + " compare with " + String.valueOf(morning) + "," + String.valueOf(lunch) + "," + String.valueOf(evening));




    }

}
