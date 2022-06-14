package com.example.androidacademymovie.presentation.movies.viewmodel

import com.example.androidacademymovie.model.Movie

internal sealed class MoviesListViewState {

    data class MoviesLoaded(val movies: List<Movie>): MoviesListViewState()

    data class FailedToLoad(val exception: Throwable): MoviesListViewState()

}