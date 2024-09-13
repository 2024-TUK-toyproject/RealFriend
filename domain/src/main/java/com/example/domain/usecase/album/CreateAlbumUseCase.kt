package com.example.domain.usecase.album

import com.example.domain.model.request.FriendIdRequest
import com.example.domain.repository.AlbumRepository
import javax.inject.Inject

class CreateAlbumUseCase @Inject constructor(private val albumRepository: AlbumRepository) {
    operator fun invoke(friendIds: List<FriendIdRequest>) =
        albumRepository.createAlbum(friendIds)

}