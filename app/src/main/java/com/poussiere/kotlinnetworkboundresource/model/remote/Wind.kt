package com.poussiere.kotlinnetworkboundresource.model.remote

import com.google.gson.annotations.SerializedName

data class Wind (
    @SerializedName("speed")
    val speed : Float,
    @SerializedName("deg")
    val direction : Int
    )
