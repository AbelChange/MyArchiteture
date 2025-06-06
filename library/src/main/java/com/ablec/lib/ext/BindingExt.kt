package com.ablec.lib.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ablec.library.R
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


inline fun <reified VB : ViewBinding> Fragment.viewBinding() =
    FragmentViewBindingDelegate(VB::class.java)

class FragmentViewBindingDelegate<VB : ViewBinding>(
    private val clazz: Class<VB>,
) : ReadOnlyProperty<Fragment, VB> {
    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        return requireNotNull(thisRef.view) { "The constructor missing layout id or the property of ${property.name} has been destroyed." }.run {
                getTag(R.id.tag_view_binding) as? VB ?: (clazz.getMethod("bind", View::class.java)
                    .invoke(null, this) as VB).also { setTag(R.id.tag_view_binding, it) }
            }.also { binding ->
                if (binding is ViewDataBinding) binding.lifecycleOwner = thisRef.viewLifecycleOwner
            }
    }

}


