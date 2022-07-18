package com.example.prc

import android.content.Intent
import android.os.Bundle
import com.example.prc.databinding.ActivityTestBinding


class TestKt : Base() {

    lateinit var bind: ActivityTestBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityTestBinding.inflate(layoutInflater)
        setContentView(bind.root)

        startService(Intent(this, KillService::class.java))
    }


}