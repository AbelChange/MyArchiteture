package com.ablec.module_base.photopicker

import android.os.Bundle
import android.view.View
import com.ablec.lib.ext.debounceClick
import com.ablec.lib.ext.viewBinding
import com.ablec.module_base.R
import com.ablec.module_base.databinding.PickPhotoDialogLayoutBinding
import com.ablec.module_base.view.BaseDialogFragment


/**
 * @author HaoShuaiHui
 * @description:
 * @date :2022/12/16 10:24
 */
class BottomPickPhotoDialog : BaseDialogFragment(R.layout.pick_photo_dialog_layout) {

    private val binding: PickPhotoDialogLayoutBinding by viewBinding()

    private var callBack:OnItemClickCallBack?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancel.debounceClick {
            callBack?.onCancel()
            dismiss()
        }
        binding.camera.debounceClick {
            callBack?.onCameraClick()
            dismiss()
        }
        binding.album.debounceClick {
            callBack?.onAlbumClick()
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.setOnCancelListener {
            callBack?.onCancel()
        }
    }

    fun setOnItemCallBack(cb:OnItemClickCallBack){
        callBack = cb
    }

    interface OnItemClickCallBack{
        fun onCameraClick()
        fun onAlbumClick()
        fun onCancel()
    }

    companion object{
        fun newInstance(): BottomPickPhotoDialog{
            return BottomPickPhotoDialog()
        }
    }


}