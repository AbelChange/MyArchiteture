package com.ablec.lib.base

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import java.lang.reflect.ParameterizedType

/**
 * Created on 2022/6/29 15:47
 *
 * @author: yaojiaqing
 * @Desc:
 */

abstract class VBBaseAdapter<VB : ViewBinding, T>(
    @LayoutRes private val layoutResId: Int,
    data: MutableList<T>? = null
) :
    BaseQuickAdapter<T, VBViewHolder<VB>>(layoutResId, data) {
    //重写返回自定义 ViewHolder
    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): VBViewHolder<VB> {
        //这里为了使用简洁性，使用反射来实例ViewBinding
        val vbClass: Class<VB> =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VB>
        val inflate = vbClass.getDeclaredMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        val mBinding =
            inflate.invoke(null, LayoutInflater.from(parent.context), parent, false) as VB
        return VBViewHolder(mBinding)
    }
}

abstract class VBBaseMultiItemAdapter<T : MultiItemEntity> :
    BaseMultiItemQuickAdapter<T, VBViewHolder<ViewBinding>>() {

    private val bindings: SparseArray<(LayoutInflater, ViewGroup, Boolean) -> ViewBinding> by lazy(
        LazyThreadSafetyMode.NONE
    ) {
        SparseArray<(LayoutInflater, ViewGroup, Boolean) -> ViewBinding>()
    }

    fun addViewBinding(type: Int, inflate: (LayoutInflater, ViewGroup, Boolean) -> ViewBinding) {
        bindings.put(type, inflate)
    }

    override fun onCreateDefViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VBViewHolder<ViewBinding> {
        val inflate = bindings.get(viewType)
        require(inflate != null) { "ViewType: $viewType found ViewBinding，please use addViewBinding() first!" }
        val viewBinding = inflate(LayoutInflater.from(parent.context), parent, false)
        return VBViewHolder(viewBinding)
    }
}


class VBViewHolder<VB : ViewBinding>( val binding: VB) : BaseViewHolder(binding.root){



}
