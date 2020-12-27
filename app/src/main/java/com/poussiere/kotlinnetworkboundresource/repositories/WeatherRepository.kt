package com.poussiere.kotlinnetworkboundresource.repositories

import com.poussiere.kotlinnetworkboundresource.model.toLocalEntity
import com.poussiere.kotlinnetworkboundresource.model.local.WeatherForADay
import com.poussiere.kotlinnetworkboundresource.services.local.LocalDataSource
import com.poussiere.kotlinnetworkboundresource.services.remote.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.joda.time.DateTime

@ExperimentalCoroutinesApi
class WeatherRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    /**
     * Make an api call,
     * update the local database
     * and subscribe to the db for refreshed result
     */
    @FlowPreview
    fun refreshWeather(city: String): Flow<FetchResult<WeatherForADay>> {
        return remoteDataSource.getWeather(city)
            .onEach { networkResponse ->
                localDataSource.insertWheatherForADay(networkResponse.toLocalEntity(city))
            }
            .flatMapLatest { localDataSource.subscribeToWeather(city) }
            .map { todayWeather -> FetchSuccess(todayWeather) }
    }

    /**
     * Return Weather, emitting last cached weather first
     * And emitting an error on exception
     */
    @FlowPreview
    fun getWeatherOrError(city: String): Flow<FetchResult<WeatherForADay>> {
        return refreshWeather(city)
            .onStart { emit(getCachedValue(city)) }
            .catch { error -> emit(FetchError(error)) }
    }

    /**
     * Return the cached value of a weather for a city
     * If it exists into db, the status is set to refreshing
     * If it does not exist into db, the status is set to loading
     */
    private suspend fun getCachedValue(city: String): FetchResult<WeatherForADay> {
        val cachedValue = localDataSource.getWeather(city)
        return if (cachedValue == null) {
            FetchLoading
        } else {
            FetchRefreshing(cachedValue)
        }
    }
}
