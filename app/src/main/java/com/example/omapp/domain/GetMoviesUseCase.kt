package com.example.omapp.domain

import androidx.paging.*
import com.example.omapp.domain.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface GetMoviesUseCase {
    suspend operator fun invoke(scope: CoroutineScope): Flow<PagingData<Movie>>
}

class GetMoviesUseCaseImpl(
    private val moviePagingSource: PagingSource<Int, Movie>
) : GetMoviesUseCase {
    companion object{
        private const val INIT_LOAD_SIZE = 5
        private const val PAGE_SIZE = 5
        private const val PREFETCH_DISTANCE = 1
    }

    override suspend fun invoke(scope: CoroutineScope) : Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(
                pageSize = PAGE_SIZE, prefetchDistance = PREFETCH_DISTANCE,
                initialLoadSize = INIT_LOAD_SIZE, enablePlaceholders = true
            )
        ) {
            moviePagingSource
        }.flow.cachedIn(scope)
    }


}