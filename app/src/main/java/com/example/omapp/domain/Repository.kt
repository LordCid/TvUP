package com.example.omapp.domain

import com.example.omapp.common.DataResponse
import com.example.omapp.domain.model.GenereShows
import com.example.omapp.domain.model.Movie
import java.util.*

interface Repository {
    suspend fun getMovieList(page: Int) : DataResponse<List<Movie>>
    suspend fun getMovieDetail(id: String) : DataResponse<Movie>
    suspend fun setFavorite(id: Long, isFavorite: Boolean) : DataResponse<Boolean>
    var currentDate: () -> Date
    suspend fun  getGenereShow(railId: String): DataResponse<List<GenereShows>>
}