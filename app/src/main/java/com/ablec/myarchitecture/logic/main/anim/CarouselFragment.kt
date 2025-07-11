package com.ablec.myarchitecture.logic.main.anim

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.helper.widget.Carousel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ablec.lib.base.BaseFragment
import com.ablec.lib.ext.viewBinding
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.FragmentMotionLayoutCarouselBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CarouselFragment : BaseFragment(R.layout.fragment_motion_layout_carousel) {
    private val binding by viewBinding<FragmentMotionLayoutCarouselBinding>()
    private val TAG = "CarouselFragment"

    val drawableResIds = mutableListOf(
        R.drawable.animal_2024172,
        R.drawable.beetle_562035,
        R.drawable.bug_189903,
        R.drawable.butterfly_417971,
        R.drawable.butterfly_dolls_363342,
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCarousel()
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.RESUMED) {
//                while (isActive){
//                    delay(1500)
//                    val currentIndex = binding.carousel.currentIndex + 1
//                    binding.carousel.transitionToIndex(currentIndex,200)
//                }
//            }
//        }
    }

    private fun setupCarousel() {

        val adapter = object : Carousel.Adapter {
            override fun count(): Int {
                return drawableResIds.size
            }

            override fun populate(view: View, index: Int) {
                Log.d(TAG, "populate: index = $index, view = $view")
                if (view is ImageView) {
                    view.setImageResource(drawableResIds[index])
                }
            }

            override fun onNewItem(index: Int) {
            }
        }
        binding.carousel.setAdapter(adapter)
        binding.carousel.jumpToIndex(1)
    }
}