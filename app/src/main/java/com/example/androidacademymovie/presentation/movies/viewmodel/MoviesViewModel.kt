package com.example.androidacademymovie.presentation.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

internal abstract class MoviesViewModel(): ViewModel() {

    abstract val moviesStateOutput: LiveData<MoviesListViewState>
}