package com.ablec.myarchitecture.logic.pageslist

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.animation.AlphaInAnimation
import com.chad.library.adapter.base.animation.BaseAnimation
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.data.server.dto.ListItem

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/4/28 16:13
 */
class ListAdapter :BaseQuickAdapter<ListItem,BaseViewHolder>(R.layout.simple_textview),
    LoadMoreModule {

    private var position: Int = -1

    override fun convert(holder: BaseViewHolder, item: ListItem) {
        holder.setText(R.id.tvResp, item.title)
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        val type = holder.itemViewType
        if (isFixedViewType(type)) {
            setFullSpan(holder)
        } else {
            addAnimation(holder)
        }
    }

    override fun setNewInstance(list: MutableList<ListItem>?) {
        super.setNewInstance(list)
        position = -1
    }

    override fun setList(list: Collection<ListItem>?) {
        super.setList(list)
        position = -1
    }

    private fun addAnimation(holder: RecyclerView.ViewHolder) {
        if (animationEnable) {
            if (!isAnimationFirstOnly || holder.layoutPosition > position) {
                if (holder.adapterPosition >= 2) {
                    val animation: BaseAnimation = adapterAnimation ?: AlphaInAnimation()
                    animation.animators(holder.itemView).forEach {
                        startAnim(it, holder.layoutPosition)
                    }
                    position = holder.layoutPosition
                } else {
                    val animation: BaseAnimation = AlphaInAnimation()
                    animation.animators(holder.itemView).forEach {
                        startAnim(it, holder.layoutPosition)
                    }
                    position = holder.layoutPosition
                }
            }
        }
    }
}