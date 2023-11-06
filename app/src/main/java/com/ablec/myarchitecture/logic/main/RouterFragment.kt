package com.ablec.myarchitecture.logic.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.ablec.lib.base.VBBaseAdapter
import com.ablec.lib.base.VBViewHolder
import com.ablec.lib.ext.navigate
import com.ablec.lib.ext.viewBinding
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.ItemNaviDataBinding
import com.ablec.myarchitecture.databinding.RouterFragmentBinding

class RouterFragment : Fragment(R.layout.router_fragment) {

    private val binding: RouterFragmentBinding by viewBinding()

    data class Item(val nav: NavDirections, val title: String? = null, val des: String? = null)

    private val adapter =
        object : VBBaseAdapter<ItemNaviDataBinding, Item>(R.layout.item_navi_data) {
            override fun convert(holder: VBViewHolder<ItemNaviDataBinding>, item: Item) {
                holder.binding.textViewTitle.text = item.title
            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = listOf<Item>(
            Item(
                RouterFragmentDirections.actionMainFragmentToConstraintLayoutFragment(),
                "ConstraintLayout"
            ),
            Item(
                RouterFragmentDirections.actionMainFragmentToCoordinatorLayoutFragment(),
                "CoordinatorLayout"
            ),
            Item(
                RouterFragmentDirections.actionMainFragmentToTransformationFragment(),
                "Transformation"
            ),
            Item(
                RouterFragmentDirections.actionMainFragmentToWebSocketFragment(),
                "Coordinator"
            ),
            Item(
                RouterFragmentDirections.actionMainFragmentToRxFragment(),
                "Rx"
            ),
            Item(
                RouterFragmentDirections.actionMainFragmentToAnimFragment(),
                "Anim"
            ),
            Item(
                RouterFragmentDirections.actionMainFragmentToMyListFragment(),
                "MyList"
            ),
            Item(
                RouterFragmentDirections.actionMainFragmentToPickerFragment(),
                "PickerPhoto"
            ),
            Item(
                RouterFragmentDirections.actionMainFragmentToBinderFragment(),
                "Android Binder"
            ),
        )

        binding!!.recyclerview.adapter = adapter.apply {
            setOnItemClickListener { adapter, view, position ->
                navigate(getItem(position).nav)
            }
            setList(list)
        }
    }

}