package com.example.omapp.data.network

import com.example.omapp.common.DataResponse
import com.example.omapp.domain.model.Movie

interface NetworkDataSource {
    suspend fun getMovieList(page: Int): DataResponse<List<Movie>>
    suspend fun getDetail(id: String): DataResponse<Movie>
}

