package com.example.omapp.presentation.generelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.omapp.common.presentation.ImagesLoader
import com.example.omapp.databinding.ItemGenereShowsBinding
import com.example.omapp.domain.model.GenereShows
import com.example.omapp.domain.model.Show

class GenereShowsAdapter(
    private val onClick: (String) -> Unit,
    private val imagesLoader: ImagesLoader
): ListAdapter<GenereShows, GenereShowsAdapter.GenereShowViewHolder>(TaskDiffCallBack()) {

    class TaskDiffCallBack : DiffUtil.ItemCallback<GenereShows>() {

        override fun areItemsTheSame(oldItem: GenereShows, newItem: GenereShows): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GenereShows, newItem: GenereShows): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenereShowViewHolder {
        val bindingView = ItemGenereShowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenereShowViewHolder(bindingView, onClick, imagesLoader)
    }

    override fun onBindViewHolder(holder: GenereShowViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }



    class GenereShowViewHolder(
        private val binding: ItemGenereShowsBinding,
        private val onClick: (String) -> Unit,
        private val imagesLoader: ImagesLoader
        ) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(show: GenereShows) {
            with(binding){
//                root.setOnClickListener { onClick(show.id) }
//                imagesLoader.loadImage(show.movieImages.photo, movieContainer)
//                titleTv.text = show.title
//                 actorsTv.text = show.actors.joinToString(",")
            }
        }

    }


}