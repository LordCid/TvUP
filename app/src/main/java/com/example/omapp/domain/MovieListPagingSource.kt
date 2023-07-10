package com.example.omapp.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.omapp.common.DataResponse
import com.example.omapp.domain.model.Movie
import java.util.ArrayList

class MovieListPagingSource(
    private val repository: Repository
) : PagingSource<Int, Movie>() {


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val currentPage = params.key ?: 1
        return when (val result = repository.getMovieList(currentPage)) {
            is DataResponse.Success -> {
                if (result.data.isEmpty()) {
                    LoadResult.Page(ArrayList<Movie>(), params.key, params.key)
                } else {
                    val nextPageNumber =
                        if (result.data.size == params.loadSize) currentPage + 1 else null
                    LoadResult.Page(data = result.data, null, nextPageNumber)
                }
            }
            is DataResponse.Failure -> LoadResult.Error(Throwable())
        }
    }


}


