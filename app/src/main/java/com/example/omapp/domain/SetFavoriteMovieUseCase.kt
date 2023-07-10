package com.example.omapp.domain

import com.example.omapp.common.DataResponse

interface SetFavoriteMovieUseCase {
    suspend operator fun invoke(id: Long, isFavorite: Boolean) : DataResponse<Boolean>
}

class SetFavoriteMovieUseCaseImpl (
    private val repository: Repository
) : SetFavoriteMovieUseCase {
    override suspend fun invoke(id: Long, isFavorite: Boolean) = repository.setFavorite(id, isFavorite)
}