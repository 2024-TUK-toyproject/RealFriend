package com.example.connex.ui.album

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ApiState
import com.example.domain.model.response.album.AlbumInfo
import com.example.domain.usecase.album.ReadAlbumInfoUseCase
import com.example.domain.usecase.album.ReadAllPicturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class PictureInfo(
    val id: Long,
    val image: String,
    val imageSize: String = "",
    val userName: String = "",
    val time: String = "",
    val profile: String = "",
    initialChecked: Boolean,
) {
    var isSelected by mutableStateOf(initialChecked)
}

data class PictureOfAlbumUiState(
    val pictures: ApiState<List<PictureInfo>> = ApiState.Loading,
    val albumInfo: ApiState<AlbumInfo> = ApiState.Loading,
)

@HiltViewModel
class PictureOfAlbumViewModel @Inject constructor(
    val readAllPicturesUseCase: ReadAllPicturesUseCase,
    val readAlbumInfoUseCase: ReadAlbumInfoUseCase,
) : ViewModel() {
    private val _pictures = MutableStateFlow(emptyList<PictureInfo>())
    val pictures: StateFlow<List<PictureInfo>> = _pictures

    private val _album = MutableStateFlow(AlbumInfo())
    val album: StateFlow<AlbumInfo> = _album

    val pictureOfAlbumUiState = combine(_pictures, _album) { picture, album ->
        PictureOfAlbumUiState(ApiState.Success(picture), ApiState.Success(album))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PictureOfAlbumUiState()
    )

    fun changeSelectedStateOfPicture(pictureId: Long) {
        _pictures.value.find { it.id == pictureId }?.let { it.isSelected = !it.isSelected }
    }

    fun selectAllOfPicture() {
        _pictures.value.map { it.isSelected = true }
    }

    fun unselectAllOfPicture() {
        _pictures.value.map { it.isSelected = false }
    }

    fun fetchReadAllPictures(albumId: String) {
        viewModelScope.launch {
            when (val result = readAllPicturesUseCase(albumId).first()) {
                is ApiState.Error -> TODO()
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> { Log.d("daeyoung", "fetchReadAllPictures: ${result.message}") }
                is ApiState.Success -> {
                    _pictures.update {
                        result.data.map { PictureInfo(id = it.photoId.toLong(), image = it.photoUrl, initialChecked = false) }
                    }
                }
            }
        }
    }

    fun fetchReadAlbumInfo(albumId: String) {
        viewModelScope.launch {
            when (val result = readAlbumInfoUseCase(albumId).first()) {
                is ApiState.Error -> TODO()
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> { Log.d("daeyoung", "fetchReadAlbumInfo: ${result.message}") }
                is ApiState.Success -> {
                    _album.update { result.data }
                }
            }
        }
    }


}