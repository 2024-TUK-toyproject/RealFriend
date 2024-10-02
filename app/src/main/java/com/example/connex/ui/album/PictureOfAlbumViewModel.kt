package com.example.connex.ui.album

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.connex.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class PictureInfo(
    val id: Long,
    val image: String,
    val imageSize: String,
    val userName: String,
    val time: String,
    val profile: String,
    initialChecked: Boolean,
) {
    var isSelected by mutableStateOf(initialChecked)
}

@HiltViewModel
class PictureOfAlbumViewModel @Inject constructor(): ViewModel() {
    private val _pictures = MutableStateFlow(
        listOf(
            PictureInfo(0, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(1, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(2, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(3, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(4, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(5, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(6, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(7, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(8, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(9, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(10, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(11, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(12, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(13, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(14, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(15, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(16, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(17, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(18, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(19, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(20, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(21, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(22, Constants.DEFAULT_PROFILE, "", "", "", "", false),
            PictureInfo(23, Constants.DEFAULT_PROFILE, "", "", "", "", false),
        )
    )
    val pictures: StateFlow<List<PictureInfo>> = _pictures

    fun changeSelectedStateOfPicture(pictureId: Long) {
        _pictures.value.find { it.id == pictureId }?.let { it.isSelected = !it.isSelected }
    }

    fun selectAllOfPicture() {
        _pictures.value.map { it.isSelected = true }
    }

    fun unselectAllOfPicture() {
        _pictures.value.map { it.isSelected = false }
    }
}