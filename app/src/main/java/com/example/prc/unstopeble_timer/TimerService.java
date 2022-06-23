package com.example.prc.unstopeble_timer;

import static com.example.prc.ExtKt.log;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

public class TimerService extends Service {

    static OnCountDown onCountDown;

    interface OnCountDown {
        void onTik(long millisUntilFinished);
    }

    public static void setOnTik(OnCountDown onCountDown) {
        TimerService.onCountDown = onCountDown;
    }

    AppPreference pref;
    CountDownTimer cdt = null;
    long remainingTik;

    @Override
    public void onCreate() {
        super.onCreate();
        pref = new AppPreference(this);
        log("onCreate");

        remainingTik = pref.getRemaining();
        cdt = new CountDownTimer(remainingTik != 0 ? remainingTik : 30000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                log("tik: " + millisUntilFinished / 1000);
                if (onCountDown != null)
                    onCountDown.onTik(millisUntilFinished);

                remainingTik = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                remainingTik = 0;
                pref.setRemaining(remainingTik);
                stopSelf();
            }
        };

        cdt.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log("onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        pref = new AppPreference(this);
        pref.setRemaining(remainingTik);
        log("onTaskRemoved");
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}