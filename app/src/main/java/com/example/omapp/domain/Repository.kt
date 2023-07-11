package com.example.omapp.domain

import com.example.omapp.common.DataResponse
import com.example.omapp.domain.model.GenereShows

interface Repository {
    suspend fun  getGenereShow(railId: String): DataResponse<List<GenereShows>>
}