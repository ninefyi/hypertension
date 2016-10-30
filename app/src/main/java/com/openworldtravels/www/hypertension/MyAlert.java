package com.openworldtravels.www.hypertension;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by pitichampeethong on 10/20/2016 AD.
 */

public class MyAlert {

    //Explicit
    private Context context;
    private String titleString, messageString;

    public MyAlert(Context context, String titleString, String messageString) {
        this.context = context;
        this.titleString = titleString;
        this.messageString = messageString;
    }

    public void myDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.nobita48);
        builder.setTitle(titleString);
        builder.setMessage(messageString);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }// myDialog Method

    public void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setIcon(R.drawable.nobita48)
                .setTitle(titleString)
                .setMessage(messageString)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }// ShowMessageOKCancel


} // Main class
