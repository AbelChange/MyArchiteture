package com.yunfeng.lib.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.yunfeng.lib.dialog.LoadingDialog
import com.yunfeng.lib.ext.showToast

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/4/28 11:05
 */
abstract class BaseFragment : VisibilityFragment() {

    open var waitDialog: LoadingDialog? = null

    fun showWaitDialog(cancelable: Boolean = false) {
        if (activity == null || requireActivity().isFinishing || requireActivity().isDestroyed) {
            return
        }
        if (!isAdded && isDetached) {
            return
        }
        if (null == waitDialog) {
            waitDialog = LoadingDialog(requireContext())
            lifecycle.addObserver(waitDialog!!)
        }
        waitDialog?.setCancelable(cancelable)
        waitDialog?.setCanceledOnTouchOutside(cancelable)
        waitDialog?.show()
    }

    fun hideWaitDialog() {
        waitDialog?.let {
            it.dismiss()
            lifecycle.removeObserver(it)
        }
    }

    fun showToast(content: String? = null, duration: Int = Toast.LENGTH_SHORT) {
        requireActivity().showToast(content, duration)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //正常加载重写
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    //默认懒加载
    override fun onVisibleFirst() {
        super.onVisibleFirst()
        lazyInit()
    }

    abstract fun lazyInit()
}