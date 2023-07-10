package com.example.omapp.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.omapp.common.DataResponse
import com.example.omapp.common.fold
import com.example.omapp.domain.GetMoviesUseCase
import com.example.omapp.domain.SetFavoriteMovieUseCase
import com.example.omapp.domain.model.Movie
import com.example.omapp.presentation.detail.MovieDetailViewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val setFavoriteMovieUseCase: SetFavoriteMovieUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val mutableViewState = MutableLiveData<PagingData<Movie>>()
    val viewState: LiveData<PagingData<Movie>>
        get() = mutableViewState


    suspend fun getMovies(scope: CoroutineScope) {
        getMoviesUseCase(scope)
            .catch { }
            .collect {
                mutableViewState.postValue(it)
            }
    }

    fun setFavorite(id: Long, isFavorite: Boolean) {
        viewModelScope.launch {
            val result = withContext(ioDispatcher) {
                setFavoriteMovieUseCase(id, isFavorite)
            }
            if (result is DataResponse.Success) {
                getMovies(viewModelScope)
            }
        }
    }


}