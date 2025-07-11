package com.ablec.myarchitecture.logic.main.anim

import com.ablec.myarchitecture.base.RouterFragment
import com.ablec.myarchitecture.gridtopager.GridActivity

class AnimRouterFragment : RouterFragment() {

    override fun getRouterList(): Collection<JUMP_ITEM>? {
        return listOf<JUMP_ITEM>(
            ItemFragment(
                AnimRouterFragmentDirections.actionAnimRouterFragmentToMotionLayoutFragment(),
                "MotionLayout-1-Basic"
            ),
            ItemFragment(
                AnimRouterFragmentDirections.actionAnimRouterFragmentToCoordinatorLayoutFragment(),
                "MotionLayout-2-CoordinatorLayout"
            ),
            ItemFragment(
                AnimRouterFragmentDirections.actionAnimRouterFragmentToCarouselFragment(),
                "MotionLayout-3-Carousel"
            ),
            ItemFragment(
                AnimRouterFragmentDirections.actionAnimRouterFragmentToAnimListFragment(),
                "AnimList"
            ),
            ItemActivity("转场动画") {
                GridActivity.start(requireContext())
            },
        )
    }
}