package com.example.domain.usecase.album

import com.example.domain.repository.AlbumRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class SetAlbumThumbnailUseCase @Inject constructor(private val albumRepository: AlbumRepository) {
    operator fun invoke(albumId: Long, albumName: String, file: MultipartBody.Part) {
        albumRepository.setAlbumThumbnail(albumId.toString(), albumName, file)
    }
}