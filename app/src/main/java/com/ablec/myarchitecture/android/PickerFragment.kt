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
import com.ablec.module_base.photopicker.BottomPickPhotoDialog
import com.ablec.module_base.photopicker.CropPictureContract
import com.ablec.module_base.photopicker.ActivityResultProxy
import com.ablec.module_base.photopicker.ActivityResultProxy.*
import com.ablec.myarchitecture.AppFileProvider.Companion.getFileUri
import com.ablec.myarchitecture.databinding.FragmentPickerBinding
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.permissionx.guolindev.PermissionX
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
                    PermissionX.init(requireActivity())
                        .permissions(
                            Manifest.permission_group.CAMERA,
                        )
                        .explainReasonBeforeRequest()
                        .onExplainRequestReason { scope, deniedList ->
                           ToastUtils.showShort("onExplainRequestReason$deniedList")
                        }
                        .request { allGranted, grantedList, deniedList ->
                            if (allGranted) {
                                LogUtils.d(TAG, grantedList.toString())
                                photoProxy.takePhoto(
                                    getFileUri("拍照的图片"),
                                    object : ResultCallBack {
                                        override fun onResult(uri: Uri?) {
                                            uri?.let {
                                                val bitmap = BitmapFactory.decodeStream(
                                                    this@PickerFragment.requireContext().contentResolver?.openInputStream(
                                                        uri
                                                    )
                                                )
                                                binding.imageView1.setImageBitmap(
                                                    bitmap
                                                )
                                            }
                                        }
                                    })
                            }else{

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
                            val fileUri = getFileUri("裁剪")
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

