package com.example.connex.utils

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import okio.source
import okio.use
import java.io.File
import kotlin.math.roundToInt

fun Uri.asMultipart(
    requestName: String,
    name: String,
    contentResolver: ContentResolver,
): MultipartBody.Part? {
    return contentResolver.query(this, null, null, null, null)?.use {
        if (it.moveToNext()) {
            val displayName =
                setFileName(it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME)), name)
            Log.d("daeyoung", "displayName: $displayName")
            val requestBody = object : RequestBody() {
                override fun contentType(): MediaType? {
                    return contentResolver.getType(this@asMultipart)?.toMediaType()
                }

                override fun writeTo(sink: BufferedSink) {
                    sink.writeAll(contentResolver.openInputStream(this@asMultipart)?.source()!!)
                }
            }
            MultipartBody.Part.createFormData(requestName, displayName, requestBody)
        } else {
            null
        }
    }
}

fun Uri.uriToFilePath(context: Context): String? {
    var filePath: String? = null
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(this, projection, null, null, null)
    cursor?.use {
        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            filePath = cursor.getString(columnIndex)
        }
    }
    return filePath

}

fun setFileName(s1: String, s2: String): String {
    val split = s1.split(".")
    return split[0] + "/" + s2 + "." + split[1]
}
