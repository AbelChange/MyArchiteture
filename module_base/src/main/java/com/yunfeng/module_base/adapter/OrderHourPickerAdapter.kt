package com.yunfeng.module_base.adapter

import android.widget.TextView
import com.blankj.utilcode.util.ColorUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yunfeng.module_base.R
import com.yunfeng.module_base.config.ModuleConstant
import com.yunfeng.module_base.dto.TimeHourDto

class OrderHourPickerAdapter :
    BaseQuickAdapter<TimeHourDto, BaseViewHolder>(R.layout.module_base_layout_order_picker_hour_item) {

    var select: Int = -1

    override fun convert(holder: BaseViewHolder, item: TimeHourDto) {
        val hourTv = holder.getView<TextView>(R.id.hour)
        hourTv.text = item.hour
        if (item.status == ModuleConstant.Bool.YES) {
            val sel = select == holder.layoutPosition
            holder.itemView.isSelected = sel
            val textColor = if (sel) {
                ColorUtils.getColor(R.color.orange_FF703A)
            } else {
                ColorUtils.getColor(R.color.gray_555)
            }
            hourTv.isEnabled = true
            hourTv.setTextColor(textColor)
        } else {
            hourTv.isEnabled = false
            hourTv.setTextColor(ColorUtils.getColor(R.color.gray_cccccc))
        }
    }

    fun setEnable() {
        data.forEach {
            it.status = ModuleConstant.Bool.NO
        }
        notifyDataSetChanged()
    }
}