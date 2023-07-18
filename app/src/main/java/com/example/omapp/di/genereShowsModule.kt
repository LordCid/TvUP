package com.example.omapp.di

import com.example.omapp.presentation.generelist.GenereShowsListViewModel
import com.example.omapp.presentation.model.MapperUI
import com.example.omapp.presentation.model.MapperUI.Companion.UI_MAPPER_NAME
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val genereShowsModule = module {
    viewModel {
        GenereShowsListViewModel(
            getGenereShowsUseCase = get(),
            mapper = get(named(UI_MAPPER_NAME))
        )
    }
}