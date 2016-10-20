package com.openworldtravels.www.hypertension;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class FirstActivity extends AppCompatActivity {

    //Explicit
    private Button signInButton, signUpButton; //Shift Command + Enter
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //Bind Widget
        signInButton = (Button) findViewById(R.id.button);
        signUpButton = (Button) findViewById(R.id.button2);
        userEditText = (EditText) findViewById(R.id.editText4);
        passwordEditText = (EditText) findViewById(R.id.editText5);

        //Signin Controller
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get Value
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //Check Space
                if (userString.equals("") || passwordString.equals("")) {
                    //Have space
                    MyAlert myAlert = new MyAlert(FirstActivity.this, "มีช่องว่าง", "กรุณาใส่ข้อมูลให้ครบนะครับ!!");
                    myAlert.myDialog();
                } else  {
                    // No Space
                    MyConstant myConstant = new MyConstant();
                    SyncUser syncUser = new SyncUser(FirstActivity.this);
                    syncUser.execute(myConstant.getUrlAllJSONString());
                }

            }// onClick
        });

        //Signup Controller
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FirstActivity.this, SignUpActivity.class);
                startActivity(intent);

            }// onClick
        });

    } // Main Method

    private class SyncUser extends AsyncTask<String, Void, String> {

        //Explicit
        private Context context;

        public SyncUser(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strings[0]).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("20octV2", "JSON ==> " + s);

        }// onPost
    } //SyncUser




} // Main Class
