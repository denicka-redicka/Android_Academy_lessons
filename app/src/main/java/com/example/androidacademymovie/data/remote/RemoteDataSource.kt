package com.example.androidacademymovie.data.remote

import com.example.androidacademymovie.model.Movie
import com.example.androidacademymovie.model.MovieDetails

interface RemoteDataSource {

    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): MovieDetails
}