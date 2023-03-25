package com.ablec.module_base.util

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import java.io.*
import java.text.DecimalFormat
import java.util.*

object FileUtil {

    fun getFileUriPath(uri: Uri?): String? {
        if (uri == null) {
            return null
        }
        val scheme = uri.scheme
        return if ("file" == scheme) {
            uri.path
        } else null
    }

    /**
     * 创建空白文件
     *
     * @param file 文件
     * @return 是否创建成功
     */
    fun createFile(file: File): Boolean {
        return try {
            if (!file.parentFile.exists()) {
                mkDir(file.parentFile)
            }
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 创建目录
     *
     * @param dir 文件
     * @return 是否创建成功
     */
    fun mkDir(dir: File): Boolean {
        while (dir.parentFile?.exists() == true) {
            dir.parentFile?.let { mkDir(it) }
        }
        return dir.mkdir()
    }

    /**
     * 检验文件是否目录完整，若不完整则创建
     *
     * @param path 文件路径
     */
    fun checkAndMakeDirs(path: String?) {
        val dstFile = File(path)
        if (dstFile != null) {
            if (dstFile.isDirectory) {
                if (!dstFile.exists()) {
                    dstFile.mkdirs()
                }
            } else {
                if (dstFile.parentFile != null && !dstFile.parentFile.exists()) {
                    dstFile.parentFile.mkdirs()
                }
            }
        }
    }

    fun makeDirs(dir: String?) {
        val dstFile = File(dir)
        dstFile.mkdirs()
    }

    /**
     * 判断文件是否存在
     *
     * @param path 文件路径
     * @return 是否存在
     */
    fun isExist(path: String?): Boolean {
        if (!TextUtils.isEmpty(path)) {
            val file = File(path)
            if (file.exists()) {
                return true
            }
        }
        return false
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    fun getFormatSize(fileS: Long): String {
        val df = DecimalFormat("#.00")
        var fileSizeString = ""
        val wrongSize = "0B"
        if (fileS == 0L) {
            return wrongSize
        }
        fileSizeString = if (fileS < 1024) {
            df.format(fileS.toDouble()) + "B"
        } else if (fileS < 1048576) {
            df.format(fileS.toDouble() / 1024) + "KB"
        } else if (fileS < 1073741824) {
            df.format(fileS.toDouble() / 1048576) + "MB"
        } else {
            df.format(fileS.toDouble() / 1073741824) + "GB"
        }
        return fileSizeString
    }

    /**
     * 获取文件大小
     */
    fun getFileSize(path: String?): Long {
        if (!isExist(path)) {
            return 0
        }
        var fis: FileInputStream? = null
        try {
            fis = FileInputStream(File(path))
            return fis.available().toLong()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     * 获取文件夹大小
     */
    fun getDirectorySize(path: String?): Long {
        var size: Long = 0
        val files: Array<File> = File(path).listFiles()
        files.forEach {
            size += if (it.isDirectory) {
                getFileSize(it.absolutePath)
            } else {
                it.length()
            }
        }
        return size
    }

    /**
     * 删除文件或目录，若是目录则删除目录内所有文件和自身
     *
     * @param file 文件或目录路径
     */
    fun delete(file: File) {
        if (file.isFile) {
            file.delete()
            return
        }
        if (file.isDirectory) {
            val childFiles = file.listFiles()
            if (childFiles == null || childFiles.size == 0) {
                file.delete()
                return
            }
            for (i in childFiles.indices) {
                delete(childFiles[i])
            }
            file.delete()
        }
    }

    fun deleteFile(path: String?) {
        path?.let {
            val file = File(path)
            if (file.isDirectory) {
                delete(file)
            } else if (file.exists()) {
                file.delete()
            }
        }
    }

    /**
     * 获取文件名
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    fun getFileName(filePath: String): String {
        return filePath.replace(".*[\\\\/]|\\.[^\\.]*$‌​".toRegex(), "")
    }

    /***
     * 清理所有缓存
     * @param context
     */
    fun clearAllCache(context: Context) {
        deleteDir(context.cacheDir)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            deleteDir(context.externalCacheDir)
        }
    }

    private fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
        }
        return dir!!.delete()
    }

    /**
     * @param bmp 获取的bitmap数据
     * @param picName 自定义的图片名
     */
    fun saveBitmap2Gallery(context: Context, image: Bitmap, name: String = "image") {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P
            && !PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ) {
            if (context is FragmentActivity) {
//                RxPermissions(context).requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    .subscribe {
//                        if (it.granted) {
//                            doActualSaveBitmap(name, context, image)
//                        } else {
//                            ToastUtils.showShort("存储权限被拒绝，无法保存")
//                        }
//                    }
            } else if (context is Fragment) {
//                RxPermissions(context).requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    .subscribe {
//                        if (it.granted) {
//                            doActualSaveBitmap(name, context, image)
//                        } else {
//                            ToastUtils.showShort("存储权限被拒绝，无法保存")
//                        }
//                    }
            }
        } else {
            doActualSaveBitmap(name, context, image)
        }
    }

    private fun doActualSaveBitmap(
        name: String,
        context: Context,
        image: Bitmap
    ) {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.DESCRIPTION, "This is an image")
        values.put(MediaStore.Images.Media.DISPLAY_NAME, name)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.TITLE, System.currentTimeMillis().toString() + ".png")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/Camera")
        }
        val external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val resolver: ContentResolver = context.contentResolver
        val insertUri = resolver.insert(external, values)
        var os: OutputStream? = null
        if (insertUri != null) {
            try {
                os = resolver.openOutputStream(insertUri)
                image.compress(Bitmap.CompressFormat.PNG, 90, os)
                values.clear()
                //publish it
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    values.putNull(MediaStore.MediaColumns.DATE_EXPIRES);
                    resolver.update(insertUri, values, null, null);
                }
                ToastUtils.showShort("图片已保存到相册")
            } catch (e: IOException) {
                LogUtils.e(e)
            } finally {
                try {
                    os?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}