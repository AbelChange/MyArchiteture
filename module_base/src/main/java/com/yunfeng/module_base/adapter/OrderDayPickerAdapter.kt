package com.yunfeng.module_base.adapter

import android.util.TypedValue.COMPLEX_UNIT_SP
import android.widget.TextView
import com.blankj.utilcode.util.ColorUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yunfeng.module_base.R
import com.yunfeng.module_base.config.ModuleConstant
import com.yunfeng.module_base.dto.TimeDayDto

class OrderDayPickerAdapter() :
    BaseQuickAdapter<TimeDayDto, BaseViewHolder>(R.layout.module_base_layout_order_picker_day_item) {

    var select: Int = 0

    override fun convert(holder: BaseViewHolder, item: TimeDayDto) {
        val dayStr = holder.getView<TextView>(R.id.day_str)
        val day = holder.getView<TextView>(R.id.day)
        dayStr.text = item.title?.substringAfterLast("-")
        if (item.fullBooked == ModuleConstant.Bool.NO) {
            day.text = item.day
            holder.itemView.isEnabled = true
            dayStr.isEnabled = true
            day.isEnabled = true
            day.setTextSize(COMPLEX_UNIT_SP, 20F)
            val pos = holder.layoutPosition
            val sel = select == pos
            holder.itemView.isSelected = sel
            holder.itemView.isEnabled = item.fullBooked == ModuleConstant.Bool.NO
            val textColor = if (sel) {
                ColorUtils.getColor(R.color.white)
            } else {
                ColorUtils.getColor(R.color.gray_555)
            }
            dayStr.setTextColor(textColor)
            day.setTextColor(textColor)
        } else {
            day.text = context.getString(R.string.module_base_full_booked)
            holder.itemView.isEnabled = false
            dayStr.isEnabled = false
            day.isEnabled = false
            day.setTextSize(COMPLEX_UNIT_SP, 13F)
        }
    }
}