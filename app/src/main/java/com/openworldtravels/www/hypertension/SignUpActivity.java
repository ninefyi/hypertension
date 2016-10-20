package com.openworldtravels.www.hypertension;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText userEditText, passwordEditText, nameEditText;
    private ImageView imageView;
    private Button button;
    private String userString, passwordString, nameString, imageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind widget
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);
        nameEditText = (EditText) findViewById(R.id.editText3);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button3);

        //Button Controller
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get Value From EditText
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();
                nameString = nameEditText.getText().toString().trim();

                //Check Space
                if (userString.equals("") || passwordString.equals("") || nameString.equals("")) {
                    //Have space
                    MyAlert myAlert = new MyAlert(SignUpActivity.this, "มีช่องว่าง", "Please Fill All Every Blank");
                    myAlert.myDialog();
                }

            } // onClick
        });

        //Image Controller
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "โปรเลือกรูปภาพ"), 0);


            }// onCLick Image
        });

    } // Main Method

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 0) && (resultCode == RESULT_OK)) {

            Log.d("20octV1", "Result ==> OK");

        }// if

    }// onActivity

}// Main Class
