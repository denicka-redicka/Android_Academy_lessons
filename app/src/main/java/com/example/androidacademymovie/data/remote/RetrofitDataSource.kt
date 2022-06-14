package com.example.androidacademymovie.data.remote

import com.example.androidacademymovie.model.Actor
import com.example.androidacademymovie.model.Genre
import com.example.androidacademymovie.model.Movie
import com.example.androidacademymovie.model.MovieDetails


private const val ADULT_AGE = 16
private const val CHILD_AGE = 13

class RetrofitDataSource(
    private val api: MovieApiService,
    private val imageUrlAppender: ImageUrlAppender
): RemoteDataSource {
    override suspend fun loadMovies(): List<Movie> {
        val genres = api.loadGenres().genres

        return api.loadUpComing(1).results.map { movie ->
            Movie(
                id = movie.id,
                name = movie.title,
                isLiked = false,
                countReviews = movie.voteCount,
                runningTime = 100,
                imageUrl = imageUrlAppender.getMoviePosterImageUrl(movie.posterPath),
                pgAge = movie.adult.toSpectatorAge(),
                genre = genres
                    .filter { genreResponse ->  movie.genreIds.contains(genreResponse.id)}
                    .map { genre -> Genre(genre.id, genre.name) },
                rating = movie.voteAverage.toInt()
            )
        }
    }

    override suspend fun loadMovie(movieId: Int): MovieDetails {
        val detail = api.loadMovieDetails(movieId)

        return MovieDetails(
            id = detail.id,
            pgAge = detail.adult.toSpectatorAge(),
            title = detail.title,
            genres = detail.genres.map { Genre(it.id, it.name) },
            reviewCount = detail.revenue,
            isLiked = false,
            rating = detail.popularity.toInt(),
            detailImageUrl = imageUrlAppender.getDetailImageUrl(detail.backdropPath),
            storyLine = detail.overview.orEmpty(),
            actors = api.loadMovieCredits(movieId).casts.map { actor ->
                Actor(
                    actor.id,
                    actor.name,
                    imageUrlAppender.getActorImageUrl(actor.profilePath)
                )
            }
        )
    }

    private fun Boolean.toSpectatorAge() = if (this) ADULT_AGE else CHILD_AGE
}