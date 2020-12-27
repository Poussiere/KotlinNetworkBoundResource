package com.poussiere.kotlinnetworkboundresource

import androidx.room.Room
import com.poussiere.kotlinnetworkboundresource.repositories.WeatherRepository
import com.poussiere.kotlinnetworkboundresource.services.local.AppDatabase
import com.poussiere.kotlinnetworkboundresource.services.remote.RemoteDataSource
import com.poussiere.kotlinnetworkboundresource.viewmodels.TodayWeatherViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


/**
 * Koin modules for Kotlin network bound resource application
 */

@FlowPreview
@OptIn(ExperimentalCoroutinesApi::class)
@InternalCoroutinesApi
val applicationModule: Module = module {

    //Create instance of the database
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "KNBRDatabase")
            .fallbackToDestructiveMigration()
            .build()
    }

    //Inject local data source (room dao)
    single { get<AppDatabase>().localDatasource() }

    //Inject network data source with Retrofit client
    single { RemoteDataSource() }

    //Repository for weather data
    single { WeatherRepository(get(), get()) }

    //ViewModel of the main weather activity
    viewModel { TodayWeatherViewModel(get()) }
}
