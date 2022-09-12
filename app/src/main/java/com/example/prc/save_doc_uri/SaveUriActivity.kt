package com.example.prc.save_doc_uri

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.prc.Base
import com.example.prc.databinding.ActivitySaveUriBinding
import java.io.File

class SaveUriActivity : Base() {

    lateinit var bind: ActivitySaveUriBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySaveUriBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val fontLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                // document uri content://com.android.providers.downloads.documents/document/raw%3A%2Fstorage%2Femulated%2F0%2FDownload%2FRoboto-Black.ttf
                val uri: Uri = result.data!!.data!!

                val destination = File(cacheDir.absolutePath + "/font/" + uri.path!!.split("/").last())

                if (destination.exists() && destination.length() != 0L)
                    "File exists".tos()
                else {
                    File(destination.parent!!).let { if (!it.exists()) it.mkdir() }
                    destination.createNewFile()

                    contentResolver.openInputStream(uri).use { inputStream ->
                        destination.outputStream().use { output ->
                            inputStream!!.copyTo(output)
                        }
                    }
                }
            }
        }
        //pick font and copy to catch dir
        fontLauncher.launch(
                Intent(Intent.ACTION_GET_CONTENT)
                        .setType("*/*")
                        .putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("font/ttf", "font/otf", "application/vnd.ms-fontobject", "font/woff", "font/woff2"))
        )
    }
}