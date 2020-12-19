package com.poussiere.kotlinnetworkboundresource.services.remote

/**
 * A class that encapsulate results of repositories
 */

sealed class FetchResult<out T>

//Loading class  can contain data if offline data exist in database
data class FetchLoading<out T>(val data: T?): FetchResult<T>()

//Data fetched from database when loading is over
data class FetchSuccess<out T>(val data: T): FetchResult<T>()

//Error
data class FetchError(val exception: Throwable): FetchResult<Nothing>()
