package com.example.prc.native_ad

import android.os.Bundle
import com.example.prc.Base
import com.example.prc.databinding.ActivityAddBinding

class AddActivity : Base() {

    lateinit var bind: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityAddBinding.inflate(layoutInflater)
        setContentView(bind.root)


    }
}