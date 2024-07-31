package com.example.connex.ui.domain

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import com.example.connex.BuildConfig
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@Composable
fun cameraLauncher(uri: Uri, update: (Uri) -> Unit): ManagedActivityResultLauncher<Uri, Boolean> {

    val scope = rememberCoroutineScope()
    return rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        Log.d("daeyoung", "camera uri: $uri")
        if (success) update(uri)
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

fun Context.getImageUri(): Uri {
    val file = this.createImageFile()
    return FileProvider.getUriForFile(
        Objects.requireNonNull(this),
        BuildConfig.APPLICATION_ID + ".provider", file
    )
}