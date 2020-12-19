package com.poussiere.kotlinnetworkboundresource.model.remote

import com.google.gson.annotations.SerializedName

data class TodayWeatherDataResponse(
    @SerializedName("main")
    val mainInformation: MainInformation,
    @SerializedName("wind")
    val wind: Wind
)
