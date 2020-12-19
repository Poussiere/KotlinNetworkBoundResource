package com.poussiere.kotlinnetworkboundresource.services.remote

import com.poussiere.kotlinnetworkboundresource.model.remote.TodayWeatherDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getTodayWeather(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): TodayWeatherDataResponse
}