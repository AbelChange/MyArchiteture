package com.ablec.module_base.view.loading

import android.content.Context
import android.view.View
import com.ablec.module_base.R
import com.kingja.loadsir.callback.Callback;

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/5/27 11:17
 */
class LoadingCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.loading_load
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
}