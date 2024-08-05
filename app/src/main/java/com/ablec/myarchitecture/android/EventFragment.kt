package com.ablec.myarchitecture.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ablec.lib.ext.debounceClick
import com.ablec.myarchitecture.databinding.FragmentEventBinding
import java.util.concurrent.locks.ReentrantLock

class EventFragment : Fragment() {

    private lateinit var binding: FragmentEventBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventBinding.inflate(layoutInflater)
        return binding.root
    }

    private val lock = ReentrantLock()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.child.debounceClick{
            try {
                lock.lockInterruptibly()
                //todo sth
            } finally {
                if (lock.isHeldByCurrentThread) {
                    lock.unlock()
                }
            }


        }

    }



}