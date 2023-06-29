package com.example.winfox_test.movies

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.winfox_test.MainActivity
import com.example.winfox_test.R
import com.example.winfox_test.databinding.FragmentMoviesBinding
import com.example.winfox_test.movie_details.MovieDetailsFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment(R.layout.fragment_movies) {
    private val viewModel: MoviesViewModel by viewModel()
    private val binding by viewBinding<FragmentMoviesBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        val adapter = MoviesAdapter { movie ->
            (activity as? MainActivity)?.replaceFragment(MovieDetailsFragment.newInstance(movie))
        }

        binding.recyclerMovies.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movies.collect(adapter::submitList)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collect { isVisible ->
                binding.progressBar.isVisible = isVisible
            }
        }
    }
}
