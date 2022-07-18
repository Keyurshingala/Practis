package com.example.prc

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class KillService : Service() {

    override fun onCreate() {
        super.onCreate()
        "onCreate".log()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        "onStartCommand".log()
        return START_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        super.onTaskRemoved(rootIntent)
        "onTaskRemoved".log()

        stopSelf();
    }

    /*// Binder given to clients
    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): KillService = this@KillService
    }*/

    override fun onBind(arg0: Intent): IBinder? {
        "onBind".log()
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        "onDestroy".log()
    }
}