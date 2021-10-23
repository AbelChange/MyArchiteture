package com.yunfeng.module_base.view

import android.view.View
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.bigkoo.convenientbanner.holder.Holder
import com.yunfeng.module_base.R

/**
 * @Description:
 * @Author: haoshuaihui
 * @CreateDate: 2020/4/13 14:49
 */
class ImageViewHolderCreator<T : Bannered?>(
    private val holder: Int = 0,
    private val radius: Int = 0
) : CBViewHolderCreator {
    override fun createHolder(itemView: View): Holder<T> {
        return ImageViewHolder<T>(holder, radius, itemView)
    }

    override fun getLayoutId(): Int {
        return R.layout.module_base_banner_imagview
    }
}