package com.example.omapp.common.presentation

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class HorizontalDividerItemDecoration(
    private val startOffset: Int,
    private val spacing: Int,
    private val bottomSpacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        if (itemPosition == 0) outRect.left = startOffset
        outRect.right = spacing
        outRect.bottom = bottomSpacing
    }
}
