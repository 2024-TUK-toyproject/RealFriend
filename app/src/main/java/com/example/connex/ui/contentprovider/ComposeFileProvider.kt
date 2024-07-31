package com.example.connex.ui.contentprovider

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.connex.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class ComposeFileProvider : FileProvider(
    R.xml.filepaths
) {
    companion object {
        fun getImageUri(context: Context): Uri {
            val file = context.createImageFile()
            val authority = context.packageName + ".fileprovider"
            return getUriForFile(
                context,
                authority,
                file
            )
        }
    }
}

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
    return image
}
