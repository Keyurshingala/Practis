package com.example.prc

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import com.example.prc.databinding.ActivityTestBinding

class TestKt : Base() {

    lateinit var bind: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityTestBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.origin.post {
            val b = bind.origin.toBmp()
            bind.bmp.setImageBitmap(b)
            "${b.rowBytes}, ${b.byteCount}".log()//4320, 3991680   //
        }
    }

    fun View.toBmp(): Bitmap {
        val b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        layout(left, top, right, bottom)
        draw(Canvas(b))
        return b
    }
}

