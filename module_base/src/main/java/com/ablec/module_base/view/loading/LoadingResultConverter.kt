package com.ablec.module_base.view.loading

import com.ablec.module_base.http.ApiResp
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.Convertor

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/5/27 12:29
 */
class LoadingResultConverter<T> : Convertor<ApiResp<T>> {
    override fun map(t: ApiResp<T>): Class<out Callback> {
        var resultCode: Class<out Callback?> =
            SuccessCallback::class.java
        if (!t.success) {
            resultCode = ErrorCallback::class.java
        } else if (t.data == null) {
            resultCode = EmptyCallback::class.java
        }
        return resultCode
    }
}