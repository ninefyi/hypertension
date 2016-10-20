package com.openworldtravels.www.hypertension;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText userEditText, passwordEditText, nameEditText;
    private ImageView imageView;
    private Button button;
    private String userString, passwordString, nameString, imageString, imagePathString, nameImageString;
    private boolean aBoolean = true;

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
                } else if (aBoolean) {
                    //Non Choose Image
                    MyAlert myAlert = new MyAlert(SignUpActivity.this, "ยังไม่เลือกรูปภาพ", "กรุณาเลือกรูปภาพด้วยค่า");
                    myAlert.myDialog();
                } else {
                    uploadImageAndStringToServer();
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

    private void uploadImageAndStringToServer() {

        //Upload Image
        try {

            //Change policy
            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                    .Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);

            //User simpleFTP
            SimpleFTP simpleFTP = new SimpleFTP();
            MyConstant myConstant = new MyConstant();
            simpleFTP.connect(myConstant.getHostString()
                    , myConstant.getPortAnInt()
                    , myConstant.getUserString()
                    , myConstant.getPasswordString());
            simpleFTP.bin();
            simpleFTP.cwd("img");
            simpleFTP.stor(new File(imagePathString));
            simpleFTP.disconnect();

            //Upload String
            AddUser addUser = new AddUser(SignUpActivity.this);
            addUser.execute(myConstant.getUrlJSONString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }// upload Method

    //Create Inner Class
    private class AddUser extends AsyncTask<String, Void, String> {

        //Explicit
        private Context context;

        public AddUser(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormEncodingBuilder()
                        .add("isAdd", "true")
                        .add("user", userString)
                        .add("password", passwordString)
                        .add("name", nameString)
                        .add("image", imagePathString)
                        .build();

                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strings[0]).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } //doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("20octV1", "Result==>" + s);

            if (Boolean.parseBoolean(s)) {
                Toast.makeText(context, "บันทึกข้อมูลสำเร็จแล้ว!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(context, "บันทึกข้อมูลไม่ผ่าน!", Toast.LENGTH_SHORT).show();
            }

        }// onPostExecute



    }// AddUser inner class


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 0) && (resultCode == RESULT_OK)) {

            Log.d("20octV1", "Result ==> OK");

            Uri uri = data.getData();
            //Show Image View
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(uri));
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }// try

            //Find path of image
            imagePathString = myFindPathImage(uri);
            Log.d("20octV1", "imagePathString ==> " + imagePathString);

            aBoolean = false;

            //Find image name
            nameImageString = imagePathString.substring(imagePathString.lastIndexOf("/"));
            imageString = "http://swiftcodingthai.com/pee/img" + nameImageString;
            Log.d("20octV1", "imageString ==> " + imageString);

        }// if

    }// onActivity

    private String myFindPathImage(Uri uri) {

        String strResult = null;

        String[] strings = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(uri, strings, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int i = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            strResult = cursor.getString(i);


        } else {
            strResult = uri.getPath();
        }

        return strResult;
    }

}// Main Class
