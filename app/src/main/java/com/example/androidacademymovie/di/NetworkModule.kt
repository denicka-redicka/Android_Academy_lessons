package com.example.androidacademymovie.di

import com.example.androidacademymovie.data.remote.MovieApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class NetworkModule {

    private val baseUrl = "https://api.themoviedb.org/"
    private val ver = "3/"

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    private val contentType = "application/json".toMediaType()
    private val loginInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(loginInterceptor)
        .addInterceptor(ApiKeyInterceptor())
        .build()

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(baseUrl + ver)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(httpClient)

    private val retrofit = retrofitBuilder.build()

    val api: MovieApiService by lazy { retrofit.create(MovieApiService::class.java) }

    class ApiKeyInterceptor: Interceptor {

    companion object {
        private const val API_KEY = "31c05ab5cb02de04c5d79593cc2d5291"
    }

        override fun intercept(chain: Interceptor.Chain): Response {
            val origin = chain.request()
            val urlBuilder = origin.url.newBuilder()
            val url = urlBuilder
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request = origin.newBuilder().url(url).build()
            return chain.proceed(request)
        }
    }
}