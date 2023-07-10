package com.example.omapp.di

import com.example.omapp.presentation.detail.MovieDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieDetailModule = module {
    viewModel {
        MovieDetailViewModel(getMovieDetailUseCase = get(), setFavoriteMovieUseCase = get(), ioDispatcher = get())
    }
}