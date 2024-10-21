package com.example.domain.usecase.album

import com.example.domain.repository.AlbumRepository
import javax.inject.Inject

class ReadPhotoInfoUseCase @Inject constructor(private val albumRepository: AlbumRepository) {
    operator fun invoke(photoId: String) = albumRepository.readPhotoInfo(photoId)
}