package com.ablec.myarchitecture.customviewlearn

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ablec.lib.ext.viewBinding
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.FragmentMatrixBinding

/**
 * @author shuaihui_hao
 * @date 2024/6/21
 * @description
 */
class MatrixFragment:Fragment(R.layout.fragment_matrix) {

    private val binding: FragmentMatrixBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }




}