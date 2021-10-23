package com.temption.myarchitecture.logic.rx

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.temption.myarchitecture.databinding.RxFragmentBinding
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

import java.util.concurrent.TimeUnit


class RxFragment : Fragment() {

    private val viewModel by viewModels<RxViewModel>()

    private lateinit var binding: RxFragmentBinding

    private val searchContent = PublishSubject.create<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = RxFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

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
            .throttleFirst(1000, TimeUnit.MILLISECONDS)
            .subscribe {
                binding.textViewRealSearch.text = it
            }

    }

}