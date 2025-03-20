package com.ablec.module_base.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Outline
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.doOnPreDraw
import com.ablec.module_base.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel


class QrcodeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.qrcodeViewStyle
) : AppCompatImageView(context, attrs, defStyleAttr) {

    var content: String = ""
        set(value) {
            field = value
            updateQrCode() // 当内容改变时重新生成二维码
        }

    var logo: Drawable? = null
        set(value) {
            field = value
            updateQrCode() // 当logo改变时重新生成二维码
        }
    private var logoSizePercentage: Int = -1 // logo占用整个imageview的比例 Int &&  >0
    private var margin: Int = 1
    private var errorCorrectionLevel: ErrorCorrectionLevel = ErrorCorrectionLevel.L
    private var pixelColor: Int = Color.BLACK
    private var gapColor: Int = Color.WHITE
    private var cornerRadius: Float = 0f

    init {
        attrs?.let { parseAttributes(context, it, defStyleAttr) }
        doOnPreDraw {
            updateQrCode()
        }
    }

    private fun parseAttributes(context: Context, attrs: AttributeSet, defStyleAttr: Int) {
        val typedArray: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.QrcodeView, defStyleAttr, 0
        )
        try {
            content = typedArray.getString(R.styleable.QrcodeView_qrcodeContent) ?: ""
            pixelColor = typedArray.getColor(R.styleable.QrcodeView_pixelColor, Color.BLACK)
            gapColor = typedArray.getColor(R.styleable.QrcodeView_gapColor, Color.WHITE)
            cornerRadius = typedArray.getDimension(R.styleable.QrcodeView_cornerRadius, 0f)
            logo = typedArray.getDrawable(R.styleable.QrcodeView_logo)
            logoSizePercentage =
                typedArray.getInteger(R.styleable.QrcodeView_logoSizePercentage, -1)
            margin = typedArray.getInteger(R.styleable.QrcodeView_margin, 1)
            val levelOrdinal = typedArray.getInt(R.styleable.QrcodeView_errorCorrectionLevel, 0)
            errorCorrectionLevel = ErrorCorrectionLevel.forBits(levelOrdinal)
        } finally {
            typedArray.recycle()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun updateQrCode() {
        if (cornerRadius > 0) {
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View?, outline: Outline?) {
                    outline?.setRoundRect(0, 0, view?.width ?: 0, view?.height ?: 0, cornerRadius)
                }
            }
            clipToOutline = true // 启用裁剪
        }
        if (content.isNotEmpty()) {
            setImageBitmap(createBitmap(content))
        } else {
            setImageBitmap(null)
        }
    }

    // 创建二维码 Bitmap
    private fun createBitmap(content: String): Bitmap? {
        val writer = QRCodeWriter()

        return try {
            val hints = mutableMapOf<EncodeHintType, Any>()
            hints[EncodeHintType.MARGIN] = margin
            hints[EncodeHintType.ERROR_CORRECTION] = errorCorrectionLevel
            val size = minOf(width, height)
            val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size, hints)
            val pixels = IntArray(bitMatrix.width * bitMatrix.height)
            var offset = 0
            for (y in 0 until bitMatrix.height) {
                for (x in 0 until bitMatrix.width) {
                    pixels[offset + x] = if (bitMatrix[x, y]) pixelColor else gapColor
                }
                offset += bitMatrix.width
            }
            Bitmap.createBitmap(
                pixels,
                bitMatrix.width,
                bitMatrix.height,
                Bitmap.Config.ARGB_8888
            ).copy(Bitmap.Config.ARGB_8888, true).apply {
                // 绘制 Logo
                logo?.let {
                    drawCenterLogo(this, it, size)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // 绘制 Logo 在二维码中间
    private fun drawCenterLogo(bitmap: Bitmap, logo: Drawable, size: Int) {
        val logoSize = minOf(logo.intrinsicWidth, logo.intrinsicHeight)
        var logoSizeInPixels = logoSize.toFloat()

        if (logoSizePercentage > 0) {
            val logoFactor = size * logoSizePercentage / 100f / logoSize
            logoSizeInPixels *= logoFactor
        }
        val canvas = Canvas(bitmap)
        val left = (bitmap.width - logoSizeInPixels) / 2
        val top = (bitmap.height - logoSizeInPixels) / 2
        val right = (bitmap.width + logoSizeInPixels) / 2
        val bottom = (bitmap.height + logoSizeInPixels) / 2
        logo.setBounds(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        logo.draw(canvas)
    }


}