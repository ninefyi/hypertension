package com.openworldtravels.www.hypertension;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    private Button emergency​Button, logoutButton, profileButton, medicineButton;
    private String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        jsonString = getIntent().getStringExtra("jsondata");
        //Log.d("22octMainMenu", jsonString);

        emergency​Button = (Button) findViewById(R.id.btnEmergency);
        logoutButton = (Button) findViewById(R.id.btnLogout);
        profileButton = (Button) findViewById(R.id.btnPatient);
        medicineButton = (Button) findViewById(R.id.btnMedicine);


        emergency​Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutClick();
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileClick();

            }
        });

        medicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicineClick();
            }
        });



    }

    private void medicineClick() {
        try {
            Intent intent = new Intent(MainMenuActivity.this, MedicineMainActivity.class);
            intent.putExtra("jsondata", jsonString);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void profileClick() {
        try {
            Intent proflePage = new Intent(MainMenuActivity.this, ProfileActivity.class);
            proflePage.putExtra("jsondata", jsonString);
            startActivity(proflePage);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void logoutClick() {
        try{
            startActivity(new Intent(MainMenuActivity.this, LoginActivity.class));
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void callPhone() {
        try {
            Intent phone_call = new Intent(Intent.ACTION_CALL);
            phone_call.setData(Uri.parse("tel:095-6982250"));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(phone_call);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
