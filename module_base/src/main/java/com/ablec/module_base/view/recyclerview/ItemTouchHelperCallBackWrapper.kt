package com.ablec.module_base.view.recyclerview

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallBackWrapper(private val listener: OnItemTouchListener) : ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled(): Boolean {
        return false //use startDrag()
        //        return super.isLongPressDragEnabled();
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            //dragStart
            listener.onDragging(viewHolder)
        } else if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //swipeStart
            listener.onSwiping(viewHolder)
        }
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        //上下drag，左swipe
        return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        //是否可移动
        return true
    }

    override fun onMoved(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        fromPos: Int,
        target: RecyclerView.ViewHolder,
        toPos: Int,
        x: Int,
        y: Int
    ) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
        listener.onMoved(viewHolder, target)

    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onSwiped(viewHolder)
    }


    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        listener.onClear(recyclerView, viewHolder)
    }

    interface OnItemTouchListener {

        fun onDragging(viewHolder: RecyclerView.ViewHolder?)

        fun onMoved(viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder)

        fun onSwiping(viewHolder: RecyclerView.ViewHolder?)

        fun onSwiped(viewHolder: RecyclerView.ViewHolder?)

        fun onClear(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder)

    }


}
