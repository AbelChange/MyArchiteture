package com.ablec.module_base.http.exception

/**
 * api自定义Exception
 */
class ApiException(cause: Throwable?, var code: Int) : Exception(cause) {

    /**
     * 错误信息
     */
    override var message: String? = null
}