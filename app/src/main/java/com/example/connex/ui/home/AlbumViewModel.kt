package com.example.connex.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ApiState
import com.example.domain.model.response.album.AlbumThumbnailInfoResponse
import com.example.domain.usecase.album.ReadAllAlbumsUseCase
import com.example.domain.usecase.album.UpdateAlbumFavoriteUseCase
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

data class AlbumUiState(
    val search: String = "",
    val albums: ApiState<List<Album>> = ApiState.Loading,
)

class Album(
    val albumId: String,
    val albumName: String,
    val albumThumbnail: String,
    val userCount: Int,
    val photoCount: Int,
    favoriteInit: Boolean,
) {
    var isFavorite by mutableStateOf(favoriteInit)

    override fun toString(): String =
        "Album(albumId='$albumId', albumName='$albumName', albumThumbnail='$albumThumbnail', userCount=$userCount, photoCount=$photoCount, isFavorite=$isFavorite)"
}

fun AlbumThumbnailInfoResponse.asDomain() =
    Album(
        albumId = albumId ?: "",
        albumName = albumName ?: "",
        albumThumbnail = albumThumbnail ?: "",
        favoriteInit = isStared ?: false,
        userCount = albumMemberCount ?: 0,
        photoCount = albumPictureCount ?: 0,
    )

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val readAllAlbumsUseCase: ReadAllAlbumsUseCase,
    private val updateAlbumFavoriteUseCase: UpdateAlbumFavoriteUseCase,
) : ViewModel() {

    private val _albums = MutableStateFlow<ApiState<List<Album>>>(ApiState.Loading)
    val albums: StateFlow<ApiState<List<Album>>> = _albums.asStateFlow()

    private val _search = MutableStateFlow("")
    val search: StateFlow<String> = _search.asStateFlow()

    fun updateSearch(search: String) {
        _search.update { search }
    }

    val albumUiState = combine(_search, _albums) { search, albums ->
        AlbumUiState(search, albums)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AlbumUiState()
    )

    fun fetchReadAlbums(notResponse: () -> Unit) {
        viewModelScope.launch {
            when (val result = readAllAlbumsUseCase().first()) {
                is ApiState.Error -> TODO()
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> {
                    if (result.exception is ConnectException) {
                        notResponse()
                    }

                    Log.d("daeyoung", "exception: ${result.exception}\n${result.message}")
                }

                is ApiState.Success -> {
                    val albums =
                        (result.data as List<AlbumThumbnailInfoResponse>).map { album -> album.asDomain() }
                    _albums.update { ApiState.Success(albums) }
                    Log.d("daeyoung", "fetchReadAlbums: ${_albums.value}")
                }
            }
        }
    }

    fun fetchUpdateAlbumFavorite(albumId: String, notResponse: () -> Unit) {
        viewModelScope.launch {
            when (val result = updateAlbumFavoriteUseCase(albumId).first()) {
                is ApiState.Error -> TODO()
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> {
                    if (result.exception is ConnectException) {
                        notResponse()
                    }

                    Log.d("daeyoung", "exception: ${result.exception}\n${result.message}")
                }

                is ApiState.Success -> {
                    (albums.value as ApiState.Success<List<Album>>).data.find { it.albumId == albumId }
                        ?.let { it.isFavorite = !it.isFavorite }
                }
            }
        }
    }


}