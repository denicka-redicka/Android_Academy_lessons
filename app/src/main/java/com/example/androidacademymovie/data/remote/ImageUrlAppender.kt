package com.example.androidacademymovie.data.remote

import com.example.androidacademymovie.data.remote.response.ImageResponse
import kotlin.properties.Delegates


private const val DEFAULT_SIZE = "original"
private const val IDENTIFIER_SMALL_SIZE = "w185"
private const val IDENTIFIER_MEDIUM_SIZE = "w500"
private const val IDENTIFIER_LARGE_SIZE = "w780"

class ImageUrlAppender(private val api: MovieApiService) {

    private var imageResponse: ImageResponse? = null
    private var baseUrl: String? = null
    private var posterSize: String by Delegates.notNull()
    private var backdropSize: String by Delegates.notNull()
    private var profileSize: String by Delegates.notNull()

    suspend fun getDetailImageUrl(backdropPath: String?): String? {
        loadConfiguration()

        return buildUrl(baseUrl, backdropSize, backdropPath)
    }

    suspend fun getMoviePosterImageUrl(posterPath: String?): String? {
        loadConfiguration()

        return buildUrl(baseUrl, posterSize, posterPath)
    }

    suspend fun getActorImageUrl(profilePath: String?): String? {
        loadConfiguration()

        return buildUrl(baseUrl, profileSize, profilePath)
    }

    private suspend fun loadConfiguration() {
        if (imageResponse != null) return

        imageResponse = api.loadConfiguration().images
        baseUrl = imageResponse?.secureBaseUrl
        posterSize = imageResponse?.posterSizes?.find { size -> size == IDENTIFIER_LARGE_SIZE } ?: IDENTIFIER_LARGE_SIZE
        backdropSize = imageResponse?.posterSizes?.find { size -> size == IDENTIFIER_MEDIUM_SIZE } ?: IDENTIFIER_MEDIUM_SIZE
        profileSize = imageResponse?.posterSizes?.find { size -> size == IDENTIFIER_SMALL_SIZE } ?: IDENTIFIER_SMALL_SIZE
    }

    private fun buildUrl(url: String?, size: String, path: String?): String? {
        return if(url == null || path == null) {
            null
        } else {
            "$url$size$path"
        }
    }
}