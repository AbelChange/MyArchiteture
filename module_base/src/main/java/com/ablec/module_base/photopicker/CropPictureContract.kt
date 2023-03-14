package com.ablec.module_base.photopicker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper

class CropPictureContract : ActivityResultContract<CropPictureContract.CropConfig, Boolean>() {
    companion object{
        const val TAG = "CropPictureContract"
    }
    data class CropConfig(
        var inputUri: Uri? = null,
        val outputUri: Uri? = null,
        val aspectX: Int = 1,
        val aspectY: Int = 1,
        val outputX: Int = 250,
        val outputY: Int = 250,
        val scale: Boolean = true,
        val scaleUpIfNeeded: Boolean = true,
    )

    @CallSuper
    override fun createIntent(context: Context, config: CropConfig): Intent {
        val cropIntent = Intent("com.android.camera.action.CROP")
        cropIntent.setDataAndType(config.inputUri, "image/*")
        // 开启裁剪：打开的Intent所显示的View可裁剪
        cropIntent.putExtra("crop", "true")
        // 裁剪宽高比
        cropIntent.putExtra("aspectX", config.aspectX)
        cropIntent.putExtra("aspectY", config.aspectY)
        // 裁剪输出大小
        cropIntent.putExtra("outputX", config.outputX)
        cropIntent.putExtra("outputY", config.outputY)
        cropIntent.putExtra("scale", config.scale)
        cropIntent.putExtra("scaleUpIfNeeded", config.scaleUpIfNeeded)

        //如果设置为true 那么data将会返回一个bitmap , 当 return-data 为 false 的时候需要设置MediaStore.EXTRA_OUTPUT
        cropIntent.putExtra("return-data", false)
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, config.outputUri)
        // 图片输出格式
        cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())

        val resInfoList = context.packageManager.queryIntentActivities(cropIntent, PackageManager.MATCH_DEFAULT_ONLY)
        for (resolveInfo in resInfoList) {
            val packageName = resolveInfo.activityInfo.packageName
            try {
                context.grantUriPermission(
                    packageName, config.inputUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            } catch (exception: Exception) {
                Log.e(TAG, "grantUriPermission error : ", exception)
            }
            try {
                context.grantUriPermission(
                    packageName, config.outputUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            } catch (exception: Exception) {
                Log.e(TAG, "grantUriPermission error : ", exception)
            }
        }
        return cropIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return resultCode == Activity.RESULT_OK && intent != null
    }


}