package com.example.data.network

import com.example.domain.DefaultResponse
import com.example.domain.model.request.AlbumRequest
import com.example.domain.model.response.AlbumIdResponse
import com.example.domain.model.request.ContentRequest
import com.example.domain.model.request.FriendIdRequest
import com.example.domain.model.response.AlbumResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface AlbumApi {
    // 앨범 생성
    @POST("/users/album/create")
    suspend fun createAlbum(@Body friendIdRequest: ContentRequest<List<FriendIdRequest>>): DefaultResponse<AlbumIdResponse>

    // 앨범 썸네일 설정
    @POST("/users/album/setthumbnail")
    suspend fun setAlbumThumbnail(@Body album: AlbumRequest): DefaultResponse<Unit>


    // 앨범 리스트 조회
    @GET("/users/get/albums/lists")
    suspend fun readAllAlbums(): DefaultResponse<List<AlbumResponse>>

    // 앨범 즐겨찾기 on, off
    @POST("/users/album/star")
    suspend fun updateAlbumFavorite(@Query("albumId") albumId: String): DefaultResponse<Unit>
}