package com.ablec.myarchitecture

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


/**
 * @author HaoShuaiHui
 * @description:
 * @date :2022/12/8 17:34
 */
object ImageUtil {

    /**
     * 压缩图片
     * @param context 上下文
     * @param uri 图片的 Uri
     * @param size 压缩后的大小，单位 KB
     */
    suspend fun compressImage(context: Context, uri: Uri, size: Int): Uri {
        return withContext(Dispatchers.IO) {
            val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            var quality = 100
            while (byteArrayOutputStream.toByteArray().size / 1024 > size) {
                byteArrayOutputStream.reset()
                quality -= 10
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
            }
            val file = File(context.cacheDir, "temp.jpg")
            val fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(byteArrayOutputStream.toByteArray())
            fileOutputStream.flush()
            fileOutputStream.close()
            val newUri = AppFileProvider.generateContentUri(file)
            bitmap.recycle()
            newUri
        }
    }

}