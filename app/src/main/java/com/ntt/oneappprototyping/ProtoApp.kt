package com.ntt.oneappprototyping

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ProtoApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        startKoin {
            // Android context
            androidContext(this@ProtoApp)
            // modules
            modules(koinModule)
        }
    }
}