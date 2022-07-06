package com.example.prc.simple_view_model

import android.os.Bundle
import androidx.activity.viewModels
import com.example.prc.Base
import com.example.prc.databinding.ActivityMyBinding

class MyActivity : Base() {

    private lateinit var bind: ActivityMyBinding

    // Use the 'by viewModels()' Kotlin property delegate
    // from the activity-ktx artifact
    private val myViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMyBinding.inflate(layoutInflater)
        setContentView(bind.root)

        myViewModel.currentName.observe(this) {
            it.tos()
        }

        bind.btnAd.setOnClickListener {
            myViewModel.add(6)
        }
    }
}