package com.yunfeng.lib.base

import android.app.Activity
import android.content.DialogInterface
import android.util.DisplayMetrics
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentManager
import com.yunfeng.lib.dialog.LoadingDialog
import com.yunfeng.lib.dialog.dialogmanager.IPriorityDialog

/**
 * dialog fragment, 固定了宽度
 *
 * @author shixingxing
 */
open class BaseDialogFragment : AppCompatDialogFragment(), IPriorityDialog {
    private var cancelable = true
    private var widthPercentage = 0.9f
    private var listener: IPriorityDialog.OnDismissListener? = null

    open var waitDialog: LoadingDialog? = null
    fun setWidthPercentage(widthPercentage: Float) {
        this.widthPercentage = widthPercentage
    }

    override fun setCancelable(cancelable: Boolean) {
        super.setCancelable(cancelable)
        this.cancelable = cancelable
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            if (widthPercentage > 0) {
                val dm = DisplayMetrics()
                activity?.windowManager?.defaultDisplay?.getMetrics(dm)
                dialog.window!!.setLayout(
                    (dm.widthPixels * widthPercentage).toInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
            isCancelable = cancelable
            dialog.setCanceledOnTouchOutside(cancelable)
        }
    }

    override fun dismiss() {
        try {
            super.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun show(activity: Activity, manager: FragmentManager, tag: String?) {
        show(manager, tag)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            super.show(manager, tag)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

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

    open override fun getPriority(): Int {
        return IPriorityDialog.NORMAL
    }

    override fun setOnDismissListener(listener: IPriorityDialog.OnDismissListener) {
        this.listener = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        this.listener?.onDismiss()
        this.listener = null
    }
}