package com.ablec.myarchitecture.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * 坐标显示 用与涉及view坐标的学习
 */
open class CoordinateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val mPaint = Paint()
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    init {
        mPaint.color = Color.BLACK
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth
        mHeight = measuredHeight
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(mWidth / 2.toFloat(), mHeight / 2.toFloat())
        mPaint.strokeWidth = 1f
        canvas.drawLine(
            -mWidth / 2 * 1f, 0f, mWidth / 2 * 1f, 0f, mPaint
        )
        canvas.drawLine(
            0f, -mHeight / 2 * 1f, 0f, mHeight / 2 * 1f, mPaint
        )
        mPaint.strokeWidth = 3f
        canvas.drawLines(
            floatArrayOf(
                mWidth / 2 * 1f, 0f, mWidth / 2 * 1f * 0.95f, -mWidth / 2 * 1f * 0.05f,
                mWidth / 2 * 1f, 0f, mWidth / 2 * 1f * 0.95f, mWidth / 2 * 1f * 0.05f
            ),
            mPaint
        )
        canvas.drawLines(
            floatArrayOf(
                0f, mHeight / 2 * 1f, mWidth / 2 * 1f * 0.05f, mHeight / 2 * 1f - mWidth / 2 * 1f * 0.05f,
                0f, mHeight / 2 * 1f, -mWidth / 2 * 1f * 0.05f, mHeight / 2 * 1f - mWidth / 2 * 1f * 0.05f
            ),
            mPaint
        )
    }
}