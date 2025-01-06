package com.example.data.network

import com.example.domain.DefaultResponse
import com.example.domain.model.request.AlbumRequest
import com.example.domain.model.request.CommentRequest
import com.example.domain.model.request.ContentRequest
import com.example.domain.model.request.FriendIdRequest
import com.example.domain.model.response.album.AlbumIdResponse
import com.example.domain.model.response.album.AlbumThumbnailInfoResponse
import com.example.domain.model.response.album.PictureIdResponse
import com.example.domain.model.response.album.PictureInfoResponse
import com.example.domain.model.response.album.AlbumInfoResponse
import com.example.domain.model.response.album.CommentResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
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
    suspend fun readAllAlbums(): DefaultResponse<List<AlbumThumbnailInfoResponse>>

    // 앨범 즐겨찾기 on, off
    @POST("/users/album/star")
    suspend fun updateAlbumFavorite(@Query("albumId") albumId: String): DefaultResponse<Unit>

    // 앨범의 정보 조회
    @GET("/users/get/album/{albumId}/info")
    suspend fun readAlbumInfo(@Path("albumId") albumId: String): DefaultResponse<AlbumInfoResponse>

    // 앨범의 전체 사진 조회
    @GET("/users/album/get/photos")
    suspend fun readAllPhotos(@Query("albumId") albumId: String): DefaultResponse<List<PictureIdResponse>>

    // 앨범의 사진 상세정보 조회
    @GET("/users/album/get/photos/{photoId}/info")
    suspend fun readPhotoInfo(@Path("photoId") photoId: String): DefaultResponse<PictureInfoResponse>

    // 앨범의 사진 업로드
    @Multipart
    @POST("/users/album/post")
    suspend fun uploadPhotos(@Query("albumId") albumId: String, @Part file: List<MultipartBody.Part>): DefaultResponse<Unit>

    // 앨범의 사진 삭제

    // 앨범의 사진 좋아요 on, off
    @POST("/users/album/star/photos")
    suspend fun updatePhotoOfAlbumFavorite(@Query("photoId") photoId: String): DefaultResponse<Unit>

    // 앨범의 댓글 작성
    @POST("/users/album/reply")
    suspend fun postComment(@Body comment: CommentRequest): DefaultResponse<Unit>

    // 앨범의 댓글 전체 조회
    @GET("/users/album/{photoId}/get/reply")
    suspend fun readAllComments(@Path("photoId") photoId: String): DefaultResponse<List<CommentResponse>>
}