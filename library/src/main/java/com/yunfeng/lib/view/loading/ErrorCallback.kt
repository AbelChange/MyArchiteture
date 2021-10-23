package com.yunfeng.lib.view.loading

import android.content.Context
import android.view.View
import android.widget.TextView
import com.kingja.loadsir.callback.Callback
import com.yunfeng.common.R

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/5/27 11:17
 */
class ErrorCallback : Callback() {
    private lateinit var textViewError: TextView
    private var errorMsg: String? = "加载出错"

    override fun onCreateView(): Int {
        return R.layout.loading_error
    }

    override fun onViewCreate(context: Context, view: View) {
        super.onViewCreate(context, view)
    }

    public fun setMsg(msg: String?) {
        errorMsg = msg
    }

    override fun onAttach(context: Context?, view: View) {
        super.onAttach(context, view)
        textViewError = view.findViewById<TextView>(R.id.tv_errorMessage)
        textViewError.text = errorMsg
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return false
    }
}