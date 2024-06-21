package com.ablec.myarchitecture.view.matrix

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.util.AttributeSet
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.view.CoordinateView


/**
 * @author shuaihui_hao
 * @date 2024/6/14
 * @description
 */
class MatrixLearnView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : CoordinateView(context, attrs, defStyleAttr, defStyleRes) {

    private val mBitmap by lazy {
        BitmapFactory.decodeResource(resources,R.drawable.nature_95365)
    }

   private val mMatrix = Matrix()



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 创建矩阵
        // 绘制图片
        //相当于先位指定的中心点，再缩放
//        mMatrix.setScale(0.5f,0.5f,20f,20f)
//        mMatrix.setRotate(180f)
//        mMatrix.setSkew(0f,0.5f)
//        mMatrix.setTranslate(0f,-mBitmap.height.toFloat())
        //每次set都会把上个效果清除掉
        mMatrix.reset()
        mMatrix.postScale(0.5f,0.5f)
        canvas.drawBitmap(mBitmap, mMatrix, null)

        mMatrix.preTranslate(0f,-mBitmap.height.toFloat())
        canvas.drawBitmap(mBitmap, mMatrix, null)

        mMatrix.preTranslate(0f,-mBitmap.height.toFloat())
        canvas.drawBitmap(mBitmap, mMatrix, Paint().apply {
            color = Color.RED
            // 创建一个颜色矩阵，用于将图像转换为灰度效果
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0f) // 设置饱和度为0，将图像转换为灰度
            colorFilter= ColorMatrixColorFilter(colorMatrix)
        })
//        mMatrix.preTranslate(0f,-mBitmap.height.toFloat())
        //先施加translate 再施加 scale,最终效果与函数调用顺序无关
//        canvas.drawBitmap(mBitmap, mMatrix, null)
    }




}