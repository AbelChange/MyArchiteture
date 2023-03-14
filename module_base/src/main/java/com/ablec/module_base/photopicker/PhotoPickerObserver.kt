package com.ablec.module_base.photopicker

import android.net.Uri
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class PhotoPickerObserver(host: ActivityResultCaller) : DefaultLifecycleObserver {

    private var mResultUri: Uri? = null
    private var mCallBack: ResultCallBack? = null

    private val pickAlbumLauncher =
        host.registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            mCallBack?.onResult(uri)
        }

    /**
     * 需要添加权限
     */
    private val takePhotoLauncher = host.registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            mCallBack?.onResult(mResultUri)
        } else {
            mCallBack?.onResult(null)
        }
    }

    private val cropPhotoLauncher = host.registerForActivityResult(CropPictureContract()) {
        if (it) {
            mCallBack?.onResult(mResultUri)
        } else {
            mCallBack?.onResult(null)
        }
    }

    fun cropPhoto(config: CropPictureContract.CropConfig, callBack: ResultCallBack) {
        mCallBack = callBack
        mResultUri = config.outputUri
        cropPhotoLauncher.launch(config)
    }

    /**
     * 拍照
     * @param inputUri FileProvider getUriForFile 获取
     */
    fun takePhoto(
        inputUri: Uri,
        callBack: ResultCallBack
    ) {
        mCallBack = callBack
        mResultUri = inputUri
        takePhotoLauncher.launch(inputUri)
    }

    /**
     * 图库选择：拿到之后 BitmapFactory.decodeStream()
     */
    fun pickAlbum(
        callBack: ResultCallBack
    ) {
        mCallBack = callBack
        pickAlbumLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }


    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        mCallBack = null
    }

    interface ResultCallBack {
        fun onResult(uri: Uri?)
    }


}