package com.example.prc

import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


fun currentDate(): String = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())

fun startHm(): String {
    val h = SimpleDateFormat("HH", Locale.ENGLISH).format(Date())

    SimpleDateFormat("mm", Locale.ENGLISH).format(Date()).let {
        return if (it.toInt() > 30) (h + 1) + ":00" else "$h:30"
    }
}

const val TAG = "FATZ"

fun Any?.log() {
    try {
        Log.wtf(TAG, "$this")
    } catch (e: Exception) {
        e.print()
    }
}

fun Exception.print() {
    printStackTrace()
    (message + " | " + stackTrace[0].toString() + " | " + javaClass.name).log()
}


fun ImageView.load(any: Any?, withCrossFade: Boolean = false) {
    Glide.with(this).load(any).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).apply {
        if (withCrossFade)
            transition(DrawableTransitionOptions.withCrossFade()).into(this@load)
        else
            into(this@load)
    }
}

data class Tp<out Z, out B, out C>(val first: Z, val second: B, val third: C)



