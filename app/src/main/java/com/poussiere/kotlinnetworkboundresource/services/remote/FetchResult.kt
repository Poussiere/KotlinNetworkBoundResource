package com.poussiere.kotlinnetworkboundresource.services.remote

/**
 * A class that encapsulate results of repositories
 */

sealed class FetchResult<out T>

data class FetchRefreshing<out T>(val data: T): FetchResult<T>()

//Loading class
object FetchLoading: FetchResult <Nothing>()

//Data fetched from database when loading is over
data class FetchSuccess<out T>(val data: T): FetchResult<T>()

//Error
data class FetchError(val exception: Throwable): FetchResult<Nothing>()
