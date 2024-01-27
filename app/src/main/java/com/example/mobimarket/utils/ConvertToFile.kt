package com.example.mobimarket.utils

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.ContentResolverCompat
import androidx.core.database.getStringOrNull
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Extension function to convert Uri to File.
 *
 * @param contentResolver The content resolver to use for querying the MediaStore.
 * @return A File representing the Uri, or null if an error occurred.
 */
fun Uri.toFile(contentResolver: ContentResolver): File? {
    var inputStream: InputStream? = null
    var fileOutputStream: FileOutputStream? = null
    var file: File? = null

    try {
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        var cursor: Cursor? = null

        // Use ContentResolverCompat in case the app is running on a device with API level below 23
        ContentResolverCompat.query(
            contentResolver,
            this,
            projection,
            null,
            null,
            null,
            null
        )?.use {
            cursor = it
            val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            if (cursor?.moveToFirst() == true && columnIndex != null) {
                val filePath = cursor?.getStringOrNull(columnIndex)
                if (!filePath.isNullOrBlank()) {
                    file = File(filePath)
                }
            }
        }

        if (file == null) {
            // Fallback to copying the content of the Uri to a temporary file
            inputStream = contentResolver.openInputStream(this)
            if (inputStream != null) {
                file = File.createTempFile("temp_file", null)
                fileOutputStream = FileOutputStream(file)
                val buffer = ByteArray(1024)
                var read: Int
                while (inputStream.read(buffer).also { read = it } != -1) {
                    fileOutputStream.write(buffer, 0, read)
                }
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            inputStream?.close()
            fileOutputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    return file
}
