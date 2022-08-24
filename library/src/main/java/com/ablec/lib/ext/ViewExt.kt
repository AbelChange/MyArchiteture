package com.ablec.lib.ext

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.TouchDelegate
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import kotlin.properties.Delegates

/**
 * @Description:
 * @Author: haoshuaihui
 * @CreateDate: 2021/1/28 14:42
 */
val Int.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

fun Context.showToast(stringResId: Int, duration: Int = Toast.LENGTH_SHORT) {
    if (duration == Toast.LENGTH_SHORT) {
        ToastUtils.showShort(stringResId)
    } else {
        ToastUtils.showLong(stringResId)
    }
}

fun Context.showToast(content: String? = null, duration: Int = Toast.LENGTH_SHORT) {
    if (duration == Toast.LENGTH_SHORT) {
        ToastUtils.showShort(content)
    } else {
        ToastUtils.showLong(content)
    }
}

/*--------------------View start---------------*/
fun View.debounceClick(
    windowDuration: Long = 800,
    listener: View.OnClickListener
) {
    var clickTime by Delegates.observable(0L) { pre, old, new ->
        if (new - old >= windowDuration) {
            listener.onClick(this)
        }
    }
    setOnClickListener {
        clickTime = System.currentTimeMillis()
    }
}


/**
 * 扩大点击区域，适用于 view 相对“孤立”的case
 */
fun View.increaseClickArea(
    left: Int = 0,
    right: Int = 0,
    top: Int = 0,
    bottom: Int = 0,
) {
    val delegateArea = Rect()
    val delegate: View = this
    delegate.getHitRect(delegateArea)
    delegateArea.top -= top.dp
    delegateArea.bottom += bottom.dp
    delegateArea.left -= left.dp
    delegateArea.right += right.dp
    if (delegate.parent is View) {
        val touchDelegate = TouchDelegate(delegateArea, delegate);
        delegate.touchDelegate = touchDelegate
    }
}

/*--------------------View end---------------*/


/**
 * recyclerview间距
 * 只处理竖直edge
 * 逻辑：第一个item无top
 */
fun getVerticalItemDecoration(
    left: Int = 0,
    right: Int = 0,
    top: Int = 0,
    bottom: Int = 0,
    includeEdge: Boolean = true
): RecyclerView.ItemDecoration {
    return object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            if (includeEdge) {
                outRect.top = top.dp
            } else {
                val position = parent.getChildAdapterPosition(view)
                if (position != 0) {
                    outRect.top = top.dp
                }
            }
            outRect.bottom = bottom.dp
            outRect.left = left.dp
            outRect.right = right.dp
        }
    }
}

fun getHorizontalItemDecoration(
    left: Int = 0,
    right: Int = 0,
    top: Int = 0,
    bottom: Int = 0,
    includeEdge: Boolean = true
): RecyclerView.ItemDecoration {
    return object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            if (includeEdge) {
                outRect.left = left.dp
            } else {
                val position = parent.getChildAdapterPosition(view)
                if (position != 0) {
                    outRect.left = left.dp
                }
            }
            outRect.top = top.dp
            outRect.bottom = bottom.dp
            outRect.right = right.dp
        }
    }
}

/**
 * 改变指定文本的颜色
 * @param rawText
 * @param colorTextArray
 * @param colorArray
 * @return
 */
@SuppressLint("ResourceAsColor")
fun TextView.setSpannable(
    spannableText: String,
    underLine: Boolean = false,
    @ColorRes color: Int = android.R.color.transparent,
    clickAction: () -> Unit
) {
    val start = this.text.indexOf(spannableText)
    if (start < 0) {
        return
    }
    val spannableString = SpannableString(this.text)
    spannableString.setSpan(
        ForegroundColorSpan(color),
        start, start + spannableText.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )
    highlightColor = color
    spannableString.setSpan(object : ClickableSpan() {
        override fun onClick(widget: View) {
            clickAction.invoke()
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = ds.linkColor
            ds.isUnderlineText = underLine
        }
    }, start, start + spannableText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.text = spannableString
    this.movementMethod = LinkMovementMethod.getInstance()
}













