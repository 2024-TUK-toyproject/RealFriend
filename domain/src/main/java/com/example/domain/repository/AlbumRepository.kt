package com.example.domain.repository

import com.example.domain.model.ApiState
import com.example.domain.model.request.FriendIdRequest
import com.example.domain.model.response.PictureIdResponse
import com.example.domain.model.response.PictureInfo
import com.example.domain.model.response.PictureInfoResponse
import com.example.domain.model.response.album.AlbumIdResponse
import com.example.domain.model.response.album.AlbumInfo
import com.example.domain.model.response.album.AlbumResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface AlbumRepository {
    fun createAlbum(friendIds: List<FriendIdRequest>): Flow<ApiState<AlbumIdResponse>>

    fun setAlbumThumbnail(albumId: String, albumName: String, albumImage: String): Flow<ApiState<Unit>>

    fun readAllAlbums(): Flow<ApiState<List<AlbumResponse>>>

    fun updateAlbumFavorite(albumId: String): Flow<ApiState<Unit>>

    fun readAlbumInfo(albumId: String): Flow<ApiState<AlbumInfo>>

    fun readAllPhotos(albumId: String): Flow<ApiState<List<PictureIdResponse>>>

    fun uploadPhotos(albumId: String, file: List<MultipartBody.Part>): Flow<ApiState<Unit>>

    fun readPhotoInfo(photoId: String): Flow<ApiState<PictureInfo>>
}