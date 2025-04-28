package com.ablec.module_base.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.ablec.lib.ext.dp
import com.blankj.utilcode.util.ScreenUtils


/**
 * @sample com.jidouauto.hmi.demo.fragment.PopFragment
 */
class PopupWindow @JvmOverloads constructor(
    val context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : PopupWindow(context, attrs, defStyleAttr, defStyleRes) {

    enum class VerticalPosition {
        ABOVE, BELOW
    }

    enum class HorizontalPosition {
        CENTER, ALIGN_LEFT, ALIGN_RIGHT
    }

    enum class OutOfBoundsDirection {
        TOP, BOTTOM, LEFT, RIGHT
    }

    private var animIn = false

    init {
        isOutsideTouchable = true
        isFocusable = true
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    /**
     * Show at relative position to anchor View with translation.
     * @param anchor Anchor View
     * @param vertPos Vertical Position Flag
     * @param horizPos Horizontal Position Flag
     * @param horizPos Horizontal Position Flag
     * @param dimAmount the amount of dimming to apply. Range is from 1.0 for completely opaque to 0.0 for no dim.
     * @param onPopupPositionAdjustedListener OnPopupPositionAdjustedListener 展示失败的回调，业务方可以尝试更换position
     */
    fun showAtLocation(
        anchor: View,
        verticalPosition: VerticalPosition,
        horizontalPosition: HorizontalPosition,
        anim: Boolean = true,
        dimAmount: Float = 0.3f, // 默认值
        xOffset: Int = 0, //  X 轴偏移
        yOffset: Int = 0, //  Y 轴偏移
        onPopupPositionAdjustedListener: OnPopupPositionAdjustedListener? = null
    ) {
        val screenWidth = ScreenUtils.getScreenWidth()
        val screenHeight = ScreenUtils.getScreenHeight()

        val location = IntArray(2)
        anchor.getLocationOnScreen(location)
        val anchorX = location[0]
        val anchorY = location[1]
        val anchorWidth = anchor.width
        val anchorHeight = anchor.height

        val newX = when (horizontalPosition) {
            HorizontalPosition.CENTER -> anchorX + (anchorWidth - width) / 2
            HorizontalPosition.ALIGN_LEFT -> anchorX
            HorizontalPosition.ALIGN_RIGHT -> anchorX + anchorWidth - width
        } + xOffset

        val newY = when (verticalPosition) {
            VerticalPosition.ABOVE -> anchorY - height
            VerticalPosition.BELOW -> anchorY + anchorHeight
        } + yOffset

        // 检查上下边界
        if (newY < 0 && verticalPosition == VerticalPosition.ABOVE) {
            onPopupPositionAdjustedListener?.onPositionOutOfScreen(OutOfBoundsDirection.TOP)
            return
        } else if ((newY + height > screenHeight) && verticalPosition == VerticalPosition.BELOW) {
            onPopupPositionAdjustedListener?.onPositionOutOfScreen(OutOfBoundsDirection.BOTTOM)
            return
        }

        // 检查左右边界
        if (newX < 0) {
            onPopupPositionAdjustedListener?.onPositionOutOfScreen(OutOfBoundsDirection.LEFT)
            return
        } else if (newX + width > screenWidth) {
            onPopupPositionAdjustedListener?.onPositionOutOfScreen(OutOfBoundsDirection.RIGHT)
            return
        }

        // 动画处理
        animIn = anim
        if (anim) {
            contentView.alpha = 0f
            contentView.scaleX = 0.5f
            contentView.scaleY = 0.5f
            contentView.post {
                contentView.pivotX = when (horizontalPosition) {
                    HorizontalPosition.CENTER -> contentView.width / 2f
                    HorizontalPosition.ALIGN_LEFT -> 0f
                    HorizontalPosition.ALIGN_RIGHT -> contentView.width.toFloat()
                }
                contentView.pivotY = when (verticalPosition) {
                    VerticalPosition.ABOVE -> contentView.height.toFloat()
                    VerticalPosition.BELOW -> 0f
                }
                contentView.animate()
                    .alpha(1.0f)
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(150)
                    .start()
            }
        }

        showAtLocation(anchor, Gravity.NO_GRAVITY, newX, newY)
        dimWindow(dimAmount)
    }

    private fun dimWindow(dimAmount: Float) {
        val container = contentView.takeIf { it.layoutParams is WindowManager.LayoutParams }
            ?: (contentView.parent as? View)?.takeIf { it.layoutParams is WindowManager.LayoutParams }
            ?: (contentView.parent.parent as? View)?.takeIf { it.layoutParams is WindowManager.LayoutParams }
            ?: throw IllegalStateException("NO WindowManager.LayoutParams!")

        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val lp = container.layoutParams as WindowManager.LayoutParams
        lp.flags = lp.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lp.dimAmount = dimAmount
        wm.updateViewLayout(container, lp)
    }

    override fun dismiss() {
        if (animIn) {
            contentView?.animate()?.apply {
                alpha(0f)
                    .scaleX(0.5f)
                    .scaleY(0.5f)
                    .setDuration(150)
                    .withEndAction { super.dismiss() }
                    .start()
            } ?: super.dismiss()
        } else {
            super.dismiss()
        }
    }


    interface OnPopupPositionAdjustedListener {
        fun onPositionOutOfScreen(verticalPosition: OutOfBoundsDirection)
    }

}