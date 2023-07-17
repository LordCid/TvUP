package com.example.omapp.di

import com.example.omapp.common.Mapper
import com.example.omapp.data.network.mapper.MapperGenereShows
import com.example.omapp.data.network.mapper.MapperGenereShows.Companion.MOVIE_GENERE_SHOWS_MAPPER_NAME
import com.example.omapp.data.network.model.GenereShowsDTO
import com.example.omapp.domain.model.GenereShows
import com.example.omapp.presentation.model.GenereShowsUI
import com.example.omapp.presentation.model.MapperUI
import com.example.omapp.presentation.model.MapperUI.Companion.UI_MAPPER_NAME
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mapperModule = module {
    single<Mapper<List<GenereShows>, List<GenereShowsDTO>>>(named(MOVIE_GENERE_SHOWS_MAPPER_NAME)) {
        MapperGenereShows()
    }
    single<Mapper<List<GenereShowsUI>, List<GenereShows>>>(named(UI_MAPPER_NAME)) {
        MapperUI()
    }
}