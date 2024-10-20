package com.example.domain.usecase.album

import androidx.lifecycle.ViewModel
import com.example.domain.repository.AlbumRepository
import javax.inject.Inject

class ReadAllPicturesUseCase @Inject constructor(private val albumRepository: AlbumRepository): ViewModel() {
    operator fun invoke(albumId: String) = albumRepository.readAllPhotos(albumId)
}