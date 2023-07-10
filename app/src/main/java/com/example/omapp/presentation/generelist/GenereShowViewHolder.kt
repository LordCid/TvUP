package com.example.omapp.presentation.generelist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.omapp.R
import com.example.omapp.common.presentation.ImagesLoader
import com.example.omapp.databinding.ItemRailBinding
import com.example.omapp.domain.model.GenereShows
import com.example.omapp.presentation.animation.SlideUpItemAnimator

class GenereShowViewHolder(
    private val binding: ItemRailBinding,
    private val onClick: (String) -> Unit,
    private val imagesLoader: ImagesLoader
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var showsAdapter: ShowAdapter
    fun bind(genereShows: GenereShows) {
        showsAdapter = ShowAdapter(onClick, imagesLoader)
        with(binding){
            title.text = genereShows.id
            horizontalGridView.apply {
                setRowHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                adapter = showsAdapter
                itemAnimator = SlideUpItemAnimator()
                binding.horizontalGridView.setItemSpacing(
                    binding.root.resources.getDimensionPixelSize(R.dimen.lb_action_icon_margin)
                )
            }
        }
        showsAdapter.submitList(genereShows.shows)
    }

}