package com.ablec.myarchitecture.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.ablec.lib.ext.dp
import com.ablec.myarchitecture.R
import com.blankj.utilcode.util.SizeUtils

class CustomLinearLayout : LinearLayout {

    private var circlePaint: Paint = Paint()
    private var borderPaint: Paint = Paint()

    // 边界线的宽度
    private var borderWidth = 1.dp

    // 圆环的宽度
    private var circleWidth = 8.dp

    // 边界线的弧度（范围 0 - 360）
    private var borderArcDegrees = 180f

    // 边界线的矩形
    private lateinit var borderRect: RectF

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        // 设置圆环画笔
        circlePaint.color = ContextCompat.getColor(context, R.color.white)
        circlePaint.style = Paint.Style.STROKE
        circlePaint.strokeWidth = circleWidth.toFloat() // 圆环宽度
        circlePaint.isAntiAlias = true // 抗锯齿

        // 设置边界线画笔
        borderPaint.color = ContextCompat.getColor(context, R.color.red)
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = borderWidth.toFloat() // 边界线宽度
        borderPaint.isAntiAlias = true // 抗锯齿
        borderPaint.strokeCap = Paint.Cap.ROUND


        // 初始化边界线的矩形
        borderRect = RectF()
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        val height = measuredHeight
        Log.d("das", SizeUtils.px2dp(width.toFloat()).toString())
        borderRect.set(0f, 0f, width.toFloat(), height.toFloat())
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawArc(
            circleWidth / 2f,
            circleWidth / 2f,
            (width - circleWidth / 2f),
            (height - circleWidth / 2f),
            0f,
            360f,
            false,
            circlePaint
        )
        //240 60
        //180 180
        //200 140

        // 绘制边界线
        val startAngle = 220f
        val sweepAngle = 180f - 2 * (startAngle - 180f)
        canvas?.drawArc(
            borderWidth / 2f,
            borderWidth / 2f,
            (width - borderWidth / 2f),
            (height - borderWidth / 2f),
            startAngle,
            sweepAngle,
            false,
            borderPaint
        )
        super.onDraw(canvas)
    }




}
