package com.example.androidacademymovie.presentation.moviedetails.viewmodel

import com.example.androidacademymovie.model.MovieDetails

internal sealed class MovieDetailsViewModelState {

    data class MovieLoaded(val movie: MovieDetails): MovieDetailsViewModelState()

    data class FailedToLoad(val exception: Throwable): MovieDetailsViewModelState()

    object NoMovie: MovieDetailsViewModelState()
}