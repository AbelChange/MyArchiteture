package com.ablec.myarchitecture.logic.pageslist

import android.widget.ImageView
import com.ablec.lib.glide.GlideUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.data.server.dto.ListItem

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/4/28 16:13
 */
class ListAdapter :BaseQuickAdapter<ListItem,BaseViewHolder>(R.layout.item_simple_list),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ListItem) {
        holder.setText(R.id.tvResp, item.title)
        val iv = holder.getView<ImageView>(R.id.imageView)
        GlideUtils.loadImage(context,item.firstImg,iv)
    }
}