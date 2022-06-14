package com.example.androidacademymovie.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieCastResponse (
    @SerialName("id") val id: Int,
    @SerialName("cast") val casts: List<CastResponse>
        )