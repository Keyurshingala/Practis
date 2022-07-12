package com.example.prc.color_picker

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.prc.Base
import com.example.prc.databinding.ActivityColorPickerBinding

class ColorPickerActivity : Base() {

    lateinit var bind: ActivityColorPickerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityColorPickerBinding.inflate(layoutInflater)
        setContentView(bind.root)

    }

    override fun onStart() {
        super.onStart()
        bind.fl.post {
            MyColorPicker(bind.fl, bind.ivPicker, selectedcolor = { int, st ->
                bind.ivColor.setImageDrawable(ColorDrawable(int))

            }, onEnd = {

            })
        }

    }
}