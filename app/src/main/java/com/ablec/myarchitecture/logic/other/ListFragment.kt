package com.ablec.myarchitecture.logic.other

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.addListener
import androidx.core.view.doOnNextLayout
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
import com.blankj.utilcode.util.ImageUtils
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
                override fun getAnimators(viewHolder: RecyclerView.ViewHolder): Array<Animator> {
                    return emptyArray()
//                    if (viewHolder.bindingAdapterPosition == 3) {
//                        arrayOf(
//                            ObjectAnimator.ofFloat(viewHolder.itemView, "scaleX", .5f, 1f),
//                            ObjectAnimator.ofFloat(viewHolder.itemView, "scaleY", .5f, 1f),
//                            ObjectAnimator.ofFloat(viewHolder.itemView, "alpha", 0f, 1f),
//                        )
//                    } else {
//                        emptyArray()
//                    }
                }
            }.apply {
                setDuration(300)
                setFirstOnly(false)
            }
        }
        vm.listLive.observe(viewLifecycleOwner) {
            listAdapter.setList(it?.list)
        }

        binding.btnTest.debounceClick {
            switch(-1)
        }

        listAdapter.setOnItemClickListener { adapter, view, position ->
//            switch(position)
            screenShot(position)
        }

    }

    private fun switch(position: Int) {
        isGridMode = !isGridMode
        listAdapter.notifyItemRangeChanged(0, listAdapter.itemCount)
        if (position >= 0) {
            gridLayoutManager.scrollToPositionWithOffset(position, 0)
        }
    }


    private fun screenShot(position: Int) {
        binding.fakeContainer.alpha = 1f
        binding.rv.alpha = 0f
        binding.fakeContainer.removeAllViews()
        val fakeBitmap = ImageUtils.view2Bitmap(binding.root)
        val itemView = listAdapter.getViewByPosition(position, R.id.root) ?: return
        val fakeBitmapItem = ImageUtils.view2Bitmap(itemView)

        val array = IntArray(2)
        itemView.getLocationInWindow(array)

        val startView: View = ImageView(requireContext()).apply {
            layoutParams = ConstraintLayout.LayoutParams(
                itemView.width,
                itemView.height,
            ).apply {
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                leftMargin = array[0]
                topMargin = array[1]
            }
            setImageBitmap(fakeBitmapItem)
        }

        binding.fakeContainer.run {
            //fake rv
            addView(
                ImageView(requireContext()).apply {
                    layoutParams = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT
                    )
                    setImageBitmap(fakeBitmap)
                }
            )
            //fake clickItem
            addView(startView)
        }
        switch(position)
        binding.root.run {
            doOnNextLayout {
                val endView = listAdapter.getViewByPosition(position, R.id.root)
                if (endView != null) {
                    animate(startView, endView)
                }
            }
        }
    }


    private fun animate(viewStart: View, viewEnd: View) {
        val centerXStart = viewStart.x + viewStart.width / 2f
        val centerYStart = viewStart.y + viewStart.height / 2f
        val centerXEnd = viewEnd.x + viewEnd.width / 2f
        val centerYEnd = viewEnd.y + viewEnd.height / 2f

        val scaleX = viewEnd.width.toFloat() / viewStart.width.toFloat()
        val scaleY = viewEnd.height.toFloat() / viewStart.height.toFloat()

        val translationX =
            ObjectAnimator.ofFloat(viewStart, "translationX", centerXEnd - centerXStart)
        val translationY =
            ObjectAnimator.ofFloat(viewStart, "translationY", centerYEnd - centerYStart)
        val scaleXT = ObjectAnimator.ofFloat(viewStart, "scaleX", scaleX)
        val scaleYT = ObjectAnimator.ofFloat(viewStart, "scaleY", scaleY)
//        val alphaFake = ObjectAnimator.ofFloat(binding.fakeContainer, "alpha", 1f,0f)
//        val alphaRv = ObjectAnimator.ofFloat(binding.rv, "alpha", 0f,1f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translationX, translationY, scaleXT, scaleYT)
        animatorSet.addListener(onEnd = {
            binding.fakeContainer.alpha = 0f
            binding.rv.alpha = 1f
            binding.fakeContainer.removeAllViews()
        })
        animatorSet.duration = 300
        animatorSet.start()
    }


}