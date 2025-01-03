package com.ablec.myarchitecture.android

import android.Manifest
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ablec.lib.BaseApplication
import com.ablec.module_base.photopicker.BottomPickPhotoDialog
import com.ablec.module_base.photopicker.CropPictureContract
import com.ablec.module_base.photopicker.ActivityResultProxy
import com.ablec.module_base.photopicker.ActivityResultProxy.*
import com.ablec.myarchitecture.AppFileProvider.Companion.getFileUri
import com.ablec.myarchitecture.ImageUtil
import com.ablec.myarchitecture.databinding.FragmentPickerBinding
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.permissionx.guolindev.PermissionX
import kotlinx.coroutines.launch
import java.io.InputStream

class PickerFragment : Fragment() {

    private lateinit var binding: FragmentPickerBinding

    private val photoProxy = ActivityResultProxy(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //photoProxy继承LifecycleOwner 感知LifeCycle生命周期
        lifecycle.addObserver(photoProxy)
    }

    override fun onDestroy() {
        super.onDestroy()
//        lifecycle.removeObserver(photoProxy) ?
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPickerBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.start.setOnClickListener {
            extracted()
        }
    }

    private fun extracted() {
        BottomPickPhotoDialog.newInstance().apply {
            setOnItemCallBack(object : BottomPickPhotoDialog.OnItemClickCallBack {
                override fun onCameraClick() {
                    PermissionX.init(this@PickerFragment)
                        .permissions(Manifest.permission.CAMERA)
                        .onExplainRequestReason { scope, deniedList ->
                            val message = "拍照功能需要您同意相册权限"
                            val ok = "确定"
                            scope.showRequestReasonDialog(deniedList, message, ok)
                        }
                        .onForwardToSettings { scope, deniedList ->
                            val message = "您需要去设置当中同意相册权限"
                            val ok = "确定"
                            scope.showForwardToSettingsDialog(deniedList, message, ok)
                        }
                        .request { allGranted, grantedList, deniedList ->
                            if (allGranted){
                                photoProxy.takePhoto(
                                    getFileUri("拍照的图片"),
                                    object : ResultCallBack {
                                        override fun onResult(uri: Uri?) {
                                            uri?.let {
                                                lifecycleScope.launch {
                                                    val compressImage = ImageUtil.compressImage(
                                                        BaseApplication.instance,
                                                        uri,
                                                        2048
                                                    )
                                                    val bitmap = BitmapFactory.decodeStream(
                                                        this@PickerFragment.requireContext().contentResolver?.openInputStream(
                                                            compressImage
                                                        )
                                                    )
                                                    binding.imageView1.setImageBitmap(
                                                        bitmap
                                                    )
                                                }
                                            }
                                        }
                                    })
                            }else{
                                ToastUtils.showShort("权限被拒绝")
                            }
                        }
                }

                override fun onAlbumClick() {
                    photoProxy.pickAlbum(object : ResultCallBack {
                        override fun onResult(uri: Uri?) {
                            if (uri == null) {
                                return
                            }
                            showImage(uri, binding.imageView1)
                            val fileUri = getFileUri("temp_crop")
                            photoProxy.crop(
                                CropPictureContract.CropConfig(uri, fileUri),
                                object : ResultCallBack {
                                    override fun onResult(uri: Uri?) {
                                        uri?.let {
                                            showImage(uri, binding.imageView2)
                                        }
                                        LogUtils.d(uri)
                                    }
                                })
                        }
                    })
                }

                override fun onCancel() {
                }
            })
        }.show(childFragmentManager)
    }

    private fun showImage(uri: Uri, imageView: ImageView) {
        var inputStream: InputStream? = null
        try {
            inputStream = context?.contentResolver?.openInputStream(uri)
            val bimap = BitmapFactory.decodeStream(inputStream)
            imageView.setImageBitmap(bimap)
        } finally {
            inputStream?.close()
        }
    }

    companion object {
        const val TAG = "PickerFragment"
    }

}

