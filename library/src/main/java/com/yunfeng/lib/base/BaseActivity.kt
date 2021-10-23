package com.yunfeng.lib.base

import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.gyf.immersionbar.ImmersionBar
import com.yunfeng.common.R
import com.yunfeng.lib.dialog.CommonDialog
import com.yunfeng.lib.dialog.LoadingDialog

abstract class BaseActivity : AppCompatActivity() {

    private var waitDialog: LoadingDialog? = null
    private var commonDialog: CommonDialog? = null

    fun setToolbar(
        toolbar: Toolbar?,
        title: String? = null,
        @ColorRes textColor: Int? = R.color.black,
        isShowBackIcon: Boolean = true
    ) {
        if (null != toolbar) {
            setSupportActionBar(toolbar)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(isShowBackIcon)
            val titleView = toolbar.findViewById<TextView>(R.id.tvTitle)
            titleView.text = title
            textColor?.let {
                titleView!!.setTextColor(ContextCompat.getColor(this, it))
            }
            supportActionBar?.title = null

            initImmersionBar(toolbar)
        }
    }

    fun initImmersionBar(toolbar: Toolbar? = null) {
        ImmersionBar.with(this)
            .titleBar(toolbar)
            .statusBarDarkFont(true, 0.2f)
            .transparentStatusBar()
            .init()
    }

    fun showWaitDialog(cancelable: Boolean = false) {
        if (isFinishing || isDestroyed) {
            return
        }
        if (null == waitDialog) {
            waitDialog = LoadingDialog(this)
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

    fun showCommonDialog(
        cancelable: Boolean = false, title: String? = null, content: String? = null,
        okStr: String = getString(R.string.ok), cancelStr: String = getString(R.string.cancel),
        okColor: Int = R.color.orange_ffa800, cancelColor: Int = R.color.gray_555,
        okClickListener: (() -> Unit)? = null,
        cancelClickListener: (() -> Unit)? = null
    ) {
        if (isFinishing || isDestroyed) {
            return
        }
        if (null == commonDialog) {
            commonDialog = CommonDialog(this)
            lifecycle.addObserver(commonDialog!!)
        }
        commonDialog?.setCancelable(cancelable)
        commonDialog?.setCanceledOnTouchOutside(cancelable)
        commonDialog?.show()
        commonDialog?.setContent(
            title,
            content,
            okStr,
            cancelStr,
            okColor,
            cancelColor,
            okClickListener,
            cancelClickListener
        )
    }

    fun hideCommonDialog() {
        commonDialog?.let {
            it.dismiss()
            lifecycle.removeObserver(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        hideCommonDialog()
        hideWaitDialog()
    }
}