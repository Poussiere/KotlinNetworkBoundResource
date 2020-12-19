package com.poussiere.kotlinnetworkboundresource.services.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.poussiere.kotlinnetworkboundresource.model.utils.DateConverter
import com.poussiere.kotlinnetworkboundresource.model.local.WeatherForADay

/**
 * Application Database with version,
 * list of entities and list of DAO
 */
@Database(entities = [WeatherForADay::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun localDatasource(): LocalDataSource
}