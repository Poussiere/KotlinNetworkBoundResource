package com.poussiere.kotlinnetworkboundresource.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.poussiere.kotlinnetworkboundresource.model.utils.DateConverter
import org.joda.time.LocalDateTime

@Entity
data class WeatherForADay (
    @PrimaryKey(autoGenerate = true)
    val uniqueID: Int = 0,
    @TypeConverters(DateConverter::class)
    val city: String,
    val date : LocalDateTime,
    val temp: Float,
    val feelsLike: Float,
    val tempMin: Float,
    val tempMax: Float,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Float
)