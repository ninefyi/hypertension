package com.openworldtravels.www.hypertension;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private String jsonString;
    private Button mainButton;
    private TextView nameTextView, userTextView, passwordTextView, weightTextView, heightTextView, allergyTextView, bodTextView
            , breakfastTextView, lunchTextView, dinnerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        jsonString = getIntent().getStringExtra("jsondata");
        JSONParser();

        mainButton = (Button) findViewById(R.id.btnMain);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainMenuActivity.class);
                intent.putExtra("jsondata", jsonString);
                startActivity(intent);
                finish();
            }
        });


    }

    private void JSONParser(){
        try {

            PatientJSONConverter patientJSONConverter = new PatientJSONConverter(jsonString);

            nameTextView = (TextView) findViewById(R.id.nameTextView);
            userTextView = (TextView) findViewById(R.id.userTextView);
            passwordTextView = (TextView) findViewById(R.id.passwordTextView);
            weightTextView = (TextView) findViewById(R.id.weightTextView);
            heightTextView = (TextView) findViewById(R.id.heightTextView);
            allergyTextView = (TextView) findViewById(R.id.allergyTextView);
            bodTextView = (TextView) findViewById(R.id.bodTextView);
            breakfastTextView = (TextView) findViewById(R.id.breakfastTextView);
            lunchTextView = (TextView) findViewById(R.id.lunchTextView);
            dinnerTextView = (TextView) findViewById(R.id.dinnerTextView);

            nameTextView.setText(patientJSONConverter.getNameString());
            userTextView.setText(patientJSONConverter.getUserString());
            passwordTextView.setText(patientJSONConverter.getPasswordString());
            weightTextView.setText(patientJSONConverter.getWeightString());
            heightTextView.setText(patientJSONConverter.getHeightString());
            allergyTextView.setText(patientJSONConverter.getAllergyString());
            bodTextView.setText(patientJSONConverter.getBodString());
            breakfastTextView.setText(patientJSONConverter.getBreakfastString());
            lunchTextView.setText(patientJSONConverter.getLunchString());
            dinnerTextView.setText(patientJSONConverter.getDinnerString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
