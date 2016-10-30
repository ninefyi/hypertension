package com.openworldtravels.www.hypertension;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    private String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        jsonString = getIntent().getStringExtra("jsondata");
        //Log.d("22octMainMenu", jsonString);

        Button emergency​Button = (Button) findViewById(R.id.btnEmergency);
        Button logoutButton = (Button) findViewById(R.id.btnLogout);
        Button profileButton = (Button) findViewById(R.id.btnPatient);
        Button medicineButton = (Button) findViewById(R.id.btnMedicine);
        Button chatButton = (Button) findViewById(R.id.btnNurse);


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

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatClick();
            }
        });


    }

    private void chatClick() {
        try {
            Intent intent = new Intent(MainMenuActivity.this, ChatActivity.class);
            intent.putExtra("jsondata", jsonString);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void medicineClick() {
        try {
            Intent intent = new Intent(MainMenuActivity.this, MedicineMainActivity.class);
            intent.putExtra("jsondata", jsonString);
            startActivity(intent);
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
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                MyAlert myAlert = new MyAlert(MainMenuActivity.this, "Hypertension", "ขออนุญาตใช้โทรศัพท์่โทรหาพยาบาลนะค่ะ!");


                if (!ActivityCompat.shouldShowRequestPermissionRationale(MainMenuActivity.this,
                        Manifest.permission.CALL_PHONE)) {
                        myAlert.showMessageOKCancel(
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(MainMenuActivity.this,
                                            new String[] {Manifest.permission.CALL_PHONE},
                                            123);
                                }
                            });
                    return;
                }
                ActivityCompat.requestPermissions(MainMenuActivity.this,
                        new String[] {Manifest.permission.CALL_PHONE},
                        123);

                return;
            }
            Intent phone_call = new Intent(Intent.ACTION_CALL);
            phone_call.setData(Uri.parse("tel:0886494888"));
            phone_call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(phone_call);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
