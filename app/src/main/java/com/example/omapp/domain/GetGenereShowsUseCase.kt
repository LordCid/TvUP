package com.example.omapp.domain

import com.example.omapp.common.DataResponse
import com.example.omapp.domain.model.GenereShows
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GetGenereShowsUseCase {
    suspend operator fun invoke(railId: String): Flow<List<GenereShows>>
}

class GetGenereShowsUseCaseImpl(
    private val repository: Repository
) : GetGenereShowsUseCase {
    override suspend fun invoke(railId: String) = flow {
        when(val result = repository.getGenereShow(railId)) {
            is DataResponse.Failure -> throw result.error
            is DataResponse.Success -> emit(result.data)
        }
    }

}