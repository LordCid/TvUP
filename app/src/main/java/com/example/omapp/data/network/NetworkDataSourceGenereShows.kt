package com.example.omapp.data.network

import com.example.omapp.common.DataResponse
import com.example.omapp.domain.model.GenereShows

interface NetworkDataSourceGenereShows {
    suspend fun getGenereShows(railsId: String): DataResponse<List<GenereShows>>
}