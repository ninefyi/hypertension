package com.openworldtravels.www.hypertension;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    //Explicit
    private Button loginButton;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString, rememberString;
    private CheckBox rememberCheckBox;
    private MyConstant myConstant;
    private TextFileAdapter textFileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.loginButton);
        userEditText = (EditText) findViewById(R.id.userEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        rememberCheckBox = (CheckBox) findViewById(R.id.rememberCheckBox);

        myConstant = new MyConstant();
        textFileAdapter = new TextFileAdapter(getApplicationContext(), myConstant.getFilenameString());
        rememberString = textFileAdapter.read();
        if(rememberString != null && rememberString.length() > 2){
            PatientJSONConverter patientJSONConverter = new PatientJSONConverter(rememberString);
            patientJSONConverter.execute();
            userEditText.setText(patientJSONConverter.getUserString());
            passwordEditText.setText(patientJSONConverter.getPasswordString());
            rememberCheckBox.setChecked(true);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();
                if (userString.equals("") || passwordString.equals("")) {
                    MyAlert myAlert = new MyAlert(LoginActivity.this, "Hypertension", "กรุณากรอกข้อมูลให้ครบ!");
                    myAlert.myDialog();
                }else{
                    sendLogin();
                }
            }
        });

    }// Main method

    private void sendLogin(){
        try {
            MyConstant myConstant = new MyConstant();
            LoginUser loginUser = new LoginUser(LoginActivity.this);
            loginUser.execute(myConstant.getUrlAPI());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Inner class
    public class LoginUser extends AsyncTask<String, Void, String> {

        //Explicit
        private Context context;

        public LoginUser(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormEncodingBuilder()
                        .add("user", userString)
                        .add("password", passwordString)
                        .add("op", "login")
                        .build();

                Request.Builder builder = new Request.Builder();
                Log.d("Alarm Log-in", strings[0]);
                Request request = builder.url(strings[0]).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code" + response.toString());
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
                if(s == null) {
                    return;
                }
                if (s.length() < 4) {
                    Toast.makeText(context, "ชื่อผู้ใช้และรหัสผ่านไม่ถูกต้อง!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "ยินดีต้อนรับเข้าสู่ระบบ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                    String jsonString = s.trim();

                    if(rememberCheckBox.isChecked()){
                        textFileAdapter.write(jsonString);
                    }

                    intent.putExtra("jsondata",jsonString);
                    startActivity(intent);
                    finish();
                }

            } catch (Exception e) {
                e.printStackTrace();

            } // Try
        }// onPostExecute
    }


}// Main class
