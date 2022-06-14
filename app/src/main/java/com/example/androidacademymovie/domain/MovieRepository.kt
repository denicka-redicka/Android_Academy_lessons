package com.example.androidacademymovie.domain

import com.example.androidacademymovie.common.model.Result
import com.example.androidacademymovie.model.Movie
import com.example.androidacademymovie.model.MovieDetails

internal interface MovieRepository {

    suspend fun loadMovies(): Result<List<Movie>>
    suspend fun loadMovie(movieId: Int): Result<MovieDetails>

}