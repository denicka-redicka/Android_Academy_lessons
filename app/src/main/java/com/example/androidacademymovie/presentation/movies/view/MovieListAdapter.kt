package com.example.androidacademymovie.presentation.movies.view

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.androidacademymovie.R
import com.example.androidacademymovie.model.Movie

class MovieListAdapter(private val onClickMovie: (movieId: Int) -> Unit) :
    ListAdapter<Movie, MovieListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item_holder, parent, false)
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClickMovie)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val movieImg: ImageView = view.findViewById(R.id.movie_image)
        private val movieName: TextView = view.findViewById(R.id.movie_name_text)
        private val like: ImageView = view.findViewById(R.id.like_icon)
        private val pgAge: TextView = view.findViewById(R.id.pg_text)
        private val genreText: TextView = view.findViewById(R.id.tag_text)
        private val runningTime: TextView = view.findViewById(R.id.film_duration)
        private val reviewText: TextView = view.findViewById(R.id.count_reviews)
        private val reviewStart: List<ImageView> = listOf(
            view.findViewById(R.id.star1_image),
            view.findViewById(R.id.star2_image),
            view.findViewById(R.id.star3_image),
            view.findViewById(R.id.star4_image),
            view.findViewById(R.id.star5_image)
        )


        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie, onClickMovie: (movieId: Int) -> Unit) {
            movieName.text = movie.name
            movieImg.load(movie.imageUrl) {
                crossfade(true)
            }
            pgAge.text = "${movie.pgAge}+"
            runningTime.text = "${movie.runningTime} minutes"
            reviewText.text = "${movie.countReviews} reviews"
            genreText.text = movie.genre.joinToString { it.name }
            val likeColor = if (movie.isLiked) R.color.tag_color else R.color.white

            ImageViewCompat.setImageTintList(
                like, ColorStateList.valueOf(ContextCompat.getColor(like.context, likeColor))
            )

            reviewStart.forEachIndexed { index, imageView ->
                val color = if (index < movie.rating) R.color.tag_color else R.color.review_gray
                ImageViewCompat.setImageTintList(
                    imageView, ColorStateList.valueOf(ContextCompat.getColor(imageView.context, color))
                )
            }

            itemView.setOnClickListener {
                onClickMovie(movie.id)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}