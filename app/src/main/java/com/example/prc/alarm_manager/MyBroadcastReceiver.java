package com.example.prc.alarm_manager;

import static com.example.prc.ExtKt.log;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

public class MyBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        i=0;
        log(i);
//        rcv();
    }

    int i=0;
    private void rcv() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            log("run: "+i);
            i++;
            rcv();
        }, 1000);
    }
}