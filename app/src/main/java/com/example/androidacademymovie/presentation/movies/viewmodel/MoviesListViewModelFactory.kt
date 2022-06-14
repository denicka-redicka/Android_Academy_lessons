package com.example.androidacademymovie.presentation.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidacademymovie.domain.MovieRepository

@Suppress("UNCHECKED_CAST")
internal class MovieListViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = MoviesListViewModelImpl(repository) as T
}