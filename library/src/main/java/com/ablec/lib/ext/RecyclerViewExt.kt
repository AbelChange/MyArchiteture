package com.ablec.lib.ext

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller


/**
* 滚动后 item居中
 */
fun RecyclerView.smoothScrollToPositionCentered(position: Int) {
    val lm = layoutManager
    if (lm is LinearLayoutManager) {
        lm.startSmoothScroll(CenterSmoothScroller(context).apply {
            targetPosition = position
        })
    }
}

class CenterSmoothScroller(context: Context) : LinearSmoothScroller(context) {
    override fun calculateDtToFit(
        viewStart: Int,
        viewEnd: Int,
        boxStart: Int,
        boxEnd: Int,
        snapPreference: Int
    ): Int {
        return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2)
    }

}