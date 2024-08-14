package com.ablec.module_base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.ablec.lib.BaseApplication


/**
 * 全局通信的viewmodel
 * ComponentActivity 默认继承ViewModelStoreOwner,
 * 返回的viewModelStore属于自身管理
 * viewmodel-store使用map管理viewmodel,只要不销毁即可
 * Fragment.activityViewModels 原理也是如此
 */
object ApplicationViewModelProvider {

    private val owner: ViewModelStoreOwner = object : ViewModelStoreOwner {
        override val viewModelStore: ViewModelStore
            get() = ViewModelStore()
    }

    fun <T : ViewModel> globalViewModel(modelClass: Class<T>): T {
        return ViewModelProvider(
            owner,
            ViewModelProvider.AndroidViewModelFactory.getInstance(BaseApplication.instance)
        )[modelClass]
    }
}

