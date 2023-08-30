package com.ablec.myarchitecture

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import com.ablec.module_base.provider.BaseInitializer.Companion.GlobalContext
import java.io.File

class AppFileProvider : FileProvider() {
    override fun onCreate(): Boolean {
        return super.onCreate()
    }

    companion object {
        private const val FILE_PROVIDER_AUTHORITY = "${BuildConfig.APPLICATION_ID}.fileProvider"

        fun getFileUri( name: String): Uri {
            val fileDir = GlobalContext.filesDir
            return file2Uri(File(fileDir, name))
        }

        private fun file2Uri(file: File): Uri {
            return getUriForFile(GlobalContext, FILE_PROVIDER_AUTHORITY, file)
        }

        fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
            val contentResolver: ContentResolver = context.contentResolver
            val bitmap: Bitmap? = try {
                // 从 URI 中获取图片的字节流
                val inputStream = contentResolver.openInputStream(uri)
                // 将字节流解码成 Bitmap 对象
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            return bitmap
        }

    }

}