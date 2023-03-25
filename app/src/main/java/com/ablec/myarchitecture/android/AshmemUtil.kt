package com.ablec.myarchitecture.android

import android.os.MemoryFile
import android.os.ParcelFileDescriptor
import java.io.FileDescriptor

 fun MemoryFile.fileDescriptor(): ParcelFileDescriptor? {
    var pfd: ParcelFileDescriptor? = null
    try {
        // 将 MemoryFile 转换成 ParcelFileDescriptor
        val method = MemoryFile::class.java.getDeclaredMethod("getFileDescriptor")
        val fd = method.invoke(this) as FileDescriptor
        pfd = ParcelFileDescriptor.dup(fd)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return pfd
}