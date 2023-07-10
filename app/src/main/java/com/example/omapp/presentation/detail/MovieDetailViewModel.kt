package com.example.omapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omapp.ERROR_GENERIC_MESSAGE
import com.example.omapp.common.DataResponse
import com.example.omapp.domain.GetMovieDetailUseCase
import com.example.omapp.domain.SetFavoriteMovieUseCase
import com.example.omapp.domain.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


sealed class MovieDetailViewState {
    object Loading : MovieDetailViewState()
    class ShowMovies(val data: Movie) : MovieDetailViewState()
    class Error(val message: String) : MovieDetailViewState()
    class FavoriteUpdate(val isFavorite: Boolean) : MovieDetailViewState()
}

class MovieDetailViewModel (
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val setFavoriteMovieUseCase: SetFavoriteMovieUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val mutableViewState = MutableLiveData<MovieDetailViewState>()
    val viewState : LiveData<MovieDetailViewState>
        get() = mutableViewState

    private var movieId : Long = 0
    private var favoriteStatus = false

    fun getMovieDetail(id: String){
        mutableViewState.value = MovieDetailViewState.Loading
        viewModelScope.launch {
            getMovieDetailUseCase(id)
                .catch {
                    mutableViewState.postValue(
                        MovieDetailViewState.Error(it.message ?: ERROR_GENERIC_MESSAGE)
                    )
                }
                .collect { evaluateResponse(it) }

        }
    }

    fun setFavorite() {
        viewModelScope.launch {
            val result = withContext(ioDispatcher) {
                setFavoriteMovieUseCase(movieId, favoriteStatus.not())
            }
            when(result) {
                is DataResponse.Failure -> mutableViewState.postValue(MovieDetailViewState.Error(result.error.message ?:""))
                is DataResponse.Success -> result.data.let{
                    favoriteStatus = it
                    mutableViewState.postValue(MovieDetailViewState.FavoriteUpdate(it))
                }
            }
        }
    }

    private fun evaluateResponse(it: DataResponse<Movie>) {
        when (it) {
            is DataResponse.Failure ->
                mutableViewState.postValue(MovieDetailViewState.Error(it.error.message ?:""))
            is DataResponse.Success -> with(it.data) {
                movieId = id
                favoriteStatus = isFavorite
                mutableViewState.postValue(MovieDetailViewState.ShowMovies(this))
            }

        }
    }
}