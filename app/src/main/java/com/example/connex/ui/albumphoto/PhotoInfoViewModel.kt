package com.example.connex.ui.albumphoto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.album.PictureInfo
import com.example.domain.model.ApiState
import com.example.domain.usecase.album.ReadPhotoInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoInfoViewModel @Inject constructor(
    private val readPhotoInfoUseCase: ReadPhotoInfoUseCase,

): ViewModel() {

    private val _photoInfo = MutableStateFlow(PictureInfo())
    val photoInfo: StateFlow<PictureInfo> = _photoInfo



    fun fetchReadPhotoInfo(photoId: String) {
        viewModelScope.launch {
            when (val result = readPhotoInfoUseCase(photoId).first()) {
                is ApiState.Error -> {}
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> TODO()
                is ApiState.Success -> { _photoInfo.update { result.data } }
            }
        }
    }
}