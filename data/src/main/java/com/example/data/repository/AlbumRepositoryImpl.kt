package com.example.data.repository

import com.example.data.network.AlbumApi
import com.example.domain.model.ApiState
import com.example.domain.model.request.AlbumRequest
import com.example.domain.model.response.album.AlbumIdResponse
import com.example.domain.model.request.ContentRequest
import com.example.domain.model.request.FriendIdRequest
import com.example.domain.model.response.album.AlbumResponse
import com.example.domain.model.response.PictureIdResponse
import com.example.domain.model.response.album.AlbumInfo
import com.example.domain.model.response.album.AlbumInfoResponse
import com.example.domain.model.response.album.asDomain
import com.example.domain.model.safeFlow
import com.example.domain.model.safeFlow2
import com.example.domain.model.safeFlowUnit
import com.example.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val albumApi: AlbumApi,
) : AlbumRepository {
    override fun createAlbum(friendIds: List<FriendIdRequest>): Flow<ApiState<AlbumIdResponse>> =
        safeFlow {
            albumApi.createAlbum(ContentRequest(friendIds))
        }

    override fun setAlbumThumbnail(albumId: String, albumName: String, albumImage: String) =
        safeFlowUnit {
            albumApi.setAlbumThumbnail(AlbumRequest(albumId, albumName, albumImage))
        }

    override fun readAllAlbums(): Flow<ApiState<List<AlbumResponse>>> =
        safeFlow { albumApi.readAllAlbums() }


    override fun updateAlbumFavorite(albumId: String) = safeFlowUnit {
        albumApi.updateAlbumFavorite(albumId)
    }

    override fun readAlbumInfo(albumId: String): Flow<ApiState<AlbumInfo>> =
        safeFlow2(apiFunc =  { albumApi.readAlbumInfo(albumId) }) { it.asDomain() }

    override fun readAllPhotos(albumId: String): Flow<ApiState<List<PictureIdResponse>>> =
        safeFlow { albumApi.readAllPhotos(albumId) }

    override fun uploadPhotos(
        albumId: String,
        file: List<MultipartBody.Part>,
    ): Flow<ApiState<Unit>> =
        safeFlowUnit {
            albumApi.uploadPhotos(albumId, file)
        }


}