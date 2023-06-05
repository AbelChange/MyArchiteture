package com.ablec.myarchitecture.logic.other

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ablec.lib.base.BaseFragment
import com.ablec.lib.ext.viewBinding
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.SimpleRecyclerviewBinding
import com.ablec.myarchitecture.logic.pageslist.DataListModel
import com.ablec.myarchitecture.logic.pageslist.ListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class MyListFragment:BaseFragment(R.layout.simple_recyclerview) {

    private val vm by viewModels<DataListModel>()

    private val binding:SimpleRecyclerviewBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listAdapter = ListAdapter()
        binding.rv.adapter = listAdapter
        vm.getListTest();
        vm.listLive.observe(viewLifecycleOwner){
            val nextBoolean = Random.nextBoolean()
            if (nextBoolean){
                listAdapter.setList(it?.list?.subList(0,1))
            }else{
                listAdapter.setList(it?.list)
            }
        }
    }


}