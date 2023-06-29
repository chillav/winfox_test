package com.example.winfox_test.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.winfox_test.R
import com.example.winfox_test.databinding.ItemMovieBinding

class MoviesAdapter(
    private val onClickAction: (Movie) -> Unit
) : ListAdapter<Movie, MoviesAdapter.MovieViewHolder>(MoviesDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view, onClickAction)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieViewHolder(
        view: View,
        onClickAction: (Movie) -> Unit,
    ) : RecyclerView.ViewHolder(view) {
        private val binding by viewBinding<ItemMovieBinding>()
        private var item: Movie? = null

        init {
            binding.root.setOnClickListener { item?.let(onClickAction) }
        }

        fun bind(movie: Movie) {
            item = movie

            binding.title.text = movie.title
            binding.image.load(movie.imageUrl) {
                crossfade(300)
                transformations(RoundedCornersTransformation(20f))
            }
        }
    }

    object MoviesDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }
}
