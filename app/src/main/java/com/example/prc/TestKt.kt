package com.example.prc

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.prc.databinding.ActivityTestBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class TestKt : Base() {

    lateinit var bind: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityTestBinding.inflate(layoutInflater)
        setContentView(bind.root);

//        val uri = URI.create("content://com.android.providers.downloads.documents/document/raw%3A%2Fstorage%2Femulated%2F0%2FDownload%2FRoboto-Black.ttf")
//        val path =  ("/document/raw:/storage/emulated/0/Download/Roboto-Black.ttf")
//        copy(File(path),File("/storage/emulated/0/DCIM/Roboto-Black.ttf"))

        val fontLuancher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                val intent: Intent = result.data!!   // intent with extras
                val uri: Uri = intent.data!!        // document uri content://com.android.providers.downloads.documents/document/raw%3A%2Fstorage%2Femulated%2F0%2FDownload%2FRoboto-Black.ttf
                val path: String = uri.path!!        // document path /document/raw:/storage/emulated/0/Download/Roboto-Black.ttf

                if (true) {
//                result?.data?.data?.let { contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION) }

//                val documentUri = MediaStore.getDocumentUri(this, uri)
//                if (documentUri != null)
//
//                    FileInputStream("/storage/emulated/0/Download/Roboto-Black.ttf").use { input ->
//                        contentResolver.openOutputStream(Uri.parse("/storage/emulated/0/DCIM/Roboto-Black.ttf"))?.use { out ->
//                            val buf = ByteArray(1024)
//                            var len: Int
//                            while (input.read(buf).also { len = it } > 0) {
//                                out.write(buf, 0, len)
//                            }
//                        }
//                    }
//                FileProvider.getUriForFile(this, "$packageName.provider", source, source.path.lowercase(Locale.getDefault()))
                    /*val source = File(FileUtils.getPath(this, uri))
                    val destination = File(cacheDir.absolutePath + "/font/" + source.name)
                    try {
                        if (hasReadPermission())
                            copy(source, destination)
                    } catch (e: Exception) {
                        e.print()
                    }*/

                    val source = File(FileUtils.getPath(this, uri))
                    val destination = File(cacheDir.absolutePath + "/font/" + source.name)

                    if (destination.exists() && destination.length() != 0L)
                        "File exists".tos()
                    else {
                        File(destination.parent!!).let { if (!it.exists()) it.mkdir() }

                        destination.createNewFile()
                        val input = contentResolver.openInputStream(uri)

                        input.use { inputStream ->
                            destination.outputStream().use { output ->
                                inputStream!!.copyTo(output)
                            }
                        }
                    }

                } else {
                  /*  val source = File(FileUtils.getPath(this, uri))
                    val destination = File(cacheDir.absolutePath + "/font/" + source.name)
                    try {
                        if (hasReadPermission())
                            copy(source, destination)
                    } catch (e: Exception) {
                        e.print()
                    }*/
                }
            }
        }

        fontLuancher.launch(
//                Intent(Intent.ACTION_OPEN_DOCUMENT)
//                        .addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION or FLAG_GRANT_WRITE_URI_PERMISSION)
//                        .addCategory(Intent.CATEGORY_OPENABLE)
//                        .setType("*/*")
//                        .putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("font/ttf", "font/otf","application/vnd.ms-fontobject","font/woff","font/woff2"))

                //works up to 10
                Intent(Intent.ACTION_GET_CONTENT)
                        .setType("*/*")
                        .putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("font/ttf", "font/otf","application/vnd.ms-fontobject","font/woff","font/woff2"))
        )
    }

    private fun hasReadPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_PERMISSIONS)
            return false
        }
        return true
    }

    /*override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode== READ_PERMISSIONS && permissions.contentEquals(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)) && grantResults[0]==PERMISSION_GRANTED){

        }
    }*/

    private fun copy(source: File, destination: File) {
        if (destination.exists() && destination.length() != 0L)
            "File exists".tos()
        else {
            File(destination.parent!!).let { if (!it.exists()) it.mkdir() }

            destination.createNewFile()
            val input = FileInputStream(source).channel
            val out = FileOutputStream(destination).channel
            input.transferTo(0, input.size(), out)
            input.close()
            out.close()
        }
    }
}
