package com.example.prc

import android.R
import android.os.Bundle
import android.view.View
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.prc.databinding.ActivityTestBinding


class TestKt : Base() {

    lateinit var bind: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityTestBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }
}

