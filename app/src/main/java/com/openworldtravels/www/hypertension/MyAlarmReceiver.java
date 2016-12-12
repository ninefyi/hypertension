package com.openworldtravels.www.hypertension;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class MyAlarmReceiver extends WakefulBroadcastReceiver {

    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.openworldtravels.www.hypertension.alarm";
    private String jsonString, scheduleString, idString, historyTimeString;
    private int morning, lunch, evening;
    private HistoryJSONConverter historyJSONConverter;
    private HistoryTime[] historyTimes;
    private Context context;

    public MyAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        jsonString = intent.getStringExtra("jsondata");
        scheduleString = intent.getStringExtra("schedule");
        morning = 0;
        lunch = 0;
        evening = 0;
        this.context = context;

        PatientJSONConverter patientJSONConverter = new PatientJSONConverter(jsonString);
        patientJSONConverter.execute();

        idString = patientJSONConverter.getIdString();

        if (patientJSONConverter.getBreakfastString() != null && patientJSONConverter.getBreakfastString().length() > 2) {
            morning = Integer.parseInt(patientJSONConverter.getRealBreakfastString());
        }

        if (patientJSONConverter.getRealLunchString() != null && patientJSONConverter.getRealLunchString().length() > 2) {
            lunch = Integer.parseInt(patientJSONConverter.getRealLunchString());
        }

        if (patientJSONConverter.getRealDinnerString() != null && patientJSONConverter.getRealDinnerString().length() > 2) {
            evening = Integer.parseInt(patientJSONConverter.getRealDinnerString());
        }


        MyConstant myConstant = new MyConstant();
        MedicineHistoryTime medicineHistoryTime = new MedicineHistoryTime();
        medicineHistoryTime.execute(myConstant.getUrlAPI());

        //Log.d("Alarm", "hello again!" + datetime + " compare with " + String.valueOf(morning) + "," + String.valueOf(lunch) + "," + String.valueOf(evening));


    }

    private void executeAfter(String data) {
        boolean notify;

        historyTimeString = data;
        if (historyTimeString != null && historyTimeString.equals("") == false) {
            historyJSONConverter = new HistoryJSONConverter(historyTimeString);
            historyJSONConverter.execute();
            historyTimes = historyJSONConverter.getHistoryTimes();
            Log.d("Alarm MedicineHistory", historyTimeString);
        }

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateformatForTime = new SimpleDateFormat("HHmm");
        dateformatForTime.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));
        String datetime = dateformatForTime.format(calendar.getTime());

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
                String medId = schedules[i].getpMedIdString();
                String takeTime = "";

                int sd = Integer.parseInt(startDate);
                int ed = Integer.parseInt(endDate);
                int dte = Integer.parseInt(dateOnly);

                int currentTime = Integer.parseInt(datetime);

                if (dte >= sd && dte <= ed) {

                    if (breakfastTime.equals("0") == false && currentTime < lunch) {
                        notify = isHistory(historyTimes, medId, "morning");
                        if(notify){
                            if(currentTime >= morning){
                                takeTime = "morning";
                                notify = true;
                            }else{
                                notify = false;
                            }
                        }

                        if (notify) {
                            Log.d("Alarm morning", datetime + ',' + medId);
                        }

                    } else if (lunchTime.equals("0") == false && currentTime < evening) {
                        notify = isHistory(historyTimes, medId, "lunch");
                        if(notify){
                            if(currentTime >= lunch){
                                takeTime = "lunch";
                                notify = true;
                            }else{
                                notify = false;
                            }
                        }
                        if (notify) {
                            Log.d("Alarm lunch", datetime + ',' + medId);
                        }
                    }else if (dinnerTime.equals("0") == false) {
                        notify = isHistory(historyTimes, medId, "evening");
                        if(notify){
                            if(currentTime >= evening){
                                takeTime = "evening";
                                notify = true;
                            }else{
                                notify = false;
                            }
                        }
                        if (notify) {
                            Log.d("Alarm evening", datetime + ',' + medId);
                        }
                    }


                    if (notify) {
                        Intent myService = new Intent(context, MyNotificationService.class);
                        myService.putExtra("jsondata", jsonString);
                        myService.putExtra("medbrand", medBrand);
                        myService.putExtra("taketime", takeTime);
                        myService.putExtra("medid", medId);
                        startWakefulService(context, myService);
                        setResultCode(Activity.RESULT_OK);
                    }

                }
            }
        }
    }

    public boolean isHistory(HistoryTime[] historyTimes, String medId, String takeTime) {
        boolean notify = true;
        if (historyTimes != null) {
            for (int i = 0; i < historyTimes.length; i++) {
                String historyMedId = historyTimes[i].getpMedIdString();
                if (medId.equals(historyMedId)) {
                    if (takeTime == "morning") {
                        if (!TextUtils.isEmpty(historyTimes[i].getBreakfastString())) {
                            notify = false;
                        }
                    } else if (takeTime == "lunch") {
                        if (!TextUtils.isEmpty(historyTimes[i].getLunchString())) {
                            notify = false;
                        }
                    } else if (takeTime.equals("evening")) {
                        if(!TextUtils.isEmpty(historyTimes[i].getDinnerString())){
                            notify = false;
                        }
                    }
                    return notify;
                }
            }
        }
        return notify;
    }

    public class MedicineHistoryTime extends AsyncTask<String, Void, String> {

        public MedicineHistoryTime() {
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormEncodingBuilder()
                        .add("patient", idString)
                        .add("op", "take_med_time")
                        .build();

                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strings[0]).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }// Try
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String result = "";
            try {
                //Log.d("Alarm result", s);
                if (s != null) {
                    if (s.length() > 4) {
                        result = s.trim();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } // Try

            executeAfter(result);
        }


    }

}
