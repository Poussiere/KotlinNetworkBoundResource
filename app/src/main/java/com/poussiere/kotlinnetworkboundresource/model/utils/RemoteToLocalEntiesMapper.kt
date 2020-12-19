package com.poussiere.kotlinnetworkboundresource.model

import com.poussiere.kotlinnetworkboundresource.model.local.WeatherForADay
import com.poussiere.kotlinnetworkboundresource.model.remote.TodayWeatherDataResponse
import org.joda.time.LocalDateTime

/**
 * Convert an API entity to a local database entity
 * As we are not interested into all remote information
 * we store all needed fetched information into only one local entity
 * We add the current date to the local entity, to check whether data are outdated
 */
fun TodayWeatherDataResponse.toLocalEntity(city: String): WeatherForADay =
    WeatherForADay(
        date = LocalDateTime.now(),
        city = city,
        temp = this.mainInformation.temp,
        feelsLike = this.mainInformation.feelsLike,
        tempMin = this.mainInformation.feelsLike,
        tempMax = this.mainInformation.feelsLike,
        pressure = this.mainInformation.pressure,
        humidity = this.mainInformation.humidity,
        windSpeed = this.wind.speed
    )

