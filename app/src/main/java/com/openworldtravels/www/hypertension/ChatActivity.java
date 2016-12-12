package com.openworldtravels.www.hypertension;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.netpie.microgear.Microgear;

public class ChatActivity extends AppCompatActivity {

    private String jsonString;
    private MessageAdapter messageAdapter;
    private EditText editText;
    private boolean isMine = true;
    private PatientJSONConverter patientJSONConverter;
    private MyConstant myConstant;
    private String messageString;
    private ListView listView;
    private final Microgear microgear = new Microgear(ChatActivity.this);
    private String appString, keyString, secretString;
    private String topicString = "patient";

    /*
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String string = bundle.getString("myKey");
            refreshMessage();
            Log.i("Message",string);
        }
    };
    */


    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d("Alarm Chat Back", jsonString);
        this.refreshMessage();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        myConstant = new MyConstant();
        jsonString = getIntent().getStringExtra("jsondata");
        patientJSONConverter = new PatientJSONConverter(jsonString);
        topicString = topicString + patientJSONConverter.getIdString();

        String actionString = getIntent().getAction();
        if(actionString != null) {
            if (actionString.equals("YES")) {
                //Log.d("Notificaiton", jsonString);
                messageString = "ทานเรียบร้อยแล้ว^^";
                SendMessageTask sendMessageTask = new SendMessageTask(ChatActivity.this);
                sendMessageTask.execute(myConstant.getUrlAPI());
            }
        }

        listView = (ListView) findViewById(R.id.chatListView);
        Button chatButton = (Button) findViewById(R.id.sendButton);
        editText = (EditText) findViewById(R.id.messageEditText);

        /*
        final MyConstant myConstant = new MyConstant();
        appString = myConstant.getAppString();
        keyString = myConstant.getKeyString();
        secretString = myConstant.getSecretString();

        try {
            //NetpieIOHelper callback = new NetpieIOHelper(handler);
            //microgear.connect(appString, keyString, secretString);
            //microgear.setCallback(callback);
            //microgear.subscribe(topicString);
            //microgear.setalias("android" + patientJSONConverter.getIdString());
        }catch (Exception e){
            e.printStackTrace();
        }

        */

        refreshMessage();

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(ChatActivity.this, "กรุณาใส่ข้อมูลให้ครบ!", Toast.LENGTH_SHORT).show();
                } else {

                    messageString = editText.getText().toString().trim();
                    SendMessageTask sendMessageTask = new SendMessageTask(ChatActivity.this);
                    sendMessageTask.execute(myConstant.getUrlAPI());

                }// if
            }
        });// OnClick


    }

    //Inner class
    public class SendMessageTask extends AsyncTask<String, Void, String> {

        //Explicit
        private Context context;

        public SendMessageTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormEncodingBuilder()
                        .add("patient", patientJSONConverter.getIdString())
                        .add("parent", "1")
                        .add("belong", "patient")
                        .add("message", messageString)
                        .add("op", "save_msg")
                        .build();
                //Log.d("24oct", messageString);

                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strings[0]).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }// Try
        }// doINBackground

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Log.d("24oct", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                String errorString = jsonObject.getString("Error");
                if (errorString.equals("")) {
                    microgear.publish(topicString, "1", 3, false);
                    Toast.makeText(context, "ส่งข้อความสำเร็จแล้ว!", Toast.LENGTH_SHORT).show();
                    refreshMessage();
                    editText.setText("");
                    //finish();
                }else{
                    Toast.makeText(context, "ส่งข้อความไม่ผ่าน!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }// Inner class

    //LoadMessageTask
    public class LoadMessageTask extends AsyncTask<String, Void, String> {

        //Explicit
        private Context context;

        public LoadMessageTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormEncodingBuilder()
                        .add("patient", patientJSONConverter.getIdString())
                        .add("op", "get_msg")
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
            try {
                //Log.d("24oct", s);
                if (s.length() > 4) {
                    List<ChatMessage> chatMessages = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String contentString = jsonObject.getString("message");
                        String belongString = jsonObject.getString("belong_message");
                        if (belongString.equals("parent")) {
                            isMine = false;
                        }else{
                            isMine = true;
                        }
                        ChatMessage chatMessage = new ChatMessage(contentString, isMine);
                        chatMessages.add(chatMessage);
                    }

                    messageAdapter = new MessageAdapter(ChatActivity.this, R.layout.right_listview, chatMessages);
                    listView.setAdapter(messageAdapter);

                }
            } catch (Exception e) {
                e.printStackTrace();
            } // Try
        }
    }//Inner class


    public void refreshMessage(){
        try {
            LoadMessageTask loadMessageTask = new LoadMessageTask(ChatActivity.this);
            loadMessageTask.execute(myConstant.getUrlAPI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// refreshMessage Method

    protected void onDestroy() {
        super.onDestroy();
        microgear.disconnect();
    }

    protected void onResume() {
        super.onResume();
        microgear.bindServiceResume();
    }


}// Main class
