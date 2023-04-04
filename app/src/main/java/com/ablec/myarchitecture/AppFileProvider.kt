package com.ablec.myarchitecture

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

class AppFileProvider : FileProvider() {
    override fun onCreate(): Boolean {
        return super.onCreate()
    }

    companion object {
        private const val FILE_PROVIDER_AUTHORITY = "${BuildConfig.APPLICATION_ID}.fileProvider"

        fun getFileUri( name: String): Uri {
            val fileDir = AppApplication.instance.filesDir
            return file2Uri(File(fileDir, name))
        }

        private fun file2Uri(file: File): Uri {
            return getUriForFile(AppApplication.instance, FILE_PROVIDER_AUTHORITY, file)
        }
    }

}