package com.example.connex.ui.album_setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.album.AlbumInfo
import com.example.domain.model.ApiState
import com.example.domain.usecase.album.ReadAlbumInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumInfoViewModel @Inject constructor(
    val readAlbumInfoUseCase: ReadAlbumInfoUseCase
): ViewModel() {
    private val _albumInfo = MutableStateFlow(AlbumInfo())
    val albumInfo: StateFlow<AlbumInfo> = _albumInfo.asStateFlow()

    fun fetchReadAlbumInfo(albumId: String) {
        viewModelScope.launch {
            when(val result = readAlbumInfoUseCase(albumId).first()) {
                is ApiState.Success -> { _albumInfo.update { result.data } }
                is ApiState.Error -> {  }
                is ApiState.Loading -> {}
                is ApiState.NotResponse -> TODO()
            }
        }
    }




}