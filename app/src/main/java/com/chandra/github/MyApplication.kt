package com.chandra.github

import android.app.Application
import com.chandra.core.di.databaseModule
import com.chandra.core.di.networkModule
import com.chandra.core.di.repositoryModule
import com.chandra.github.di.useCaseModule
import com.chandra.github.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    databaseModule

                )
            )
        }
    }
}