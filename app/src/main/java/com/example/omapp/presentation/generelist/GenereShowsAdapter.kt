package com.example.omapp.presentation.generelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.omapp.common.presentation.ImagesLoader
import com.example.omapp.databinding.ItemGenereShowsBinding
import com.example.omapp.databinding.ItemRailBinding
import com.example.omapp.domain.model.GenereShows
import com.example.omapp.domain.model.Show
import com.example.omapp.presentation.model.GenereShowsUI

class GenereShowsAdapter(
    private val onClick: (String) -> Unit,
    private val imagesLoader: ImagesLoader
): ListAdapter<GenereShowsUI, GenereShowViewHolder>(TaskDiffCallBack()) {

    class TaskDiffCallBack : DiffUtil.ItemCallback<GenereShowsUI>() {

        override fun areItemsTheSame(oldItem: GenereShowsUI, newItem: GenereShowsUI): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: GenereShowsUI, newItem: GenereShowsUI): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenereShowViewHolder {
        val bindingView = ItemRailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenereShowViewHolder(bindingView, onClick, imagesLoader)
    }

    override fun onBindViewHolder(holder: GenereShowViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


}