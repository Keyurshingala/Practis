package com.example.prc

import android.view.MotionEvent
import androidx.multidex.MultiDexApplication
import com.instabug.library.Instabug
import com.instabug.library.invocation.InstabugInvocationEvent


class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Instabug.Builder(this, "42a8fb3d0c3444260a9b8eb83b063cbd")
//                .setInvocationEvents(InstabugInvocationEvent.SHAKE, InstabugInvocationEvent.FLOATING_BUTTON)
                .build()
    }


}