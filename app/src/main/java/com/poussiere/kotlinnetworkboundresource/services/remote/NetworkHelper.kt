package quantum.airbus.com.mobile.disruption.services

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Http client of the application
 */
fun createHttpClient(): OkHttpClient {
    val clientBuilder = OkHttpClient.Builder()

    return clientBuilder
        .addNetworkInterceptor(StethoInterceptor())
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .build()
}
