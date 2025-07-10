package com.ablec.myarchitecture.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.ablec.lib.base.VBBaseAdapter
import com.ablec.lib.base.VBViewHolder
import com.ablec.lib.ext.navigate
import com.ablec.lib.ext.viewBinding
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.android.BottomSheetBehaviorActivity
import com.ablec.myarchitecture.databinding.ItemNaviDataBinding
import com.ablec.myarchitecture.databinding.RouterFragmentBinding
import com.ablec.myarchitecture.gridtopager.GridActivity

abstract class RouterFragment : Fragment(R.layout.router_fragment) {

    private val binding: RouterFragmentBinding by viewBinding()

    sealed class JUMP_ITEM(open val title: String? = null, open val des: String? = null)

    data class ItemFragment(
        val nav: NavDirections,
        override val title: String? = null,
        override val des: String? = null
    ) :
        JUMP_ITEM(title, des)

    data class ItemActivity(
        override val title: String? = null,
        override val des: String? = null,
        val callBack: () -> Unit,
    ) : JUMP_ITEM()


    private val adapter =
        object : VBBaseAdapter<ItemNaviDataBinding, JUMP_ITEM>(R.layout.item_navi_data) {
            override fun convert(holder: VBViewHolder<ItemNaviDataBinding>, item: JUMP_ITEM) {
                holder.binding.textViewTitle.text = item.title
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.adapter = adapter.apply {
            setOnItemClickListener { adapter, view, position ->
                when (val item = getItem(position)) {
                    is ItemActivity -> {
                        item.callBack.invoke()
                    }

                    is ItemFragment -> navigate(
                        item.nav
                    )
                }
            }
            setList(getRouterList())
        }
    }

    abstract fun getRouterList(): Collection<JUMP_ITEM>?

}