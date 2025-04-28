package com.ablec.module_base.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.ablec.module_base.R


abstract class BaseDialogFragment(@LayoutRes private val contentLayoutId: Int) :
    DialogFragment(contentLayoutId) {

    private val fragmentTag: String = javaClass.simpleName

    //点击外部取消
    protected open fun isCancelableOutside(): Boolean {
        return false
    }

    //返回按钮取消
    protected open fun canBeCancel(): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getGravity() == Gravity.BOTTOM) {
            setStyle(STYLE_NO_TITLE, R.style.DialogTheme_Sheet)
        }else{
            setStyle(STYLE_NO_TITLE, R.style.DialogTheme_Fade)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.apply {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));//1.必须设置dialog的window背景为透明颜色，不然圆角无效或者是系统默认的颜色
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCanceledOnTouchOutside(isCancelableOutside())
            setCancelable(canBeCancel())
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            val gravity = getGravity()
            setDimAmount(0.5f)
            setGravity(gravity)
            //底部抬升
            setLayout(getWidth(), getHeight())
        }
    }

    protected open fun getHeight(): Int {
        return WindowManager.LayoutParams.MATCH_PARENT
    }

    protected open fun getWidth(): Int {
        return WindowManager.LayoutParams.MATCH_PARENT
    }

    /*
    * 支持bottom和center
    * */
    protected open fun getGravity(): Int {
        return Gravity.CENTER
    }

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, fragmentTag)
    }



}
