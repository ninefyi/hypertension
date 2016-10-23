package com.openworldtravels.www.hypertension;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private String jsonString;
    private MessageAdapter messageAdapter;
    private List<ChatMessage> chatMessages;
    private EditText editText;
    private boolean isMine = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        jsonString = getIntent().getStringExtra("jsondata");
        chatMessages = new ArrayList<>();

        ListView listView = (ListView) findViewById(R.id.chatListView);
        Button chatButton = (Button) findViewById(R.id.sendButton);
        editText = (EditText) findViewById(R.id.messageEditText);

        messageAdapter = new MessageAdapter(ChatActivity.this, R.layout.left_listview, chatMessages);
        listView.setAdapter(messageAdapter);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(ChatActivity.this, "กรุณาใส่ข้อมูลให้ครบ!", Toast.LENGTH_SHORT).show();
                } else {
                    //add message to list
                    ChatMessage chatMessage = new ChatMessage(editText.getText().toString(), isMine);
                    chatMessages.add(chatMessage);
                    messageAdapter.notifyDataSetChanged();
                    editText.setText("");
                    if (isMine) {
                        isMine = false;
                    } else {
                        isMine = true;
                    }
                }
            }
        });


    }
}
