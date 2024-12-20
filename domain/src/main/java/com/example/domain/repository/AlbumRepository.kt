package com.example.domain.repository

import com.example.domain.entity.album.AlbumInfo
import com.example.domain.entity.album.CommentInfo
import com.example.domain.entity.album.PictureInfo
import com.example.domain.model.ApiState
import com.example.domain.model.request.FriendIdRequest
import com.example.domain.model.response.album.AlbumIdResponse
import com.example.domain.model.response.album.AlbumThumbnailInfoResponse
import com.example.domain.model.response.album.PictureIdResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface AlbumRepository {
    fun createAlbum(friendIds: List<FriendIdRequest>): Flow<ApiState<AlbumIdResponse>>

    fun setAlbumThumbnail(albumId: String, albumName: String, albumImage: String): Flow<ApiState<Unit>>

    fun readAllAlbums(): Flow<ApiState<List<AlbumThumbnailInfoResponse>>>

    fun updateAlbumFavorite(albumId: String): Flow<ApiState<Unit>>

    fun readAlbumInfo(albumId: String): Flow<ApiState<AlbumInfo>>

    fun readAllPhotos(albumId: String): Flow<ApiState<List<PictureIdResponse>>>

    fun uploadPhotos(albumId: String, file: List<MultipartBody.Part>): Flow<ApiState<Unit>>

    fun readPhotoInfo(photoId: String): Flow<ApiState<PictureInfo>>

    fun updatePhotoOfAlbumFavorite(photoId: String): Flow<ApiState<Unit>>

    fun postComment(photoId: String, comment: String): Flow<ApiState<Unit>>

    fun readAllComments(photoId: String): Flow<ApiState<List<CommentInfo>>>

}