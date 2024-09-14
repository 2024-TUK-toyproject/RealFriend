package com.example.domain.repository

import com.example.domain.model.ApiState
import com.example.domain.model.response.AlbumIdResponse
import com.example.domain.model.request.FriendIdRequest
import com.example.domain.model.response.AlbumResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface AlbumRepository {
    fun createAlbum(friendIds: List<FriendIdRequest>): Flow<ApiState<AlbumIdResponse>>

    fun setAlbumThumbnail(albumId: String, albumName: String, albumImage: String): Flow<ApiState<Unit>>

    fun readAllAlbums(): Flow<ApiState<List<AlbumResponse>>>

    fun updateAlbumFavorite(albumId: String): Flow<ApiState<Unit>>
}