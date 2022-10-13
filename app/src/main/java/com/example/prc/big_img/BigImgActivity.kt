package com.example.prc.big_img

import android.app.DownloadManager
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.prc.Base
import com.example.prc.R
import com.example.prc.databinding.ActivityBigImgBinding
import java.io.File


class BigImgActivity : Base() {

    //    val url = "https://preview.redd.it/c3uhsgo1vx541.jpg?auto=webp&s=a45b583ebf921d3ad1649e77ad05e55226140120"
    val url = "https://images4.alphacoders.com/103/1038322.jpg"

    lateinit var bind: ActivityBigImgBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityBigImgBinding.inflate(layoutInflater)
        setContentView(bind.root)


        val cpd = CircularProgressDrawable(this)
        cpd.strokeWidth = 5f
        cpd.centerRadius = 30f
        cpd.setColorSchemeColors(Color.GREEN, Color.CYAN, Color.LTGRAY)
        cpd.start()

        Glide.with(this)
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(cpd)                                    // comment when applying gif
//              .thumbnail(Glide.with(activity).load("https://i.gifer.com/ZKZx.gif"))   //for gif
//              .fitCenter()                                         //may be optional
                .error(R.drawable.liked)
                .into(bind.iv)

        enqueueDownload(this)
    }

    private fun enqueueDownload(context: Context) {
        val fileName = "${System.currentTimeMillis()}.png"
        val destination = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).path}/$fileName"

        val file = File(destination)
        if (file.exists()) file.delete()

        val request = DownloadManager.Request(Uri.parse(url))
                .setMimeType("*/*")
                .setTitle(fileName)
                .setDescription("downloading")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationUri(Uri.parse("file://$destination"))

        (getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)
        "downloading".tosL()
    }
}