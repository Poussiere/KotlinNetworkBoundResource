package com.poussiere.kotlinnetworkboundresource

import android.app.Application
import com.facebook.stetho.Stetho
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import timber.log.Timber
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KNBRApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: KNBRApplication? = null
    }

    @FlowPreview
    @InternalCoroutinesApi
    override fun onCreate() {
        super.onCreate()

        //Initialize Timber if in debug mode
        Timber.plant(Timber.DebugTree())
        // Start Koin for dependency injection

        //Initialize Stetho for debuging if in debug mode
        Stetho.initializeWithDefaults(this)

        startKoin {
            // Use Koin Android Logger
            androidLogger()
            // declare Android context
            androidContext(this@KNBRApplication)
            // declare modules to use
           //modules(applicationModule)
            koin.loadModules(listOf(applicationModule))
            koin.createRootScope()
        }
    }
}