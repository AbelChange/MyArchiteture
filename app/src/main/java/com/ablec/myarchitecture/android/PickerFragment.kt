package com.ablec.myarchitecture.android

import android.Manifest
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.core.content.contentValuesOf
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import com.ablec.lib.glide.GlideUtils
import com.ablec.module_base.photopicker.BottomPickPhotoDialog
import com.ablec.module_base.photopicker.CropPictureContract
import com.ablec.module_base.photopicker.ActivityResultProxy
import com.ablec.module_base.photopicker.ActivityResultProxy.*
import com.ablec.myarchitecture.AppFileProvider
import com.ablec.myarchitecture.AppFileProvider.Companion.generateContentUri
import com.ablec.myarchitecture.databinding.FragmentPickerBinding
import com.blankj.utilcode.util.ToastUtils
import com.permissionx.guolindev.PermissionX
import java.io.File

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
                                    generateContentUri("pending_photo"),
                                    object : ResultCallBack {
                                        override fun onResult(uri: Uri?) {
                                            GlideUtils.loadImage(binding.imageView1.context, uri, binding.imageView1)
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
                            val saveAndExpose = AppFileProvider.saveAndExpose(
                                binding.imageView1.context, uri, "crop_source"
                            )
//                            GlideUtils.loadImage(binding.imageView1.context, saveAndExpose, binding.imageView1)

                            photoProxy.crop(CropPictureContract.CropConfig(
                                saveAndExpose, generateContentUri("crop_result")
                            ), object : ResultCallBack {
                                override fun onResult(uri: Uri?) {
                                    uri?.let {
                                        GlideUtils.loadImage(
                                            binding.imageView1.context,
                                            uri,
                                            binding.imageView1
                                        )
                                    }
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


    companion object {
        const val TAG = "PickerFragment"
    }

}

