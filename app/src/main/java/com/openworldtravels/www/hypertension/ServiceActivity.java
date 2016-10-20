package com.openworldtravels.www.hypertension;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class ServiceActivity extends AppCompatActivity {

    //Explicit
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        //Bind widget
        listView = (ListView) findViewById(R.id.livUser);

        //Receive value from intent
        String[] nameStrings = getIntent().getStringArrayExtra("name");
        String[] imageStrings = getIntent().getStringArrayExtra("image");

        //Create list view
        UserAdapter userAdapter = new UserAdapter(ServiceActivity.this, nameStrings, imageStrings);
        listView.setAdapter(userAdapter);

    }// Main method



}// Main class
