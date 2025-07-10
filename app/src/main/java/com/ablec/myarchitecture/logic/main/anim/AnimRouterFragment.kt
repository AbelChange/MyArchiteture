package com.ablec.myarchitecture.logic.main.anim

import com.ablec.myarchitecture.base.RouterFragment

class AnimRouterFragment : RouterFragment() {

    override fun getRouterList(): Collection<JUMP_ITEM>? {
        return listOf<JUMP_ITEM>(
            ItemFragment(
                AnimRouterFragmentDirections.actionAnimRouterFragmentToMotionLayoutFragment(),
                "MotionLayout"
            ),
            ItemFragment(
                AnimRouterFragmentDirections.actionAnimRouterFragmentToCoordinatorLayoutFragment(),
                "CoordinatorLayout"
            ),
            ItemFragment(
                AnimRouterFragmentDirections.actionAnimRouterFragmentToAnimListFragment(),
                "AnimList"
            )
        )
    }
}