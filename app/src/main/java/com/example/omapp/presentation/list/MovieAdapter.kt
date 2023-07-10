package com.example.omapp.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.omapp.R
import com.example.omapp.common.presentation.ImagesLoader
import com.example.omapp.common.formatDuration
import com.example.omapp.databinding.ItemMovieBinding
import com.example.omapp.domain.model.Movie

class MovieAdapter(
    private val imagesLoader: ImagesLoader,
    private val onCLick: (String) -> Unit,
    private val onClickFavorite: (Long, Boolean) -> Unit
) : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(TaskDiffCallBack()) {


    class TaskDiffCallBack : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(
            oldItem: Movie,
            newItem: Movie
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Movie,
            newItem: Movie
        ) = oldItem == newItem
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val bindingView = ItemMovieBinding.inflate(inflater, parent, false)
        return MovieViewHolder(bindingView, imagesLoader, onCLick, onClickFavorite)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

    }

    class MovieViewHolder(
        private val bindingView: ItemMovieBinding,
        private val imagesLoader: ImagesLoader,
        private val onCLick: (String) -> Unit,
        private val onClickFavorite: (Long, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(bindingView.root) {

        fun bind(movie: Movie) {
            bindingView.apply {
                root.setOnClickListener { onCLick(movie.externalId) }
                imagesLoader.loadImage(movie.imagesURL.first(), movieContainer)
                titleTv.text = movie.name
                yearTv.text = movie.year.toString()
                durationTv.text = itemView.context.getString(R.string.duration, movie.duration.formatDuration())
                setFavoriteButton(movie)
            }
        }

        private fun setFavoriteButton(movie: Movie) {
            bindingView.favouriteButton.apply {
                setImageResource(getFavoriteIcon(movie.isFavorite))
                setOnClickListener {
                    onClickFavorite(movie.id, movie.isFavorite.not())
                }
            }
        }

        private fun getFavoriteIcon(isFavorite: Boolean): Int{
            return if(isFavorite) R.drawable.ic_favorite_black else R.drawable.ic_favorite_border_black
        }
    }
}