package com.example.omapp.di

import com.example.omapp.common.Mapper
import com.example.omapp.data.local.mapper.LocalModelToMovieMapper
import com.example.omapp.data.local.mapper.LocalModelToMovieMapper.Companion.LOCAL_TO_MOVIE_MAPPER_NAME
import com.example.omapp.data.local.mapper.MovieToLocalModelMapper
import com.example.omapp.data.local.mapper.MovieToLocalModelMapper.Companion.MOVIE_TO_LOCAL_MAPPER_NAME
import com.example.omapp.data.local.room.MovieRoomModel
import com.example.omapp.data.network.mapper.MovieDetailMapper
import com.example.omapp.data.network.mapper.MovieDetailMapper.Companion.MOVIE_DETAIL_MAPPER_NAME
import com.example.omapp.data.network.mapper.MovieListMapper
import com.example.omapp.data.network.mapper.MovieListMapper.Companion.MOVIE_LIST_MAPPER_NAME
import com.example.omapp.data.network.model.MovieDTO
import com.example.omapp.data.network.model.MovieDetailResponseDTO
import com.example.omapp.data.network.model.MovieListResponseDTO
import com.example.omapp.domain.model.Movie
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mapperModule = module {

    single<Mapper<Movie, MovieDetailResponseDTO>> (named(MOVIE_DETAIL_MAPPER_NAME)) { MovieDetailMapper() }
    single<Mapper<List<Movie>, MovieListResponseDTO>>(named(MOVIE_LIST_MAPPER_NAME)) { MovieListMapper() }
    single<Mapper<MovieRoomModel, Movie>>(named(MOVIE_TO_LOCAL_MAPPER_NAME)) { MovieToLocalModelMapper() }
    single<Mapper<Movie, MovieRoomModel>>(named(LOCAL_TO_MOVIE_MAPPER_NAME)) { LocalModelToMovieMapper() }

}