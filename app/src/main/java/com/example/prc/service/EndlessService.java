package com.example.prc.service;

import static com.example.prc.ExtKt.log;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.prc.R;

public class EndlessService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        log("onCreate");
        if (handler != null) handler.removeCallbacks(runnable);
        update();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log("onStartCommand");

        createNotificationChannel();
        Intent notificationIntent = new Intent(this, LocationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setContentTitle(getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        return START_STICKY;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    "CHANNEL_ID",
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    Handler handler = new Handler();
    Runnable runnable = () -> {
        if (checkPermissions()) {
            doTask();
        }
        update();
    };

    private void update() {
        handler.postDelayed(runnable, 1000L);
    }

    private void doTask() {
        log("doing task");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        log("onTaskRemoved");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private boolean checkPermissions() {
        return true;
//        return ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
}
