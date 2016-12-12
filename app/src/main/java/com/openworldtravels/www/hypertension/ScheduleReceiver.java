package com.openworldtravels.www.hypertension;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import static android.content.Context.NOTIFICATION_SERVICE;

public class ScheduleReceiver extends BroadcastReceiver {

    private String jsonString, takeTime, medId, idString;
    private int notify;

    public ScheduleReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        notify = intent.getIntExtra("notificationid", 0);
        jsonString = intent.getStringExtra("jsondata");
        takeTime = intent.getStringExtra("taketime");
        medId = intent.getStringExtra("medid");
        Log.d("Alarm Schedule Receiver", takeTime + ',' + medId + ',' + notify);

        PatientJSONConverter patientJSONConverter = new PatientJSONConverter(jsonString);
        patientJSONConverter.execute();

        idString = patientJSONConverter.getIdString();


        MyConstant myConstant = new MyConstant();
        SaveMedicineTime saveMedicineTime = new SaveMedicineTime();
        saveMedicineTime.execute(myConstant.getUrlAPI());


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(notify);

    }

    public class SaveMedicineTime extends AsyncTask<String, Void, String> {

        public SaveMedicineTime() {

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                //Log.d("31oct", s);
                if (s.length() > 3) {
                    Log.d("Alarm: Save Time", s + ',' + medId + ',' + takeTime + ',' + idString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } // Try
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormEncodingBuilder()
                        .add("p_med_id", medId)
                        .add("taketime", takeTime)
                        .add("patient", idString)
                        .add("op", "save_take_med")
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
    }

}


