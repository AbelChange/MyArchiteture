package com.yunfeng.lib.view.loading

import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.Convertor
import com.yunfeng.lib.http.base.BaseResp

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/5/27 12:29
 */
class LoadingResultConverter<T> : Convertor<BaseResp<T>> {
    override fun map(t: BaseResp<T>): Class<out Callback> {
        var resultCode: Class<out Callback?> =
            SuccessCallback::class.java
        if (!t.isSuccess()) {
            resultCode = ErrorCallback::class.java
        } else if (t.data == null) {
            resultCode = EmptyCallback::class.java
        }
        return resultCode
    }
}