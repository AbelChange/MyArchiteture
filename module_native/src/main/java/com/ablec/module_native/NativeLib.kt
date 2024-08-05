package com.ablec.module_native

object NativeLib {
    init {
        System.loadLibrary("nativelib")
    }

    /**
     * A native method that is implemented by the 'module_native' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    external fun setJavaCallback(cb: CallBack)

    external fun release()

}

interface CallBack {
    fun onStringGet(str: String)
}