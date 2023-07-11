package com.example.omapp.di

import com.example.omapp.domain.*
import org.koin.dsl.module

val domainModule = module {
    single<GetGenereShowsUseCase> { GetGenereShowsUseCaseImpl(repository = get()) }
}