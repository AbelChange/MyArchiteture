package com.ablec.lib.ext

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlin.math.ceil


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


/**
 * @description 水平分割线 LinearLayoutManager only
 * @param color 如果只要间距效果，可以设置透明
 * @param insetStart 分割线start距离
 * @param insetEnd 分割线end距离
 * @param thickness 分割线厚度
 * @param spacing item space
 */
fun RecyclerView.addDividerForLinear(
    insetStart: Int = 0,
    insetEnd: Int = 0,
    spacing: Int = 0,
    @ColorInt color: Int = -1,
    @Px thickNess: Int = 1,
) {
    require(layoutManager is LinearLayoutManager) { "LinearLayoutManager Only" }
    addItemDecorationIfAbsent(object : MaterialDividerItemDecoration(context, VERTICAL) {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            if (orientation == VERTICAL) {
                outRect.top = spacing
                outRect.bottom = spacing
            } else {
                outRect.left = spacing
                outRect.right = spacing
            }
        }
    }.apply {
        if (color >= 0) {
            dividerColor = color
        }
        dividerThickness = thickNess
        dividerInsetStart = insetStart
        dividerInsetEnd = insetEnd
    })
}


/**
 * 兼容Linear & Grid
 * @param bottomSpace 末行space
 */
fun RecyclerView.addBottomSpaceDecorationForLastLine(bottomSpace: Int) {
    addItemDecorationIfAbsent(object : ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            when (val layoutManager = parent.layoutManager) {
                is GridLayoutManager -> {
                    val itemPosition = parent.getChildAdapterPosition(view)
                    if (isLastRowInGridLayoutManager(itemPosition, parent, layoutManager)) {
                        outRect.bottom = bottomSpace
                    }
                }

                is LinearLayoutManager -> {
                    val position = parent.getChildAdapterPosition(view)
                    val totalItemCount = layoutManager.itemCount
                    val isLastItem = position == totalItemCount - 1
                    if (isLastItem) {
                        outRect.bottom = bottomSpace
                    }
                }
            }
        }
    })
}

/**
 * 判断当前项是否在最后一行
 */
fun RecyclerView.isLastRowInGridLayoutManager(
    itemPosition: Int,
    parent: RecyclerView,
    layoutManager: GridLayoutManager
): Boolean {
    val childCount = parent.adapter?.itemCount ?: 0
    val spanCount = layoutManager.spanCount
    if (spanCount == 0) {
        return false
    }
    // 计算总行数，考虑不完全的最后一行
    val rowCount = ceil(childCount.toDouble() / spanCount).toInt()
    // 计算最后一行的起始位置
    val lastRowStartPos = (rowCount - 1) * spanCount
    // 计算最后一行的元素数
    val lastRowItemCount = if (childCount % spanCount == 0) spanCount else childCount % spanCount
    // 判断当前项是否在最后一行
    return itemPosition >= lastRowStartPos && itemPosition < lastRowStartPos + lastRowItemCount
}

/**
 * Add an [ItemDecoration] to this RecyclerView.
 * @param itemDecoration The [ItemDecoration] to add.
 */
fun RecyclerView.addItemDecorationIfAbsent(itemDecoration: ItemDecoration) {
    for (i in 0 until itemDecorationCount) {
        if (itemDecoration == getItemDecorationAt(i)) {
            return
        }
    }
    addItemDecoration(itemDecoration)
}
