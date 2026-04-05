package com.ablec.myarchitecture.logic.navigation.module

import com.ablec.module_base.service.RouterServiceManager
import com.ablec.myarchitecture.base.RouterFragment

class ModuleRouterFragment : RouterFragment() {

    override fun getRouterList(): Collection<JUMP_ITEM>? {
        return listOf<JUMP_ITEM>(
            ItemActivity(
                "Compose"
            ) {
                RouterServiceManager.getComposeService()?.start(requireContext())
            },
            ItemActivity(
                "JNI"
            ) {
                RouterServiceManager.getNativeService()?.startNativeUi(requireContext())
            },
            ItemFragment(ModuleRouterFragmentDirections.actionModuleRouterFragmentToBinderFragment(),"Binder")
        )
    }
}


