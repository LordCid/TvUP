package com.example.omapp.presentation.generelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.omapp.common.presentation.ImagesLoader
import com.example.omapp.databinding.ShowCardViewBinding
import com.example.omapp.domain.model.Show

class ShowAdapter(
private val onClick: (String) -> Unit,
private val imagesLoader: ImagesLoader
): ListAdapter<Show, ShowAdapter.ShowViewHolder>(TaskDiffCallBack()) {

    class TaskDiffCallBack : DiffUtil.ItemCallback<Show>() {

        override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ShowViewHolder(
            ShowCardViewBinding.inflate(inflater, parent, false),
            onClick,
            imagesLoader
        )
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    class ShowViewHolder(
        private val binding: ShowCardViewBinding,
        private val onClick: (String) -> Unit,
        private val imagesLoader: ImagesLoader
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(show: Show) {
            with(binding) {
                root.setOnClickListener { onClick(show.id) }
                imagesLoader.loadImage(show.movieImages.poster, mainImage)
            }
        }
    }



}