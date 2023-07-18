package com.example.omapp.presentation.generelist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.omapp.R
import com.example.omapp.common.presentation.HorizontalDividerItemDecoration
import com.example.omapp.common.presentation.ImagesLoader
import com.example.omapp.databinding.ItemRailBinding
import com.example.omapp.domain.model.GenereShows
import com.example.omapp.presentation.animation.SlideUpItemAnimator
import com.example.omapp.presentation.model.GenereShowsUI

class GenereShowViewHolder(
    private val binding: ItemRailBinding,
    private val onClick: (String) -> Unit,
    private val imagesLoader: ImagesLoader
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var showsAdapter: ShowAdapter
    fun bind(genereShows: GenereShowsUI) {
        showsAdapter = ShowAdapter(onClick, imagesLoader)
        with(binding){
            title.text = genereShows.title
            val divider = HorizontalDividerItemDecoration(
                root.resources.getDimension(R.dimen.spacing_sl).toInt(),
                0,
                0
            )
            horizontalListView.apply {
                this.adapter = showsAdapter
                addItemDecoration(divider)
            }
        }
        showsAdapter.submitList(genereShows.shows)
    }

}