package com.poussiere.kotlinnetworkboundresource.services.remote


import com.google.gson.GsonBuilder
import com.poussiere.kotlinnetworkboundresource.BuildConfig
import com.poussiere.kotlinnetworkboundresource.model.remote.TodayWeatherDataResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import quantum.airbus.com.mobile.disruption.services.createHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    private val openWeatherApiService: OpenWeatherApiService

    init {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.OPEN_WEATHER_BASE_URL)
            .client(createHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        openWeatherApiService = retrofit.create(OpenWeatherApiService::class.java)
    }

    @ExperimentalCoroutinesApi
    fun getWeatherForToday(city: String): Flow<FetchResult<TodayWeatherDataResponse>> {
        return flow {
            emit(
                FetchSuccess(
                        openWeatherApiService . getTodayWeather (
                        city = city,
                units = BuildConfig.OPEN_WEATHER_API_UNITS,
                apiKey = BuildConfig.OPEN_WEATHER_API_KEY
            )))
        }
    }
}