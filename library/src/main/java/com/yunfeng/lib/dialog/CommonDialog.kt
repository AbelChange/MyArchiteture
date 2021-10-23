package com.yunfeng.lib.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.blankj.utilcode.util.ScreenUtils
import com.yunfeng.common.R
import com.yunfeng.common.databinding.CommonDialogBinding
import com.yunfeng.lib.ext.debounceClick

open class CommonDialog(context: Context, themeResId: Int = 0) : Dialog(context, themeResId),
    LifecycleObserver {
    private lateinit var mDialogBinding: CommonDialogBinding
    private var mOkClickListener: (() -> Unit)? = null
    private var mCancelClickListener: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDialogBinding = CommonDialogBinding.inflate(LayoutInflater.from(context))
        setContentView(mDialogBinding.root)
        mDialogBinding.cancelBtn.debounceClick {
            dismiss()
            mCancelClickListener?.invoke()
        }
        mDialogBinding.okBtn.debounceClick() {
            dismiss()
            mOkClickListener?.invoke()
        }
    }

    fun setLayoutParams() {
        val layoutParams: ViewGroup.LayoutParams = mDialogBinding.llRoot.layoutParams
        layoutParams.width = ScreenUtils.getAppScreenWidth() - ScreenUtils.getAppScreenHeight()
        mDialogBinding.llRoot.layoutParams = layoutParams
    }

    open fun setContent(
        title: String? = null,
        content: String? = null,
        okStr: String = context.getString(R.string.ok),
        cancelStr: String = context.getString(R.string.cancel),
        okColor: Int = R.color.colorAccent,
        cancelColor: Int = R.color.gray_333,
        okClickListener: (() -> Unit)? = null,
        cancelClickListener: (() -> Unit)? = null
    ) {
        if (title.isNullOrBlank()) {
            mDialogBinding.titleTextview.visibility = GONE
        } else {
            mDialogBinding.titleTextview.text = title
        }
        mDialogBinding.contentTextview.text = content
        mDialogBinding.okBtn.text = okStr
        mDialogBinding.okBtn.setTextColor(ContextCompat.getColor(context, okColor))
        if (cancelStr.isBlank()) {
            mDialogBinding.cancelBtn.visibility = GONE
            mDialogBinding.centerLine.visibility = GONE
        } else {
            mDialogBinding.cancelBtn.text = cancelStr
            mDialogBinding.cancelBtn.setTextColor(ContextCompat.getColor(context, cancelColor))
        }
        mOkClickListener = okClickListener
        mCancelClickListener = cancelClickListener
    }

    fun setContentGravity() {
        mDialogBinding.contentTextview.gravity = Gravity.LEFT
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun destroy() {
        dismiss()
    }
}