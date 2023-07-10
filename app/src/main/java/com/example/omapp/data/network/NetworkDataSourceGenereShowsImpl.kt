package com.example.omapp.data.network

import com.example.omapp.ERROR_NETWORK_GENERIC_MESSAGE
import com.example.omapp.common.DataResponse
import com.example.omapp.common.ErrorResponse
import com.example.omapp.common.Mapper
import com.example.omapp.data.network.model.GenereShowsDTO
import com.example.omapp.domain.model.GenereShows
import com.example.omapp.domain.model.Movie
import retrofit2.awaitResponse

class NetworkDataSourceGenereShowsImpl(
    private val apiService: ApiService,
    private val mapperGenereShows: Mapper<List<GenereShows>, List<GenereShowsDTO>>
) : NetworkDataSourceGenereShows {
    override suspend fun getGenereShows(railsId: String): DataResponse<List<GenereShows>> {
        return runCatching {
            apiService.getGeneresShows(railsId).awaitResponse()
        }.fold(
            onSuccess = {
                it.body()?.let { listResponse ->
                    DataResponse.Success(
                        mapperGenereShows.map(listResponse)
                    )
                } ?: DataResponse.Success(emptyList())
            },
            onFailure = { DataResponse.Failure(ErrorResponse.Unexpected(it.message ?: ERROR_NETWORK_GENERIC_MESSAGE)) }
        )
    }
}