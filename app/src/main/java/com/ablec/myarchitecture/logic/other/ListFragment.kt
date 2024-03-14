package com.ablec.myarchitecture.logic.other

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ablec.lib.base.BaseFragment
import com.ablec.lib.ext.debounceClick
import com.ablec.lib.ext.viewBinding
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.SimpleRecyclerviewBinding
import com.ablec.myarchitecture.logic.pageslist.DataListModel
import com.ablec.myarchitecture.logic.pageslist.ListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyListFragment : BaseFragment(R.layout.simple_recyclerview) {

    private val vm by viewModels<DataListModel>()

    private val binding: SimpleRecyclerviewBinding by viewBinding()
    private val listAdapter = ListAdapter()

    private var isGridMode = false

    private val gridLayoutManager by lazy {
        GridLayoutManager(requireContext(), 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (isGridMode) {
                        return 1
                    } else {
                        return 2
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv.apply {
            itemAnimator = null
            layoutManager = gridLayoutManager
            adapter = object : AnimationAdapter2(listAdapter) {
                override fun getAnimators(holder: RecyclerView.ViewHolder): Array<Animator> {
                    if (isGridMode) {
                        return arrayOf(
                            ObjectAnimator.ofFloat(holder.itemView, "alpha", 0f, 1f),
                        )
                    }
                    return if (holder.bindingAdapterPosition == 3) {
                        arrayOf(
                            ObjectAnimator.ofFloat(holder.itemView, "scaleX", .5f, 1f),
                            ObjectAnimator.ofFloat(holder.itemView, "scaleY", .5f, 1f),
                            ObjectAnimator.ofFloat(holder.itemView, "alpha", 0f, 1f),)
                    } else {
                        arrayOf(
                            ObjectAnimator.ofFloat(holder.itemView, "alpha", 0f, 1f),
                        )
                    }
                }
            }.apply {
                setDuration(300)
                setFirstOnly(false)
            }
        }
        vm.getListTest();
        vm.listLive.observe(viewLifecycleOwner) {
            listAdapter.setList(it?.list)
        }

        binding.btnTest.debounceClick {
            switch(-1)
        }

        listAdapter.setOnItemClickListener { adapter, view, position ->
            switch(position)
        }

    }

    private fun switch(i: Int) {
        isGridMode = !isGridMode
        listAdapter.notifyItemRangeChanged(0, listAdapter.itemCount)
        if (i > 0) {
            gridLayoutManager.scrollToPositionWithOffset(i, 0)
        }
    }


}