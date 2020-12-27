package com.poussiere.kotlinnetworkboundresource.services.local

import androidx.room.*
import com.poussiere.kotlinnetworkboundresource.model.local.WeatherForADay
import kotlinx.coroutines.flow.Flow

@Dao
interface  LocalDataSource {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWheatherForADay(item : WeatherForADay)

    @Delete
    suspend fun deleteWheatherForADay(item : WeatherForADay)

    //Get the last recorded for a city (one shot)
    @Query("SELECT * FROM WeatherForADay WHERE city =:city ")
    suspend fun getWeather(city: String): WeatherForADay?

    //Subscribe to any change on Weather for a day table change
    @Query("SELECT * FROM WeatherForADay WHERE city =:city ")
    fun subscribeToWeather(city: String): Flow<WeatherForADay>

}