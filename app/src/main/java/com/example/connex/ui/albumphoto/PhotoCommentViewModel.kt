package com.example.connex.ui.albumphoto

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.album.CommentInfo
import com.example.domain.model.ApiState
import com.example.domain.usecase.album.PostCommentUseCase
import com.example.domain.usecase.album.ReadAllCommentsUseCase
import com.example.domain.usecase.user.ReadUserImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PhotoCommentUiState(
    val input: String = "",
    val userProfile: ApiState<String> = ApiState.Loading,
    val comments: ApiState<Set<CommentInfo>> = ApiState.Loading,
)

@HiltViewModel
class PhotoCommentViewModel @Inject constructor(
    private val readUserImageUseCase: ReadUserImageUseCase,
    private val readAllCommentsUseCase: ReadAllCommentsUseCase,
    private val postCommentUseCase: PostCommentUseCase,

    ): ViewModel() {

    private val _input = MutableStateFlow("")
    val input: StateFlow<String> = _input.asStateFlow()

    private val _userProfile = MutableStateFlow("")
    val userProfile: StateFlow<String> = _userProfile.asStateFlow()

    private val _comments = MutableStateFlow<Set<CommentInfo>>(emptySet())
    val comments: StateFlow<Set<CommentInfo>> = _comments.asStateFlow()

    val photoCommentUiState = combine(_input, _userProfile, _comments) { input, userProfile, comments ->
        PhotoCommentUiState(input = input, userProfile = ApiState.Success(userProfile), comments = ApiState.Success(comments))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PhotoCommentUiState()
    )

    fun updateComment(newComment: String) {
        _input.value = newComment
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

    fun fetchReadAllComments(photoId: String) {
        viewModelScope.launch {
            when (val result = readAllCommentsUseCase(photoId).first()) {
                is ApiState.Error -> { Log.d("daeyoung", "error: ${result.errMsg}") }
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> { Log.d("daeyoung", "error: ${result.exception}, msg: ${result.message}") }
                is ApiState.Success -> { _comments.update { result.data.toSet() } }
            }
        }
    }

    fun fetchPostComment(photoId: String, comment: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            when (val result = postCommentUseCase(photoId = photoId, comment = comment).first()) {
                is ApiState.Error -> { Log.d("daeyoung", "error: ${result.errMsg}") }
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> { Log.d("daeyoung", "error: ${result.exception}, msg: ${result.message}") }
                is ApiState.Success -> {
                    fetchReadAllComments(photoId)
                    _input.update { "" }
                    onSuccess()
                }
            }
        }
    }
}