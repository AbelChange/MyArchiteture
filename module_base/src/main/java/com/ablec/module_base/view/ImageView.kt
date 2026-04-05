package com.ablec.module_base.view

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Looper
import android.os.MessageQueue
import android.util.AttributeSet
import android.util.Log
import com.ablec.myarchitecture.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.AbsoluteCornerSize
import com.google.android.material.shape.CornerSize
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.ShapeAppearanceModel


open class ImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ShapeableImageView(context, attrs, defStyleAttr), MessageQueue.IdleHandler {

    val TAG = this.javaClass.name

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.ImageView,
            defStyleAttr,
            0
        )

        val round = typedArray.getDimension(R.styleable.ImageView_round, 0f)
        val roundPercent = typedArray.getFloat(R.styleable.ImageView_roundPercent, -1f)
        val cornerTypeOrdinal = typedArray.getInt(
            R.styleable.ImageView_roundCornerType,
            RadiusCornerType.ALL.ordinal
        )
        typedArray.recycle()

        val cornerType = RadiusCornerType.entries[cornerTypeOrdinal]
        setShape(AbsoluteCornerSize(round), cornerType)
        if (roundPercent > 0f) {
            setShape(RelativeCornerSize(roundPercent), cornerType)
        }
    }

    fun setShape(size: CornerSize, cornerType: RadiusCornerType) {
        val shapeBuilder = ShapeAppearanceModel.Builder()
        when (cornerType) {
            RadiusCornerType.ALL -> shapeBuilder.setAllCornerSizes(size)
            RadiusCornerType.TOP_LEFT -> shapeBuilder.setTopLeftCornerSize(size)
            RadiusCornerType.TOP_RIGHT -> shapeBuilder.setTopRightCornerSize(size)
            RadiusCornerType.BOTTOM_LEFT -> shapeBuilder.setBottomLeftCornerSize(size)
            RadiusCornerType.BOTTOM_RIGHT -> shapeBuilder.setBottomRightCornerSize(size)
            RadiusCornerType.TOP -> {
                shapeBuilder.setTopLeftCornerSize(size)
                shapeBuilder.setTopRightCornerSize(size)
            }

            RadiusCornerType.BOTTOM -> {
                shapeBuilder.setBottomLeftCornerSize(size)
                shapeBuilder.setBottomRightCornerSize(size)
            }

            RadiusCornerType.LEFT -> {
                shapeBuilder.setTopLeftCornerSize(size)
                shapeBuilder.setBottomLeftCornerSize(size)
            }

            RadiusCornerType.RIGHT -> {
                shapeBuilder.setTopRightCornerSize(size)
                shapeBuilder.setBottomRightCornerSize(size)
            }
        }
        // Apply the updated ShapeAppearanceModel
        shapeAppearanceModel = shapeBuilder.build()
    }


    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        monitor()
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
    }

    private fun monitor() {
        Looper.myQueue().removeIdleHandler(this)
        Looper.myQueue().addIdleHandler(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Looper.myQueue().removeIdleHandler(this)
    }

    override fun queueIdle(): Boolean {
        post { checkDrawable() }
        return false
    }

    private fun checkDrawable() {
        val mDrawable = drawable ?: return
        if (mDrawable !is BitmapDrawable) {
            return
        }
        val drawableWidth = mDrawable.intrinsicWidth
        val drawableHeight = mDrawable.intrinsicHeight
        val viewWidth = width
        val viewHeight = height
        if (viewWidth != 0 && viewHeight != 0 && (drawableWidth > 2 * viewWidth || drawableHeight > 2 * viewHeight)) {
            log(
                log = "Drawable: $drawableWidth x $drawableHeight, ImageView: $viewWidth x $viewHeight. " +
                        "Tip: Consider resizing large images to save memory. " +
                        "If loading use ImageResource.Remote(), you can use 'sizeMultiplier' to scale the image down."
            )
        }
    }

    private fun log(log: String) {
        Log.e(TAG,log)
    }

    enum class RadiusCornerType {
        ALL,
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
        TOP, BOTTOM, LEFT, RIGHT
    }

}