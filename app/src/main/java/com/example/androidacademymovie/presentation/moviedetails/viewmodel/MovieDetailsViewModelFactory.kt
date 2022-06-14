package com.example.androidacademymovie.presentation.moviedetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidacademymovie.domain.MovieRepository

internal class MovieDetailsViewModelFactory(private val repository: MovieRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = MovieDetailsViewModelImpl(repository) as T
}