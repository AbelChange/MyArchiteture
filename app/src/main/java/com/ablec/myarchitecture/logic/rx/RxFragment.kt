package com.ablec.myarchitecture.logic.rx

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.ablec.lib.base.BaseFragment
import com.ablec.lib.ext.viewBinding
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.RxFragmentBinding
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class RxFragment : BaseFragment(R.layout.rx_fragment) {

    private val viewModel by viewModels<RxViewModel>()

    private val binding: RxFragmentBinding by viewBinding()

    private val searchContent = PublishSubject.create<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.edtSearch.doAfterTextChanged {
            searchContent.onNext(it.toString())
        }
        subscribe()
        testFlowable()
        TextView(requireContext()).addTextChangedListener{

        }
    }

    private fun testFlowable() {

        Observable.just(123).toFlowable(BackpressureStrategy.BUFFER).subscribe()
        Flowable.just(123).subscribe()

        val str:String? = "Dsadsa"
        val lenth = str?.length?:{
            -1
        }
    }

    /**
     * 防抖操作符 debounce :对于连续动作(动作间的时间间隔小于t)，以最后一次为准
     * 避免每次都去server查询
     *
     *
     */
    private fun subscribe() {
        val subscribe = searchContent
            .throttleFirst(100, TimeUnit.MILLISECONDS)
            .subscribe {
                binding.textViewRealSearch.text = it
            }

    }

}