package com.example.omapp.di

import com.example.omapp.presentation.generelist.GenereShowsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val genereShowsModule = module {
    viewModel {
        GenereShowsListViewModel(
            getGenereShowsUseCase = get(),
            mapper = get()
        )
    }
}