package com.example.omapp.data.local

import com.example.omapp.common.DataResponse
import com.example.omapp.domain.model.Movie

interface LocalDataSource {
    suspend fun storeMovies(movies: List<Movie>)
    suspend fun getMovieList(): DataResponse<List<Movie>>
    suspend fun invalidate()
    suspend fun setFavoriteMovie(id: Long, isFavorite: Boolean) : DataResponse<Boolean>
    suspend fun checkIfFavorite(id: Long) : Boolean
}