package com.example.data.repository

import com.example.data.network.AlbumApi
import com.example.domain.model.ApiState
import com.example.domain.model.request.AlbumRequest
import com.example.domain.model.response.AlbumIdResponse
import com.example.domain.model.request.ContentRequest
import com.example.domain.model.request.FriendIdRequest
import com.example.domain.model.response.AlbumResponse
import com.example.domain.model.safeFlow
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

}