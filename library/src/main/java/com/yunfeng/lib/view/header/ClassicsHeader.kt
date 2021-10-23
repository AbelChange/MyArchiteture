package com.yunfeng.lib.view.header

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshKernel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import com.scwang.smart.refresh.layout.util.SmartUtil
import com.yunfeng.common.R
import com.yunfeng.lib.ext.dp
import com.yunfeng.lib.glide.GlideUtils

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/6/21 11:25
 */
class ClassicsHeader : LinearLayout, RefreshHeader {

    private lateinit var mHeaderText: TextView

    private lateinit var mHeaderImage: ImageView

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    private fun initView(context: Context) {
        gravity = Gravity.CENTER
        mHeaderText = TextView(context);
//        addView(mHeaderText, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        mHeaderImage = ImageView(context)
        addView(mHeaderImage, 45.dp, 45.dp)
        minimumHeight = SmartUtil.dp2px(45f)
    }

    @NonNull
    override fun getView(): View {
        return this
    }

    override fun getSpinnerStyle(): SpinnerStyle {
        return SpinnerStyle.Translate //指定为平移，不能null
    }

    override fun onStateChanged(
        refreshLayout: RefreshLayout,
        oldState: RefreshState,
        newState: RefreshState
    ) {
        when (newState) {
            RefreshState.PullDownToRefresh -> {
                mHeaderText.text = "下拉开始刷新"
                GlideUtils.loadWebP(context, R.drawable.loading, mHeaderImage)
            }
            RefreshState.Refreshing -> {
                mHeaderText.text = "正在刷新"
            }
            RefreshState.ReleaseToRefresh -> {
                mHeaderText.text = "释放立即刷新"
            }
            else -> {

            }
        }
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        if (success) {
            mHeaderText.text = "刷新完成"
        } else {
            mHeaderText.text = "刷新失败"
        }
        return 200 //延迟500毫秒之后再弹回
    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
        //开始动画
    }

    override fun onMoving(
        isDragging: Boolean,
        percent: Float,
        offset: Int,
        height: Int,
        maxDragHeight: Int
    ) {
    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {}
    fun onPulling(percent: Float, offset: Int, headHeight: Int, maxDragHeight: Int) {}
    fun onReleasing(percent: Float, offset: Int, headHeight: Int, maxDragHeight: Int) {}
    fun onRefreshReleased(layout: RefreshLayout?, headerHeight: Int, maxDragHeight: Int) {}
    override fun setPrimaryColors(@ColorInt vararg colors: Int) {}
}