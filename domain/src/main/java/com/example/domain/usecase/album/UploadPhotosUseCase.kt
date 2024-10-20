package com.example.domain.usecase.album

import com.example.domain.repository.AlbumRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadPhotosUseCase @Inject constructor(private val albumRepository: AlbumRepository) {
    operator fun invoke(albumId: String, file: List<MultipartBody.Part>) =
        albumRepository.uploadPhotos(albumId, file)
}