package com.ablec.myarchitecture

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import com.ablec.module_base.provider.BaseInitializer.Companion.GlobalContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class AppFileProvider : FileProvider() {
    override fun onCreate(): Boolean {
        return super.onCreate()
    }

    companion object {

         const val FILE_PROVIDER_AUTHORITY = "${BuildConfig.APPLICATION_ID}.fileProvider"

        // 获取文件的 ContentUri
        fun generateContentUri(name: String): Uri {
            val fileDir = GlobalContext.filesDir
            return generateContentUri(File(fileDir, name))
        }

        fun generateContentUri(file: File): Uri {
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

        // 将 contentUri 保存到指定的文件，并标记为 suspend 函数
        fun saveAndExpose(context: Context, contentUri: Uri, fileName: String): Uri? {
            val contentResolver: ContentResolver = context.contentResolver
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                inputStream = contentResolver.openInputStream(contentUri)
                if (inputStream == null) {
                    return null
                }
                val destinationFile = File(GlobalContext.filesDir, fileName)
                outputStream = FileOutputStream(destinationFile)
                inputStream.copyTo(outputStream)
                return generateContentUri(destinationFile)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            } finally {
                try {
                    inputStream?.close()
                    outputStream?.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

}