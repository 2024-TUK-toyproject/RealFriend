package com.example.connex.ui.album

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connex.utils.MemoryUnit
import com.example.connex.utils.asMultipart
import com.example.domain.model.ApiState
import com.example.domain.usecase.album.UploadPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Picture(
    val uri: Uri,
    val date: String,
    val fileSize: Long,
)

@HiltViewModel
class AddPictureViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val uploadPhotosUseCase: UploadPhotosUseCase,
    ) : ViewModel() {

    private val _imageUrl = MutableStateFlow<List<Picture>>(emptyList())
    val imageUrl: StateFlow<List<Picture>> = _imageUrl

    fun updateImageUri(imageUri: List<Picture>) {
        val empty = (_imageUrl.value + imageUri).distinct()
        _imageUrl.update { empty }

    }

    fun deleteImage(uri: Uri) {
        val updatedList = _imageUrl.value.toMutableList()
        updatedList.removeIf { it.uri == uri }
        _imageUrl.update { updatedList }
    }

    fun fetchUploadPhotos(albumId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val files = imageUrl.value.map { it.uri.asMultipart("file",it.date , context.contentResolver)!! }
            when (val result = uploadPhotosUseCase(albumId, files).first()) {
                is ApiState.Error -> {}
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> TODO()
                is ApiState.Success -> { onSuccess() }
            }
        }
    }

}