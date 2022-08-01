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
                .load("https://preview.redd.it/c3uhsgo1vx541.jpg?auto=webp&s=a45b583ebf921d3ad1649e77ad05e55226140120")
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(cpd)                                    // comment when applying gif
//              .thumbnail(Glide.with(activity).load("https://i.gifer.com/ZKZx.gif"))   //for gif
//              .fitCenter()                                         //may be optional
                .error(R.drawable.liked)
                .into(bind.iv)
    }
}