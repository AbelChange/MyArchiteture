package com.ablec.module_base.service

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @author : shuaihui.hao
 * CreateTime : 2023/11/7
 * Description :
 */
interface INativeService : IProvider {
    fun startNativeUi(context:Context)
}