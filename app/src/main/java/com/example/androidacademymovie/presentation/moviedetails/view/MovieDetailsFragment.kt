package com.example.androidacademymovie.presentation.moviedetails.view

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.androidacademymovie.R
import com.example.androidacademymovie.di.MovieRepositoryProvider
import com.example.androidacademymovie.extention.exhaustive
import com.example.androidacademymovie.model.MovieDetails
import com.example.androidacademymovie.presentation.moviedetails.viewmodel.MovieDetailsViewModelFactory
import com.example.androidacademymovie.presentation.moviedetails.viewmodel.MovieDetailsViewModelImpl
import com.example.androidacademymovie.presentation.moviedetails.viewmodel.MovieDetailsViewModelState

class MovieDetailsFragment : Fragment() {
    private var onBackButtonClickListener : OnBackButtonClickListener? = null
    private val viewModel: MovieDetailsViewModelImpl by viewModels {
        MovieDetailsViewModelFactory((requireActivity() as MovieRepositoryProvider).provideMovieRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = arguments?.getSerializable(PARAM_MOVIE_ID) as? Int ?: return

        view.findViewById<View>(R.id.back_button_layout)?.setOnClickListener {
            onBackButtonClickListener?.onBackButtonClicked()
        }

        view.findViewById<RecyclerView>(R.id.actors_recycler).apply {
            this.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            this.adapter = ActorsListAdapter()
        }

        viewModel.loadDetails(movieId)

        viewModel.stateOutput.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieDetailsViewModelState.MovieLoaded -> bindUi(view, state.movie)
                is MovieDetailsViewModelState.FailedToLoad -> Toast.makeText(
                    requireContext(),
                    R.string.network_error_msg,
                    Toast.LENGTH_SHORT
                ).show()
                is MovieDetailsViewModelState.NoMovie -> showNoMovieError()
            }.exhaustive
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBackButtonClickListener) {
            onBackButtonClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        onBackButtonClickListener = null
    }

    private fun bindUi(view: View, movie: MovieDetails) {
        updateMovieDetailsInfo(movie)

        val adapter = view.findViewById<RecyclerView>(R.id.actors_recycler).adapter as ActorsListAdapter
        adapter.submitList(movie.actors)

    }

    private fun updateMovieDetailsInfo(movie: MovieDetails) {
        view?.findViewById<ImageView>(R.id.movie_logo_image)?.load(movie.detailImageUrl)

        view?.findViewById<TextView>(R.id.movie_name_text)?.text = movie.title
        view?.findViewById<TextView>(R.id.age_target)?.text = "${movie.pgAge}+"
        view?.findViewById<TextView>(R.id.tag_text)?.text = movie.genres.joinToString { it.name }
        view?.findViewById<TextView>(R.id.count_reviews)?.text = "${movie.reviewCount} REVIEWS"
        view?.findViewById<TextView>(R.id.movie_description)?.text = movie.storyLine

        val starsImages = listOf<ImageView?>(
            view?.findViewById(R.id.star_1_image),
            view?.findViewById(R.id.star_2_image),
            view?.findViewById(R.id.star_3_image),
            view?.findViewById(R.id.star_4_image),
            view?.findViewById(R.id.star_5_image)
        )
        starsImages.forEachIndexed {index, imageView ->
            imageView?.let {
                val colorId = if (movie.rating > index) R.color.tag_color else R.color.gray_dark
                ImageViewCompat.setImageTintList(
                    imageView, ColorStateList.valueOf(
                        ContextCompat.getColor(imageView.context, colorId)
                    )
                )
            }
        }

    }

    private fun showNoMovieError(){
        Toast.makeText(
            requireContext(),
            R.string.cant_found_movie_error_msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    interface OnBackButtonClickListener {
        fun onBackButtonClicked()
    }

    companion object {
        private const val PARAM_MOVIE_ID = "movie_id"

        fun create(movieId: Int) = MovieDetailsFragment().also {
            val args = bundleOf(
                PARAM_MOVIE_ID to movieId
            )
            it.arguments = args
        }
    }
}