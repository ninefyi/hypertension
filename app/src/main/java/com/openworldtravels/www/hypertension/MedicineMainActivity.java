package com.openworldtravels.www.hypertension;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MedicineMainActivity extends AppCompatActivity {

    private Button mainButton;
    private String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_main);

        jsonString = getIntent().getStringExtra("jsondata");

        mainButton = (Button) findViewById(R.id.btnMain);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicineMainActivity.this, MainMenuActivity.class);
                intent.putExtra("jsondata", jsonString);
                startActivity(intent);
                finish();
            }
        });

    }
}
