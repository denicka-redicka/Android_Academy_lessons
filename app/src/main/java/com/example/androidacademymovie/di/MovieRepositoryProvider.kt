package com.example.androidacademymovie.di

import com.example.androidacademymovie.domain.MovieRepository

internal interface MovieRepositoryProvider {
    fun provideMovieRepository(): MovieRepository
}