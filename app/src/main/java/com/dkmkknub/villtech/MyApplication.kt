package com.dkmkknub.villtech

import android.app.Application
import com.dkmkknub.villtech.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    AppModule.appModule,
                    AppModule.viewModelModule,
                    AppModule.databaseModule
                )
            )
        }
    }
}