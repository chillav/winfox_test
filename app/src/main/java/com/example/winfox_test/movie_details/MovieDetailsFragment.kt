package com.example.winfox_test.movie_details

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.winfox_test.R
import com.example.winfox_test.databinding.FragmentMovieDetailsBinding
import com.example.winfox_test.movies.Movie

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    private val binding by viewBinding<FragmentMovieDetailsBinding>()
    private val movieArg: Movie by lazy {
        val arg = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(MOVIE_ARG, Movie::class.java)
        } else {
            arguments?.getParcelable(MOVIE_ARG)
        }
        arg ?: error("MovieDetailsFragment requires arg $MOVIE_ARG")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            image.load(movieArg.imageUrl) {
                crossfade(300)
                transformations(RoundedCornersTransformation(20f))
            }
            textSummary.text = movieArg.summary
            imageBackButton.setOnClickListener {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }
    }

    companion object {
        private const val MOVIE_ARG = "movie_arg"

        fun newInstance(movie: Movie): MovieDetailsFragment {
            return MovieDetailsFragment().apply {
                arguments = bundleOf(MOVIE_ARG to movie)
            }
        }
    }
}
