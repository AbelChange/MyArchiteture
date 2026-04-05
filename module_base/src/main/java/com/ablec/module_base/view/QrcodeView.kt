package com.ablec.module_base.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Outline
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
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

    private var logoResId: Int = 0
        set(value) {
            field = value
            updateQrCode()
        }

    private var logoSizePercentage: Int = -1 // logo占用整个imageview的比例 Int &&  >0
    private var margin: Int = 1
    private var errorCorrectionLevel: ErrorCorrectionLevel = ErrorCorrectionLevel.L
    private var pixelColor: Int = Color.BLACK
    private var gapColor: Int = Color.WHITE
    private var cornerRadius: Float = 0f

    init {
        attrs?.let { parseAttributes(context, it, defStyleAttr) }
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
            logoResId = typedArray.getResourceId(R.styleable.QrcodeView_logo, 0)
            logoSizePercentage =
                typedArray.getInteger(R.styleable.QrcodeView_logoSizePercentage, -1)
            margin = typedArray.getInteger(R.styleable.QrcodeView_margin, 1)
            val levelOrdinal = typedArray.getInt(R.styleable.QrcodeView_errorCorrectionLevel, 0)
            errorCorrectionLevel = ErrorCorrectionLevel.forBits(levelOrdinal)
        } finally {
            typedArray.recycle()
        }
        if (cornerRadius > 0) {
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View?, outline: Outline?) {
                    outline?.setRoundRect(
                        0,
                        0,
                        view?.width ?: 0,
                        view?.height ?: 0,
                        cornerRadius
                    )
                }
            }
            clipToOutline = true // 启用裁剪
        }
    }

    private fun updateQrCode() {
        post {
            setImageBitmap(createBitmap(content))
        }
    }

    private fun createBitmap(content: String?): Bitmap? {
        val size = minOf(width, height)
        val baseBitmap = if (content.isNullOrBlank()) {
            Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888).apply {
                eraseColor(gapColor)
            }
        } else {
            val writer = QRCodeWriter()
            try {
                val hints = mutableMapOf<EncodeHintType, Any>()
                hints[EncodeHintType.MARGIN] = margin
                hints[EncodeHintType.ERROR_CORRECTION] = errorCorrectionLevel
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
                ).copy(Bitmap.Config.ARGB_8888, true)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }

        // 添加中心 Logo（如果有）
        ContextCompat.getDrawable(context, logoResId)?.let {
            drawCenterLogo(baseBitmap, it, size)
        }
        return baseBitmap
    }

    // 绘制 Logo 在二维码中间
    private fun drawCenterLogo(bitmap: Bitmap, logo: Drawable?, size: Int) {
        if (logo == null) {
            return
        }
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

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        return SavedState(superState).also {
            it.content = this.content
            it.logoResId = this.logoResId
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            content = state.content
            logoResId = state.logoResId
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    private class SavedState : BaseSavedState {
        var content: String = ""
        var logoResId: Int = 0

        constructor(superState: Parcelable?) : super(superState)

        private constructor(parcel: Parcel) : super(parcel) {
            content = parcel.readString() ?: ""
            logoResId = parcel.readInt()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(content)
            out.writeInt(logoResId)
        }

        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(parcel: Parcel): SavedState {
                    return SavedState(parcel)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

}