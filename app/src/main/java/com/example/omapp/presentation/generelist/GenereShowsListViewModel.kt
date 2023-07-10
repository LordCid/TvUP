package com.example.omapp.presentation.generelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omapp.ERROR_GENERIC_MESSAGE
import com.example.omapp.common.DataResponse
import com.example.omapp.domain.GetGenereShowsUseCase
import com.example.omapp.domain.model.GenereShows
import com.example.omapp.domain.model.Movie
import com.example.omapp.presentation.detail.MovieDetailViewState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

sealed class GenereShowsListViewState {
    object Loading : GenereShowsListViewState()
    class ShowMovies(val data: List<GenereShows>) : GenereShowsListViewState()
    class Error(val message: String) : GenereShowsListViewState()
}

class GenereShowsListViewModel(
    private val getGenereShowsUseCase: GetGenereShowsUseCase
): ViewModel() {

    private val mutableViewState = MutableLiveData<GenereShowsListViewState>()
    val viewState: LiveData<GenereShowsListViewState>
        get() = mutableViewState

    fun getGenereShows(railId: String) {
        mutableViewState.value = GenereShowsListViewState.Loading
        viewModelScope.launch {
            getGenereShowsUseCase(railId)
                .catch {
                    mutableViewState.postValue(
                        GenereShowsListViewState.Error(it.message ?: ERROR_GENERIC_MESSAGE)
                    )
                }
                .collect { mutableViewState.postValue(GenereShowsListViewState.ShowMovies(it)) }
        }
    }
}