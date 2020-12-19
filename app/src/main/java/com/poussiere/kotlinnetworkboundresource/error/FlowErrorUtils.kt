package com.poussiere.kotlinnetworkboundresource.error

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber

@InternalCoroutinesApi
fun <T> Flow<T>.handleErrors(): Flow<T> = flow {fun <T> Flow<T>.handleErrors(): Flow<T> =
    catch { e -> Timber.e("e.message") }
}