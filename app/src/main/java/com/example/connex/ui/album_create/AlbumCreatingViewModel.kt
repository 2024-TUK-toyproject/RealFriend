package com.example.connex.ui.album_create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connex.ui.home.FriendUiState
import com.example.connex.utils.Constants
import com.example.domain.model.ApiState
import com.example.domain.model.request.FriendIdRequest
import com.example.domain.usecase.friend.ReadAllFriendsUseCase
import com.example.domain.usecase.album.CreateAlbumUseCase
import com.example.domain.usecase.album.SetAlbumThumbnailUseCase
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
import java.net.ConnectException
import javax.inject.Inject

data class FriendSelectUiState(
    val search: String = "",
    val friends: ApiState<List<FriendUiState>> = ApiState.Loading,
)

@HiltViewModel
class AlbumCreatingViewModel @Inject constructor(
    val readAllFriendsUseCase: ReadAllFriendsUseCase,
    val createAlbumUseCase: CreateAlbumUseCase,
    val setAlbumThumbnailUseCase: SetAlbumThumbnailUseCase
) : ViewModel() {

    var albumId = 0L

    private val _search = MutableStateFlow("")
    val search: StateFlow<String> = _search.asStateFlow()

    private val _friends = MutableStateFlow(emptyList<FriendUiState>())
    val friends: StateFlow<List<FriendUiState>> = _friends.asStateFlow()

    private val _filteredFriends = MutableStateFlow<ApiState<List<FriendUiState>>>(ApiState.Loading)
    val filteredFriends: StateFlow<ApiState<List<FriendUiState>>> = _filteredFriends.asStateFlow()

    private val _albumName = MutableStateFlow("")
    val albumName: StateFlow<String> = _albumName

    val friendSelectUiState = combine(_search, _filteredFriends) { search, friends ->
        FriendSelectUiState(search, friends)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FriendSelectUiState()
    )


    fun updateSearch(search: String) {
        _search.update { search }
    }

    fun updateAlbumName(search: String) {
        _albumName.update { search }
        Log.d("daeyoung", "albumName: ${albumName.value}")
    }


    fun realTimeSearching(name: String) {
        val filtering = friends.value.filter { it.friend.name.contains(name) }
        if (name.isEmpty()) {
            _filteredFriends.update { ApiState.Success(friends.value) }
        } else {
            _filteredFriends.update { ApiState.Success(filtering) }
        }
    }

    fun selectOrUnSelectFriend(userId: Long, isSelect: Boolean) {
        friends.value.find { it.friend.userId == userId }
            ?.let { it.isSelect = isSelect }
        (filteredFriends.value as ApiState.Success<List<FriendUiState>>).data.find { it.friend.userId == userId }
            ?.let {
                it.isSelect = isSelect
            }
    }

    fun fetchReadAllFriends(notResponse: (String) -> Unit) {
        viewModelScope.launch {
            when (val result = readAllFriendsUseCase().first()) {
                is ApiState.Error -> TODO()
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> {
                    if (result.exception is ConnectException) {
                        Log.d(
                            "daeyoung",
                            "message: ${result.message}\nexception: ${result.exception}"
                        )
                        notResponse("인터넷이 연결이 되어 있지 않습니다.")
                    }
                }

                is ApiState.Success -> {
                    result.data.also { list ->
                        val mappedList = list.map { FriendUiState(it, false) }
                            .filter { it.friend.isFriend }
                        _friends.update { mappedList }
                        _filteredFriends.update { ApiState.Success(mappedList) }
                    }
                }
            }
        }
    }

    fun fetchCreateAlbum(onSuccess: () -> Unit, notResponse: (String) -> Unit) {
        viewModelScope.launch {
            val friendsId = friends.value.filter { it.isSelect }
                .map { FriendIdRequest(it.friend.userId.toString()) }
            when (val result = createAlbumUseCase(friendsId).first()) {
                is ApiState.Error -> TODO()
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> {
                    if (result.exception is ConnectException) {
                        Log.d(
                            "daeyoung",
                            "message: ${result.message}\nexception: ${result.exception}"
                        )
                        notResponse("인터넷이 연결이 되어 있지 않습니다.")
                    }
                }

                is ApiState.Success -> {
                    albumId = result.data.albumId
                    onSuccess()
                }
            }
        }
    }
    fun fetchUpdateAlbumThumbnail(onSuccess: () -> Unit, notResponse: (String) -> Unit) {
        viewModelScope.launch {
            when (val result = setAlbumThumbnailUseCase(albumId, albumName.value, Constants.DEFAULT_PROFILE).first()) {
                is ApiState.Error -> Log.d("daeyoung", "message: ${result.errMsg}")
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> {
                    if (result.exception is ConnectException) {
                        notResponse("인터넷이 연결이 되어 있지 않습니다.")
                    }
                    Log.d("daeyoung", "message: ${result.message}\nexception: ${result.exception}")
                }

                is ApiState.Success -> { onSuccess() }
            }
        }
    }


}