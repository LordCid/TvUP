package com.example.omapp.di

import com.example.omapp.common.presentation.GlideImplementation
import com.example.omapp.common.presentation.ImagesLoader
import com.example.omapp.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val appModule = module {
    single { ApiService.create() }
    single { Dispatchers.IO }
    single<ImagesLoader> { GlideImplementation() }

}