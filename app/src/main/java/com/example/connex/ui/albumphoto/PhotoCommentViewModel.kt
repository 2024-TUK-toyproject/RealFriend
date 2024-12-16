package com.example.connex.ui.albumphoto

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connex.ui.album.PictureOfAlbumUiState
import com.example.domain.model.ApiState
import com.example.domain.usecase.user.ReadUserImageUseCase
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

data class PhotoCommentUiState(
    val comment: String = "",
    val userProfile: ApiState<String> = ApiState.Loading
)

@HiltViewModel
class PhotoCommentViewModel @Inject constructor(
    val readUserImageUseCase: ReadUserImageUseCase

): ViewModel() {

    private val _comment = MutableStateFlow("")
    val comment: StateFlow<String> = _comment

    private val _userProfile = MutableStateFlow("")
    val userProfile: StateFlow<String> = _userProfile

    val photoCommentUiState = combine(_comment, _userProfile) { comment, userProfile ->
        PhotoCommentUiState(comment = comment, userProfile = ApiState.Success(userProfile))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PhotoCommentUiState()
    )

    fun updateComment(newComment: String) {
        _comment.value = newComment
    }

    fun fetchReadUserImage() {
        viewModelScope.launch {
            when (val result = readUserImageUseCase().first()) {
                is ApiState.Error -> {}
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> { Log.d("daeyoung", "error: ${result.exception}, msg: ${result.message}") }
                is ApiState.Success -> { _userProfile.update { result.data.profileImage } }
            }
        }
    }
}