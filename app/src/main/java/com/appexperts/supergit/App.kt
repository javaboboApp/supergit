package com.appexperts.supergit

import android.app.Application
import com.appexperts.supergit.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
private val appComponent = mutableListOf(databaseModule, networkModule,repositoriesModule, firebaseModule,
  viewModelModule)

    override fun onCreate() {
        super.onCreate()

        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(applicationContext)
            // declare modules
            modules(appComponent)
        }
    }

}