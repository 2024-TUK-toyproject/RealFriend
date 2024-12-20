package com.example.domain.usecase.album

import com.example.domain.repository.AlbumRepository
import javax.inject.Inject

class ReadAllCommentsUseCase @Inject constructor(private val albumRepository: AlbumRepository) {
    operator fun invoke(photoId: String) =
        albumRepository.readAllComments(photoId)

}