package com.ablec.module_base.view.loading


import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback;
import com.ablec.module_base.R


/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/5/27 11:17
 */
class EmptyCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.loading_empty
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return false
    }

    override fun getSuccessVisible(): Boolean {
        return super.getSuccessVisible()
    }

    override fun onAttach(context: Context?, view: View?) {
        super.onAttach(context, view)
    }

    override fun onDetach() {
        super.onDetach()
    }
}