package com.yunfeng.module_base.view

import android.view.View
import android.widget.ImageView
import com.bigkoo.convenientbanner.holder.Holder
import com.yunfeng.lib.glide.GlideUtils
import com.yunfeng.module_base.R

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/5/21 13:14
 */
class ImageViewHolder<T : Bannered?>(
    private val holder: Int = 0,
    private val radius: Int = 0,
    itemView: View?,
) : Holder<T>(itemView) {

    private lateinit var iv: ImageView

    override fun initView(itemView: View) {
        iv = itemView.findViewById(R.id.iv_banner)
    }

    override fun updateUI(data: T?) {
        try {
            data?.let {
                if (data.getImageUrl() != null) {
                    GlideUtils.loadCornersImage(
                        itemView.context,
                        data.getImageUrl()!!,
                        iv,
                        radius,
                        holder,
                        holder
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}