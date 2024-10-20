package com.example.connex.ui.domain

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.exifinterface.media.ExifInterface
import com.example.connex.ui.album.Picture
import com.example.connex.utils.MemoryUnit
import com.example.connex.utils.toLocalDateTime
import com.example.connex.utils.uriToFilePath
import kotlin.math.roundToInt
import kotlin.math.roundToLong


// 갤러리에서 사진 가져오기
@Composable
fun takePhotoFromAlbumLauncher(update: (Uri) -> Unit): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val t = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) {

    }
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                update(uri)
            }
        } else if (result.resultCode != Activity.RESULT_CANCELED) {
        }
    }
}

// 갤러리에서 사진 가져오기(복수)
// PickMultipleVisualMedia 사용하는 경우 Manifest.xml에서 read 권한이 필요없음
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun takeMultiPhotoFromAlbumLauncher(
    maxCount: Int,
    update: (List<Picture>) -> Unit,
): ManagedActivityResultLauncher<PickVisualMediaRequest, List<Uri>> {
    val context = LocalContext.current

    return rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(
            maxCount
        )
    ) { result ->
        if (result.isNotEmpty()) {
            val pictures = result.map { uri ->
                val exif = uri.uriToFilePath(context)?.let {
//                    Log.d("image", "path: ${it}")
                    ExifInterface(it)
                }
                var localDateTime = exif?.dateTime?.toLocalDateTime() ?: ""
                val fileSize = uri.getImageSize(context) ?: 0L
                Log.d("image", "fileSize: ${fileSize}")
                Picture(uri, localDateTime, fileSize)
            }

//            result.forEach { uri ->
//
//
//                val exif = uri.uriToFilePath(context)?.let {
////                    Log.d("image", "path: ${it}")
//                    ExifInterface(it)
//                }
//                exif?.latLong?.get(0) // 위도 latitude
//                exif?.latLong?.get(1) // 경도 longitude
//                var localDateTime = exif?.dateTime?.toLocalDateTime() ?: ""
//                val fileSize = uri.getImageSize(context)
//                Log.d("image", "dateTime: ${localDateTime}")
//                Log.d("image", "fileSize: ${fileSize}")
//            }
            update(pictures)
        }
    }
}


// 이미지 용량 가져오기, KB 단위
fun Uri.getImageSize(context: Context): Long? {
    val fileDescriptor = context.contentResolver.openAssetFileDescriptor(this, "r")
    return fileDescriptor?.length?.div(1000f)?.roundToLong()
}


val takePhotoFromAlbumIntent =
    Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
        type = "image/*"
        action = Intent.ACTION_GET_CONTENT
        putExtra(
            Intent.EXTRA_MIME_TYPES,
            arrayOf("image/jpeg", "image/png", "image/bmp", "image/webp")
        )
        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
    }

