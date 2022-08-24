package com.ablec.module_base.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.ablec.module_base.R


/**
 * @author HaoShuaiHui
 * @description: dialog 支持viewmodel + bottom动画 + 宽度自定义（高度随机）
 * @date :2022/3/9 17:44
 */
abstract class BaseDialogFragment(@LayoutRes private val contentLayoutId: Int) :
    AppCompatDialogFragment(contentLayoutId) {

    protected val fragmentTag = javaClass.simpleName

    //点击外部取消
    protected open fun isCancelableOutside(): Boolean {
        return true
    }

    //返回按钮取消
    protected open fun canBeCancel(): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BaseDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            setCanceledOnTouchOutside(isCancelableOutside())
            setCancelable(canBeCancel())
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        val gravity = getGravity()
        dialog?.window?.apply {
            setDimAmount(0.3f)
            setGravity(gravity)
            //底部抬升
            if (gravity == Gravity.BOTTOM) {
                setWindowAnimations(R.style.BottomDialogTheme)
            }
            setLayout(getWindowAttrWidth(), WindowManager.LayoutParams.WRAP_CONTENT)
        }
    }

    protected open fun getWindowAttrWidth(): Int {
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

    override fun dismiss() {
        dialog?.setOnDismissListener(null)
        dialog?.setOnKeyListener(null)
        super.dismiss()
    }

    override fun dismissAllowingStateLoss() {
        dialog?.setOnDismissListener(null)
        dialog?.setOnKeyListener(null)
        super.dismissAllowingStateLoss()
    }

}
