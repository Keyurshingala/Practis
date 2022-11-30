package com.example.prc.accessibility_srvs

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.PixelFormat
import android.media.AudioManager
import android.view.*
import android.view.accessibility.AccessibilityEvent
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.example.prc.BuildConfig
import com.example.prc.R
import com.example.prc.log
import com.google.gson.Gson


//        val flagKeyBoard = 8913696
@SuppressLint("StaticFieldLeak")
open class MyAccessibilityService : AccessibilityService() {

    companion object {
        lateinit var fl: FrameLayout

        var rl: RelativeLayout? = null
    }

    lateinit var audioManager: AudioManager

    @SuppressLint("WrongConstant", "ClickableViewAccessibility")
    override fun onServiceConnected() {
        super.onServiceConnected()
        "onServiceConnected:".log()
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        fl = FrameLayout(this)
        val lp = WindowManager.LayoutParams()
        lp.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        lp.format = PixelFormat.TRANSLUCENT
        lp.flags = 8913704
//        lp.flags = lp.flags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.TOP or Gravity.CENTER
        val inflater = LayoutInflater.from(this)
        inflater.inflate(R.layout.activity_main, fl)
        wm.addView(fl, lp)

        rl = fl.findViewById(R.id.rlMain)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        event.log()
        Gson().toJson(event).log()

        /*when (event.eventType) {
            AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED -> {
                event.log()
                /*val pkg = event.packageName
                val notification = (event.parcelableData as Notification)
                val title =notification.extras.getString(Notification.EXTRA_TITLE)
                val msg =notification.extras.getString(Notification.EXTRA_TEXT)*/
            }

            AccessibilityEvent.TYPE_VIEW_LONG_CLICKED -> startActivity(packageManager.getLaunchIntentForPackage(BuildConfig.APPLICATION_ID))
            else -> "${event.eventType}".log()
        }*/
    }



    override fun onInterrupt() {
        "onInterrupt".log()
    }

    override fun onDestroy() {
        super.onDestroy()
        "onDestroy".log()
    }
}
