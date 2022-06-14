package com.example.androidacademymovie.data

import com.example.androidacademymovie.common.model.Result
import com.example.androidacademymovie.common.model.runCatchingResult
import com.example.androidacademymovie.data.remote.RemoteDataSource
import com.example.androidacademymovie.domain.MovieRepository
import com.example.androidacademymovie.model.Movie
import com.example.androidacademymovie.model.MovieDetails

internal class MovieRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): MovieRepository {
    override suspend fun loadMovies(): Result<List<Movie>> {
        return runCatchingResult {remoteDataSource.loadMovies() }
    }

    override suspend fun loadMovie(movieId: Int): Result<MovieDetails> {
        return runCatchingResult { remoteDataSource.loadMovie(movieId) }
    }
}