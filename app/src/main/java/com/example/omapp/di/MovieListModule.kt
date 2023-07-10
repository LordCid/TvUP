package com.example.omapp.di

import com.example.omapp.presentation.list.MovieListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieListModule = module {
    viewModel {
        MovieListViewModel(
            getMoviesUseCase = get(),
            setFavoriteMovieUseCase = get(),
            ioDispatcher = get()
        )
    }
}