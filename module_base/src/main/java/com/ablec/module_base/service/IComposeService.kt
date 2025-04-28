package com.ablec.module_base.service

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

interface IComposeService : IProvider {
    fun start(context: Context?)

    fun ss()
}

