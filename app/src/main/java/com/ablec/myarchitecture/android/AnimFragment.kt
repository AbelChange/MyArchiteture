package com.ablec.myarchitecture.android

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ablec.lib.base.VBBaseAdapter
import com.ablec.lib.base.VBViewHolder
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.FragmentAnimBinding
import com.ablec.myarchitecture.databinding.ItemSimpleDataBinding

class AnimFragment : Fragment() {

    private lateinit var binding: FragmentAnimBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimBinding.inflate(layoutInflater)
        return binding.root
    }


    private val adapter =
        object : VBBaseAdapter<ItemSimpleDataBinding, String>(R.layout.item_simple_data) {
            override fun convert(holder: VBViewHolder<ItemSimpleDataBinding>, item: String) {
                holder.binding.textView.text = item
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerview.adapter = adapter
        val list =
            "宋江、卢俊义、吴用、公孙胜、关胜、林冲、秦明、呼延灼、花荣、柴进、李应、朱仝、鲁智深、武松、董平、张清、杨志、徐宁、张超、".split("、").toList()
        adapter.setList(list)

        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        scheduleDelayShow()
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(-1)) {
                    //滑到底部
                } else if (!recyclerView.canScrollVertically(1)) {
                    //滑到顶部
                } else {
                    hide()
                }

            }
        })
    }

    private var isHiding = false
    private var hideAnim: ObjectAnimator? = null
    private var showAnim: ObjectAnimator? = null
    private fun hide() {
        showAnim?.cancel()
        if (hideAnim?.isRunning == true) {
            return
        }
        hideAnim = ObjectAnimator.ofFloat(
            binding.animView,
            "translationX",
            binding.animView.translationX,
            200f
        ).apply {
            duration = 200
            doOnCancel {
                isHiding = false
            }
            doOnEnd {
                isHiding = false
            }
        }
        hideAnim?.start()

    }

    private fun scheduleDelayShow() {
        showAnim?.cancel()
        showAnim = ObjectAnimator.ofFloat(
            binding.animView,
            "translationX",
            binding.animView.translationX,
            0f
        ).apply {
            setAutoCancel(true)
            duration = 200
            startDelay = 500
        }
        showAnim?.start()
    }

}