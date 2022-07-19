package com.example.prc

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class Base : AppCompatActivity() {

    fun <T> T.tos() = Toast.makeText(this@Base, "$this", Toast.LENGTH_SHORT).show()
    fun <T> T.tosL() = Toast.makeText(this@Base, "$this", Toast.LENGTH_LONG).show()
}