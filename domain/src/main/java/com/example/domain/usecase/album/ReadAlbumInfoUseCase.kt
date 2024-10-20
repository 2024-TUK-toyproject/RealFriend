package com.example.domain.usecase.album

import com.example.domain.repository.AlbumRepository
import javax.inject.Inject

class ReadAlbumInfoUseCase @Inject constructor(private val albumRepository: AlbumRepository) {
    operator fun invoke(albumId: String) = albumRepository.readAlbumInfo(albumId)
}