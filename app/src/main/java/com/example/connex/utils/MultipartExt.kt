package com.example.connex.utils

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import okio.source

fun Uri.asMultipart(name: String, contentResolver: ContentResolver): MultipartBody.Part? {
//    return MultipartBody.Part.createFormData(
//        "files",
//        name,
//        this.toString().toRequestBody("image/jpeg".toMediaType())
//    )
    return contentResolver.query(this, null, null, null, null)?.use {
        if (it.moveToNext()) {
            val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            val requestBody = object : RequestBody() {
                override fun contentType(): MediaType? {
                    return contentResolver.getType(this@asMultipart)?.toMediaType()
                }

                override fun writeTo(sink: BufferedSink) {
                    sink.writeAll(contentResolver.openInputStream(this@asMultipart)?.source()!!)
                }
            }
            MultipartBody.Part.createFormData(name, displayName, requestBody)
        } else {
            null
        }
    }
}

