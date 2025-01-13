package com.ablec.module_base.photopicker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper


class CropPictureContract : ActivityResultContract<CropPictureContract.CropConfig, Boolean>() {
    companion object{
        const val TAG = "CropPictureContract"
    }
    data class CropConfig(
        var inputUri: Uri? =null,
        val outputUri: Uri? = null,
        val aspectX: Int = 1,
        val aspectY: Int = 1,
        val outputX: Int = 200,
        val outputY: Int = 200,
        val scale: Boolean = true,
        val scaleUpIfNeeded: Boolean = true,
    )

    @CallSuper
    override fun createIntent(context: Context, input: CropConfig): Intent {
        val cropIntent = Intent("com.android.camera.action.CROP")
        val resolveActivity =
            context.packageManager.resolveActivity(cropIntent, PackageManager.MATCH_DEFAULT_ONLY)
        kotlin.runCatching {
            val packageName = resolveActivity?.activityInfo?.packageName
            context.grantUriPermission(
                packageName, input.inputUri,
                Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
            )
            context.grantUriPermission(
                packageName, input.outputUri,
                Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
            )
        }

        cropIntent.setDataAndType(input.inputUri, "image/*")
        // 开启裁剪：打开的Intent所显示的View可裁剪
        cropIntent.putExtra("crop", "true")
        // 裁剪宽高比
        cropIntent.putExtra("aspectX", input.aspectX)
        cropIntent.putExtra("aspectY", input.aspectY)
        // 裁剪输出大小
        cropIntent.putExtra("outputX", input.outputX)
        cropIntent.putExtra("outputY", input.outputY)
        cropIntent.putExtra("scale", input.scale)
        cropIntent.putExtra("scaleUpIfNeeded", input.scaleUpIfNeeded)

        //如果设置为true 那么data将会返回一个bitmap , 当 return-data 为 false 的时候需要设置MediaStore.EXTRA_OUTPUT
        cropIntent.putExtra("return-data", false)
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, input.outputUri)
        // 图片输出格式
        cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        // 是否取消人脸识别
        cropIntent.putExtra("noFaceDetection", true)
        cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        return cropIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return resultCode == Activity.RESULT_OK && intent != null
    }


}