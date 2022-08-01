package com.example.prc

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import com.example.prc.databinding.ActivityTestBinding


class TestKt : Base() {

    lateinit var bind: ActivityTestBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityTestBinding.inflate(layoutInflater)
        setContentView(bind.root)


        val intent = Intent(Intent.ACTION_SEND)
        val possibleActivitiesList: List<ResolveInfo> = packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)

        // Verify that an activity in at least two apps on the user's device
        // can handle the intent. Otherwise, start the intent only if an app
        // on the user's device can handle the intent.
        if (possibleActivitiesList.size > 1) {

            // Create intent to show chooser.
            // Title is something similar to "Share this photo with".

            val chooser = resources.getString(R.string.app_name).let { title ->
                Intent.createChooser(intent, title)
            }
            startActivity(chooser)
        } else if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }


}