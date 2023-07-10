package com.example.omapp


import android.app.Application
import android.content.Context
import com.example.omapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeDependencyInjection(this)
    }

    private fun initializeDependencyInjection(context: Context) {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(context)
            modules(
                appModule,
                mapperModule,
                dataModule,
                domainModule,
                movieListModule,
                movieDetailModule,
                genereShowsModule
            )
        }
    }
}
