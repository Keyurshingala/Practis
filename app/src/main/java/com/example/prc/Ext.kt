package com.example.prc

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
//import com.google.android.play.core.ktx.installStatus
//import com.google.android.play.core.ktx.updatePriority
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*





typealias lay = R.layout
typealias dwbl = R.drawable

const val READ_PERMISSIONS = 111

fun currentDate(): String = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())

fun startHm(): String {
    val h = SimpleDateFormat("HH", Locale.ENGLISH).format(Date())

    SimpleDateFormat("mm", Locale.ENGLISH).format(Date()).let {
        return if (it.toInt() > 30) (h + 1) + ":00" else "$h:30"
    }
}

const val TAG = "FATZ"

fun <T> T.gson() = Gson().toJson(this)

fun <T> T.log() {
    try {
        Log.wtf(TAG, "$this")
    } catch (e: Exception) {
        e.print()
    }
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gon() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun Exception.print() {
    printStackTrace()
    (message + " | " + stackTrace[0].toString() + " | " + javaClass.name).log()
}

/** if dose not work call this method inside View.post() method **/
fun View.toBmp(): Bitmap {
    val b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    layout(left, top, right, bottom)
    draw(Canvas(b))
    return b
}

fun ImageView.load(any: Any?, withCrossFade: Boolean = false) {
    Glide.with(this).load(any).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).apply {
        if (withCrossFade)
            transition(DrawableTransitionOptions.withCrossFade()).into(this@load)
        else
            into(this@load)
    }
}

//Extras

fun test() {
    buildMutableMap<String, String> {
//        here  we can access method of maps
    }
}

//we can use higher order function as extension function
fun <K, V> buildMutableMap(build: HashMap<K, V>.() -> Unit): Map<K, V> {
    val map = HashMap<K, V>()
    map.build()
    return map
}


data class Tp<out Z, out B, out C>(val first: Z, val second: B, val third: C)

fun factorial(i: Int): Int {
    return if (i != 1) i * factorial(i - 1) else 1
}

fun fibonacci(i: Long): Long {
    return if (i == 0L || i == 1L) i else fibonacci(i - 1) + fibonacci(i - 2)
}

fun reversString(s: String): String {
    return if (s.length == 1) s else s[s.length - 1] + reversString(s.substring(0, s.length - 1))
}

//for
//1
//121
//12321
fun ptn(n: Int) {
    for (i in 1..n) {
        upTo(1, i)
        dwnTo(i - 1)
        println()
    }
}

private fun dwnTo(n: Int) {
    if (n > 0) {
        print("$n ")
        dwnTo(n - 1)
    }
}

private fun upTo(i: Int, n: Int) {
    if (n > 0) {
        upTo(i, n - 1)
        print("$n ")
    }
}

//        val uri = URI.create("content://com.android.providers.downloads.documents/document/raw%3A%2Fstorage%2Femulated%2F0%2FDownload%2FRoboto-Black.ttf")
//        val path =  ("/document/raw:/storage/emulated/0/Download/Roboto-Black.ttf")
//        copy(File(path),File("/storage/emulated/0/DCIM/Roboto-Black.ttf"))


