package com.ablec.myarchitecture.logic.main.widget

import com.ablec.myarchitecture.android.BottomSheetBehaviorActivity
import com.ablec.myarchitecture.base.RouterFragment
import com.ablec.myarchitecture.gridtopager.GridActivity

class WidgetRouterFragment : RouterFragment() {

    override fun getRouterList(): Collection<JUMP_ITEM>? {
        return listOf<JUMP_ITEM>(
            ItemFragment(
                WidgetRouterFragmentDirections.actionMainFragmentToConstraintLayoutFragment(),
                "ConstraintLayout"
            ),
            ItemFragment(
                WidgetRouterFragmentDirections.actionMainFragmentToTransformationFragment(),
                "Transformation"
            ),
            ItemFragment(
                WidgetRouterFragmentDirections.actionMainFragmentToWebSocketFragment(),
                "WebSocket"
            ),
            ItemFragment(
                WidgetRouterFragmentDirections.actionMainFragmentToRxFragment(),
                "Rx"
            ),
            ItemFragment(
                WidgetRouterFragmentDirections.actionMainFragmentToMyListFragment(),
                "MyList"
            ),
            ItemFragment(
                WidgetRouterFragmentDirections.actionMainFragmentToPickerFragment(),
                "PickerPhoto"
            ),
            ItemFragment(
                WidgetRouterFragmentDirections.actionMainFragmentToFlowFragment(),
                "Kt - Flow"
            ),
            ItemFragment(
                WidgetRouterFragmentDirections.actionMainFragmentToMatrixFragment(),
                "Matrix - Learn"
            ),
            ItemFragment(
                WidgetRouterFragmentDirections.actionMainFragmentToEventFragment(),
                "EventDispatch"
            ),
            ItemActivity("BottomBehaviorActivity") {
                BottomSheetBehaviorActivity.start(requireActivity())
            }
        )
    }
}