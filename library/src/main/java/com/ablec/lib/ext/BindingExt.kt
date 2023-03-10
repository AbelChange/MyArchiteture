package com.ablec.lib.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * 反射inflate方法/bind方法
 *
 * 用例：
 *      private val binding: ActivityHomeBinding by viewBinding()
 *
 * 或者：
 *     private val binding by viewBinding<ActivityHomeBinding>()
 */
inline fun <reified VB : ViewBinding> ComponentActivity.viewBinding() =
    lazy(LazyThreadSafetyMode.NONE) {
        val rootView = findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
        if (rootView != null) {
            return@lazy VB::class.java.getMethod("bind", View::class.java)
                .invoke(null, rootView) as VB
        }
        VB::class.java.getMethod("inflate", LayoutInflater::class.java)
            .invoke(null, layoutInflater) as VB
    }

/**
 * 调用inflate方法
 *
 * 用例：
 *     private val binding by viewBinding(ActivityHomeBinding::inflate)
 */
@JvmName("viewBindingInflate")
inline fun <reified VB : ViewBinding> ComponentActivity.viewBinding(noinline inflate: (LayoutInflater) -> VB) =
    lazy(LazyThreadSafetyMode.NONE) { inflate.invoke(layoutInflater) }

/**
 * 调用bind方法
 *
 * 用例：
 *      private val binding by viewBinding(ActivityHomeBinding::bind)
 */
@JvmName("viewBindingBind")
inline fun <reified VB : ViewBinding> ComponentActivity.viewBinding(noinline bind: (View) -> VB) =
    lazy(LazyThreadSafetyMode.NONE) {
        bind.invoke(findViewById<ViewGroup>(android.R.id.content).getChildAt(0))
    }


/**
 * 使用bind方法
 *
 * 反射用例：
 *     private val binding: FragmentTimelineBinding by viewBinding()
 *
 * 或者
 *    private val binding by viewBinding<FragmentTimelineBinding>()
 *
 * 不反射用例：
 *     private val binding by viewBinding(FragmentTimelineBinding::bind)
 */
inline fun <reified VB : ViewBinding> Fragment.viewBinding(noinline bind: ((View) -> VB)? = null) =
    FragmentViewBindingDelegate(VB::class.java, bind)

class FragmentViewBindingDelegate<VB : ViewBinding>(
    private val clazz: Class<VB>,
    private val bind: ((View) -> VB)? = null
) : ReadOnlyProperty<Fragment, VB> {
    private var binding: VB? = null

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (thisRef.view != null && binding == null) {
            binding =
                bind?.invoke(thisRef.requireView()) ?: clazz.getMethod("bind", View::class.java)
                    .invoke(null, thisRef.requireView()) as VB
            thisRef.viewLifecycleOwnerLiveData.observeForever(object :Observer<LifecycleOwner> {
                override fun onChanged(owner: LifecycleOwner?) {
                    if (owner == null) {
                        binding = null
                        thisRef.viewLifecycleOwnerLiveData.removeObserver(this)
                    }
                }
            })
        }
        return binding ?: throw IllegalStateException(
            "Should not attempt to get bindings when it might not be available"
        )
    }

}


