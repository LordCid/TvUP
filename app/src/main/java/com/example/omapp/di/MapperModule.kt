package com.example.omapp.di

import com.example.omapp.common.Mapper
import com.example.omapp.data.network.mapper.MapperGenereShows
import com.example.omapp.data.network.mapper.MapperGenereShows.Companion.MOVIE_GENERE_SHOWS_MAPPER_NAME
import com.example.omapp.data.network.model.GenereShowsDTO
import com.example.omapp.domain.model.GenereShows
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mapperModule = module {
    single<Mapper<List<GenereShows>, List<GenereShowsDTO>>>(named(MOVIE_GENERE_SHOWS_MAPPER_NAME)) {
        MapperGenereShows()
    }
}