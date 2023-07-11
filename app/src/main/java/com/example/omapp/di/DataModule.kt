package com.example.omapp.di

import com.example.omapp.data.network.NetworkDataSourceGenereShows
import com.example.omapp.data.network.NetworkDataSourceGenereShowsImpl
import com.example.omapp.data.RepositoryImpl
import com.example.omapp.data.network.mapper.MapperGenereShows.Companion.MOVIE_GENERE_SHOWS_MAPPER_NAME
import com.example.omapp.domain.Repository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single<NetworkDataSourceGenereShows> {
        NetworkDataSourceGenereShowsImpl(
            apiService = get(),
            mapperGenereShows = get(named(MOVIE_GENERE_SHOWS_MAPPER_NAME))
        )
    }

    single<Repository> {
        RepositoryImpl(
            networkDataSourceGenereShows = get()
        )
    }
}