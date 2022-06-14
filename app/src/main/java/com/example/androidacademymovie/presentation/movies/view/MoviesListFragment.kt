package com.example.androidacademymovie.presentation.movies.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademymovie.R
import com.example.androidacademymovie.di.MovieRepositoryProvider
import com.example.androidacademymovie.extention.exhaustive
import com.example.androidacademymovie.presentation.movies.viewmodel.MovieListViewModelFactory
import com.example.androidacademymovie.presentation.movies.viewmodel.MoviesListViewModelImpl
import com.example.androidacademymovie.presentation.movies.viewmodel.MoviesListViewState.*


class MoviesListFragment : Fragment() {
    private var onClickMovieItemListener : OnClickMovieItemListener? = null
    private val viewModel: MoviesListViewModelImpl by viewModels {
        MovieListViewModelFactory((requireActivity() as MovieRepositoryProvider).provideMovieRepository())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.movies_list_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = view.findViewById<RecyclerView>(R.id.movie_recycler_view)
        val adapter = MovieListAdapter{ movie ->
            onClickMovieItemListener?.onClickMovieItemClicked(movie)
        }
        list.adapter = adapter
        list.layoutManager = GridLayoutManager(this.context, 2)
        loadDataToAdapter(adapter)
    }

    private fun loadDataToAdapter(adapter: MovieListAdapter) {
        viewModel.moviesStateOutput.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MoviesLoaded -> adapter.submitList(state.movies)
                is FailedToLoad -> Toast.makeText(
                    requireContext(),
                    R.string.network_error_msg,
                    Toast.LENGTH_SHORT
                ).show()
            }.exhaustive
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnClickMovieItemListener) {
            onClickMovieItemListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()

        onClickMovieItemListener = null
    }

    interface OnClickMovieItemListener {
        fun onClickMovieItemClicked(movieId: Int)
    }
}