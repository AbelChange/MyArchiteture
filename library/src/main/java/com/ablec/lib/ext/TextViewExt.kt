package com.ablec.lib.ext

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt

/**
 * 改变指定文本的颜色
 * @param rawText
 * @param colorTextArray
 * @param colorArray
 * @return
 */
@SuppressLint("ResourceAsColor")
fun TextView.setSpannable(
    text: CharSequence,
    spannableText: String,
    underLine: Boolean = true,
    @ColorInt foregroundColor: Int,
    @ColorInt highlightColor: Int = Color.TRANSPARENT,
    clickAction: (view: View) -> Unit
) {
    this.text = text
    val start = this.text.indexOf(spannableText)
    if (start < 0) {
        return
    }
    val spannableString = SpannableString(this.text)
    spannableString.setSpan(
        ForegroundColorSpan(foregroundColor),
        start, start + spannableText.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )
    spannableString.setSpan(object : ClickableSpan() {
        override fun onClick(view: View) {
            clickAction.invoke(view)
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = underLine
        }
    }, start, start + spannableText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.highlightColor = highlightColor
    this.text = spannableString
    this.movementMethod = LinkMovementMethod.getInstance()
}