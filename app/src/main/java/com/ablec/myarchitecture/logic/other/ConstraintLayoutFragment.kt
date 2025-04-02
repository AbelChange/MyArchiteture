package com.ablec.myarchitecture.logic.other

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ablec.myarchitecture.databinding.ConstraintFragmentBinding
import com.ablec.myarchitecture.databinding.SimpleButtonBinding


/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/1/8 16:59
 */
class ConstraintLayoutFragment : Fragment() {

    private lateinit var binding: ConstraintFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ConstraintFragmentBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val remoteView = binding.viewStub.inflate()
        val stubBinding = SimpleButtonBinding.bind(remoteView)
        binding.btn.setOnClickListener {
            remoteView.isVisible = !remoteView.isVisible
            Log.d("remoteView", remoteView.isVisible.toString())
            Log.d("stubBinding", stubBinding.root.isVisible.toString())
            //always false
            Log.d("viewStub", binding.viewStub.isVisible.toString())
        }
        val trackDrawable = binding.switchCompat.trackDrawable
        Log.d("trackDrawable", trackDrawable.javaClass.name)
    }


}