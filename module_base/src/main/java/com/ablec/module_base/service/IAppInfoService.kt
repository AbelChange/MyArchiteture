package com.ablec.module_base.service

import com.alibaba.android.arouter.facade.template.IProvider


interface IAppInfoService : IProvider {

    fun channel(): String?

}