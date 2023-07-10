package com.example.omapp.di

import androidx.paging.PagingSource
import com.example.omapp.domain.*
import com.example.omapp.domain.model.Movie
import org.koin.dsl.module

val domainModule = module {
    single<PagingSource<Int, Movie>> { MovieListPagingSource(repository = get()) }
    single<GetMoviesUseCase> { GetMoviesUseCaseImpl(moviePagingSource = get()) }
    single<GetMovieDetailUseCase> { GetMovieDetailUseCaseImpl(repository = get())}
    single<SetFavoriteMovieUseCase> { SetFavoriteMovieUseCaseImpl(repository = get())}
    single<GetGenereShowsUseCase> { GetGenereShowsUseCaseImpl(repository = get()) }
}