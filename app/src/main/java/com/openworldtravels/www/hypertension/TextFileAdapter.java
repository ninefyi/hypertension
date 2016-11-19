package com.openworldtravels.www.hypertension;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by pitichampeethong on 11/8/2016 AD.
 */

public class TextFileAdapter {
    private String fileString;
    private Context context;

    public TextFileAdapter(Context context, String fileString) {
        this.fileString = fileString;
        this.context = context;
    }

    public String getFileString() {
        return fileString;
    }

    public Boolean write(String dataString) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(fileString, Context.MODE_PRIVATE);
            outputStream.write(dataString.getBytes());
            outputStream.close();
            Log.d("HT", dataString);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String read() {
        try {
            FileInputStream fileIn = context.openFileInput(fileString);
            BufferedReader r = new BufferedReader(new InputStreamReader(fileIn));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
            Log.d("HT", total.toString());
            return total.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

