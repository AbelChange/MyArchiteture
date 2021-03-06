package com.yunfeng.lib.ext

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.location.LocationManager
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
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.disposables.Disposable
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

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
fun View.debounceClick(windowDuration: Long = 800,
                       unit: TimeUnit = TimeUnit.MILLISECONDS,
                       listener: (view: View) -> Unit): Disposable {
    return Observable.create(ObservableOnSubscribe<View> { emitter ->
        setOnClickListener {
            if (!emitter.isDisposed) {
                emitter.onNext(it)
            }
        }
    }).throttleFirst(windowDuration, unit)
        .subscribe { listener(it) }
}

/**
 * ?????????????????????????????? view ?????????????????????case
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
 * recyclerview??????
 * ???????????????edge
 * ??????????????????item???top
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
 * ???????????????????????????
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

/**
 * ????????????????????????
 */
fun String?.toReadText(): String {
    try {
        return BigDecimal(this).stripTrailingZeros().toPlainString()
    } catch (e: Exception) {
        LogUtils.e(e)
    }
    return "0"
}

/**
 * app????????????????????????
 */

fun isLocServiceEnable(context: Context): Boolean {
    val locationManager =
        context.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
    val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    return gps || network
}









