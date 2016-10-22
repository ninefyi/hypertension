package com.openworldtravels.www.hypertension;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class MedicineMainActivity extends AppCompatActivity {

    private String jsonString;
    private  PatientJSONConverter patientJSONConverter;
    private Medicine[] medicines;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_main);

        //Bind widget
        listView = (ListView) findViewById(R.id.medListView);
        Button mainButton = (Button) findViewById(R.id.btnMain);

        //Receive value from intent
        jsonString = getIntent().getStringExtra("jsondata");

        patientJSONConverter = new PatientJSONConverter(jsonString);
        patientJSONConverter.execute();

        //Call SyncTask
        MedicineTask medicineTask = new MedicineTask(MedicineMainActivity.this);
        medicineTask.execute(new MyConstant().getUrlAPI());

        //Create list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MedicineMainActivity.this, MedicineDetailActivity.class);
                intent.putExtra("data", medicines[i]);

            }// onItemCLick
        });// onItemClick

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicineMainActivity.this, MainMenuActivity.class);
                intent.putExtra("jsondata", jsonString);
                startActivity(intent);
                finish();
            }
        });

    }// Main Method

    //Inner class
    public class MedicineTask extends AsyncTask<String, Void, String> {
        private Context context;

        public MedicineTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormEncodingBuilder()
                        .add("id", patientJSONConverter.getIdString())
                        .add("op", "medicine")
                        .build();

                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strings[0]).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }// Try
        }// doInBackground

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                //Log.d("22oct", s);
                if (s.length() > 4) {
                    MedicineJSONConverter medicineJSONConverter = new MedicineJSONConverter(s);
                    medicineJSONConverter.execute();
                    medicines = medicineJSONConverter.getMedicines();
                    MedicineAdapter medicineAdapter = new MedicineAdapter(MedicineMainActivity.this, medicines);
                    listView.setAdapter(medicineAdapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } // Try

        }// doPostExecute
    }


}// Main Class
