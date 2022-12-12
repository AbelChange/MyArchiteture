package com.droi.hotshopping.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import com.blankj.utilcode.util.ImageUtils
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

    suspend fun getResizeBitmap(context: Context, uri: Uri, targetSize: Int = 2 * 1024): Uri? {
        return withContext(Dispatchers.IO) {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            //decodeBounds
            BitmapFactory.decodeStream(
                context.contentResolver.openInputStream(uri),
                null,
                options
            )
            options.inSampleSize = ImageUtils.calculateInSampleSize(options, 1024, 1024)
            options.inJustDecodeBounds = false
            //decode realBitmap  with option inSampleSize 防止oom
            val decodeBitmap = BitmapFactory.decodeStream(
                context.contentResolver.openInputStream(uri),
                null,
                options
            )

            //压缩到上传要求
            val baos = ByteArrayOutputStream()
            decodeBitmap?.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                baos
            )
            var quality = 90
            while (baos.toByteArray().size / 1024 > targetSize) { // 循环判断如果压缩后图片是否大于targetSize(Kb),大于继续压缩
                baos.reset()
                decodeBitmap?.compress(
                    Bitmap.CompressFormat.JPEG,
                    quality,
                    baos
                )
                quality -= 10 // 每次都减少10
            }
            //baos 2 file 2 uri
            var fileOutputStream: FileOutputStream? = null
            val targetFile = File(context.cacheDir, "temp${System.currentTimeMillis()}.jpg")
            try {
                fileOutputStream =
                    FileOutputStream(targetFile)
                fileOutputStream.write(baos.toByteArray())
                file2Uri(context, targetFile)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            } finally {
                fileOutputStream?.close()
            }
        }
    }


    private fun file2Uri(context: Context, file: File): Uri? {
        return FileProvider.getUriForFile(context, "com.droi.hotshopping.fileprovider", file)
    }


}