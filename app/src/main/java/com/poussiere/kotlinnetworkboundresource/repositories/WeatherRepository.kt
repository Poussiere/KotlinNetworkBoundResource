package com.poussiere.kotlinnetworkboundresource.repositories

import com.poussiere.kotlinnetworkboundresource.model.toLocalEntity
import com.poussiere.kotlinnetworkboundresource.model.local.WeatherForADay
import com.poussiere.kotlinnetworkboundresource.services.remote.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest

class WeatherRepository(private val remoteDataSource: RemoteDataSource) {

    /**
     * Get today weather from network
     */
    @ExperimentalCoroutinesApi
    fun getTodayWeather(city: String): Flow<FetchResult<WeatherForADay>> {
        remoteDataSource.getWeatherForToday(city)
        return remoteDataSource.getWeatherForToday(city)
            .catch { error -> emit(FetchError(error))}
            .mapLatest { response ->
                when (response) {
                    is FetchSuccess -> FetchSuccess(response.data.toLocalEntity(city))
                    is FetchError -> FetchError(response.exception)
                    is FetchLoading -> FetchLoading(response.data?.toLocalEntity(city))
                }
            }
    }
}