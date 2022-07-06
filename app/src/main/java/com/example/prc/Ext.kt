package com.example.prc

import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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

fun <T> T.log() {
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

//Extras
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
