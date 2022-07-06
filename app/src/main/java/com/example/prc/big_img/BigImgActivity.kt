package com.example.prc.big_img

import android.graphics.Color
import android.os.Bundle
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.prc.Base
import com.example.prc.R
import com.example.prc.databinding.ActivityBigImgBinding

class BigImgActivity : Base() {

    lateinit var bind:ActivityBigImgBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityBigImgBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val cpd = CircularProgressDrawable(this)
        cpd.strokeWidth = 5f
        cpd.centerRadius = 30f
        cpd.setColorSchemeColors(Color.GREEN,Color.CYAN,Color.LTGRAY)
        cpd.start()

        Glide.with(this)
                .load("https://i.pinimg.com/originals/bf/82/f6/bf82f6956a32819af48c257243e286.jpg")
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(cpd)
                .error(R.drawable.liked)
                .into(bind.iv)
    }
}