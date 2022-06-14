package com.example.androidacademymovie.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidacademymovie.R
import com.example.androidacademymovie.data.MovieRepositoryImpl
import com.example.androidacademymovie.presentation.moviedetails.view.MovieDetailsFragment
import com.example.androidacademymovie.presentation.movies.view.MoviesListFragment
import com.example.androidacademymovie.data.remote.ImageUrlAppender
import com.example.androidacademymovie.di.NetworkModule
import com.example.androidacademymovie.data.remote.RetrofitDataSource
import com.example.androidacademymovie.domain.MovieRepository
import com.example.androidacademymovie.di.MovieRepositoryProvider

internal class MainActivity : AppCompatActivity(),
    MoviesListFragment.OnClickMovieItemListener,
    MovieDetailsFragment.OnBackButtonClickListener,
    MovieRepositoryProvider {

    private val networkModule = NetworkModule()
    private val remoteDataSource = RetrofitDataSource(networkModule.api, ImageUrlAppender(networkModule.api))
    private val movieRepository = MovieRepositoryImpl(remoteDataSource)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            openMoviesList()
        }
    }

    private fun openMoviesList() {
        supportFragmentManager.beginTransaction()
            .add(R.id.frame_layouts, MoviesListFragment())
            .commit()
    }

    override fun onClickMovieItemClicked(movieId: Int) {
        supportFragmentManager.beginTransaction()
            .add(R.id.frame_layouts,
                MovieDetailsFragment.create(movieId),
                MovieDetailsFragment::class.java.simpleName)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackButtonClicked() {
        supportFragmentManager.popBackStack()
    }

    override fun provideMovieRepository(): MovieRepository = movieRepository
}