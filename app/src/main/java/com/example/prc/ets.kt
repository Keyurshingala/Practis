package com.example.prc

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

class ets {
    fun getPath(c: Context, uri: Uri): String? {
        var path: String? = null
        val projection = arrayOf(MediaStore.Files.FileColumns.DATA)
        val cursor = c.contentResolver.query(uri, projection, null, null, null)
        if (cursor == null) {
            path = uri.path
        } else {
            cursor.moveToFirst()
            val column_index = cursor.getColumnIndexOrThrow(projection[0])
            path = cursor.getString(column_index)
            cursor.close()
        }
        return if (path == null || path.isEmpty()) uri.path else path
    }
}