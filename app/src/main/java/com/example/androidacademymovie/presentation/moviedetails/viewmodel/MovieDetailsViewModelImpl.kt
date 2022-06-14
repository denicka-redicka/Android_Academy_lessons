package com.example.androidacademymovie.presentation.moviedetails.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidacademymovie.common.model.Failure
import com.example.androidacademymovie.common.model.Result
import com.example.androidacademymovie.common.model.Success
import com.example.androidacademymovie.domain.MovieRepository
import com.example.androidacademymovie.extention.exhaustive
import com.example.androidacademymovie.model.MovieDetails
import kotlinx.coroutines.launch

internal class MovieDetailsViewModelImpl(private val repository: MovieRepository): MovieDetailsViewModel() {

    override val stateOutput = MutableLiveData<MovieDetailsViewModelState>()

    fun loadDetails(movieId: Int) {
        viewModelScope.launch {
            handleResult(repository.loadMovie(movieId))
        }
    }


    private fun handleResult(result: Result<MovieDetails?>) {
        when (result) {
            is Success -> handleMovieLoadResult(result.data)
            is Failure -> stateOutput.postValue(MovieDetailsViewModelState.FailedToLoad(result.exception))
        }.exhaustive
    }

    private fun handleMovieLoadResult(movie: MovieDetails?) {
        if (movie != null) {
            stateOutput.postValue(MovieDetailsViewModelState.MovieLoaded(movie))
        } else {
            stateOutput.postValue(MovieDetailsViewModelState.NoMovie)
        }
    }
}