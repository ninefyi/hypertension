package com.openworldtravels.www.hypertension;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.os.Handler;

import io.netpie.microgear.MicrogearEventListener;

/**
 * Created by pitichampeethong on 9/10/16 AD.
 */
public class NetpieIOHelper implements MicrogearEventListener {

    Handler handler;

    public NetpieIOHelper(Handler handler){
        this.handler = handler;
    }

    @Override
    public void onConnect() {
        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("myKey", "Now I'm connected with netpie");
        msg.setData(bundle);
        handler.sendMessage(msg);
        Log.d("Alarm Connected", "Now I'm connected with netpie");
    }

    @Override
    public void onMessage(String topic, String message) {
        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("myKey", topic + " : " + message);
        msg.setData(bundle);
        handler.sendMessage(msg);
        Log.d("Alarm Message", topic + " : " + message);
    }

    @Override
    public void onPresent(String token) {
        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("myKey", "New friend Connect :" + token);
        msg.setData(bundle);
        handler.sendMessage(msg);
        Log.d("Alarm present", "New friend Connect :" + token);
    }

    @Override
    public void onAbsent(String token) {
        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("myKey", "Friend lost :" + token);
        msg.setData(bundle);
        handler.sendMessage(msg);
        Log.d("Alarm absent", "Friend lost :" + token);
    }

    @Override
    public void onDisconnect() {
        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("myKey", "Disconnected");
        msg.setData(bundle);
        handler.sendMessage(msg);
        Log.d("Alarm disconnect", "Disconnected");
    }

    @Override
    public void onError(String error) {
        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("myKey", "Exception : " + error);
        msg.setData(bundle);
        handler.sendMessage(msg);
        Log.d("exception", "Exception : " + error);
    }

    @Override
    public void onInfo(String info) {
        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("myKey", "Exception : "+info);
        msg.setData(bundle);
        handler.sendMessage(msg);
        Log.d("Alarm info","Info : "+info);
    }

}
