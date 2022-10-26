package com.ablec.lib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author HaoShuaiHui
 * @description:navigation中使用，重建时使用上次保留的view
 * @date :2022/10/25 11:27
 */
abstract class BaseNavigationFragment : BaseFragment(0) {

    var hasInitializedRootView = false
    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getPersistentView(inflater, container, savedInstanceState, getLayoutId())
    }

    abstract fun getLayoutId(): Int
    private fun getPersistentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
        layout: Int
    ): View? {
        if (rootView == null) {
            // Inflate the layout for this fragment
            rootView = inflater?.inflate(layout, container, false)
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove rootView from the existing parent view group
            // (it will be added back).
            (rootView?.parent as? ViewGroup)?.removeView(rootView)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            initView(view, savedInstanceState)
        }
    }

    private fun initView(view: View, savedInstanceState: Bundle?) {


    }

}