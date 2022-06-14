package com.example.androidacademymovie.presentation.movies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidacademymovie.common.model.Failure
import com.example.androidacademymovie.common.model.Result
import com.example.androidacademymovie.common.model.Success
import com.example.androidacademymovie.domain.MovieRepository
import com.example.androidacademymovie.extention.exhaustive
import com.example.androidacademymovie.model.Movie
import kotlinx.coroutines.launch

internal class MoviesListViewModelImpl(private val repository: MovieRepository): MoviesViewModel() {

    override val moviesStateOutput = MutableLiveData<MoviesListViewState>()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch{ handleResult(repository.loadMovies()) }
    }

    private fun handleResult(result: Result<List<Movie>>) {
        when (result) {
            is Success -> moviesStateOutput.postValue(MoviesListViewState.MoviesLoaded(result.data))
            is Failure -> moviesStateOutput.postValue(MoviesListViewState.FailedToLoad(result.exception))
        }.exhaustive
    }
}